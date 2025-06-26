package com.leumas.finance.config.db;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

@Component
public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver {

    private static final String DEFAULT_TENANT = "public";

    @Override
    public Object resolveCurrentTenantIdentifier() {
        String tenantIdentifier = TenantContext.getCurrentTenant();
        return (String) (tenantIdentifier == null ? DEFAULT_TENANT : tenantIdentifier);
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}