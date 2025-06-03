package com.leumas.finance.config.db;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class MultiTenantConnectionProviderImpl implements MultiTenantConnectionProvider {

    private final DataSource dataSource;

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
            connection.createStatement().execute("SET search_path TO " + tenantId);
        } catch (SQLException e) {
            throw new RuntimeException("Não foi possível definir o schema: " + tenantIdentifier, e);
        }
        return connection;
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
