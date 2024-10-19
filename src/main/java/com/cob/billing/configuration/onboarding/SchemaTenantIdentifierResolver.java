package com.cob.billing.configuration.onboarding;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

@Component
public class SchemaTenantIdentifierResolver implements CurrentTenantIdentifierResolver {
    private static final String DEFAULT_TENANT = "public"; // Set default schema here
    @Override
    public String resolveCurrentTenantIdentifier() {
        String tenantId = SchemaTenantContext.getCurrentTenant();
        return (tenantId != null) ? tenantId : DEFAULT_TENANT;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
