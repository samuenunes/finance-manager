package com.leumas.finance.config.db;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class MultiTenantConnectionProviderImpl implements MultiTenantConnectionProvider {
    private final DataSource dataSource;
    private final Set<String> migratedSchemas = ConcurrentHashMap.newKeySet();

    public MultiTenantConnectionProviderImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Connection getAnyConnection() throws SQLException {
        return DataSourceUtils.getConnection(dataSource);
    }

    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        DataSourceUtils.releaseConnection(connection, dataSource);
    }

    @Override
    public Connection getConnection(Object tenantIdentifier) throws SQLException {
        final Connection connection = DataSourceUtils.getConnection(dataSource);
        final String tenantId = (String) tenantIdentifier;
        try {
            ensureSchemaAndMigrate(tenantId);
            connection.createStatement().execute("SET search_path TO %s".formatted(tenantId));
        } catch (SQLException e) {
            throw new RuntimeException("Não foi possível definir o schema: " + tenantIdentifier, e);
        }
        return connection;
    }

    private void ensureSchemaAndMigrate(String schema) {
        log.info("SCHEMA: {}", schema);
        if (migratedSchemas.contains(schema)) return;

        synchronized (this) {
            if (migratedSchemas.contains(schema)) return;

            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            String sql = "SELECT schema_name FROM information_schema.schemata WHERE schema_name = ?";
            boolean exists = Boolean.TRUE.equals(jdbcTemplate.query(sql, (ResultSetExtractor<Boolean>) ResultSet::next, schema));

            if (!exists) {
                jdbcTemplate.execute("CREATE SCHEMA " + schema);

                Flyway.configure()
                        .dataSource(dataSource)
                        .schemas(schema)
                        .locations("classpath:db/migration")
                        .baselineOnMigrate(true)
                        .load()
                        .migrate();
            }
            migratedSchemas.add(schema);
        }
    }

    @Override
    public void releaseConnection(Object tenantIdentifier, Connection connection) throws SQLException {
        DataSourceUtils.releaseConnection(connection, dataSource);
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return false;
    }

    @Override

    public boolean isUnwrappableAs(@NonNull Class aClass) {
        return false;
    }

    @Override
    public <T> T unwrap(@NonNull Class<T> unwrapType) {
        return null;
    }
}
