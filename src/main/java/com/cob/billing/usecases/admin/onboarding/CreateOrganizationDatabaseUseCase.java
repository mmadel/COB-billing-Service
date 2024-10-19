package com.cob.billing.usecases.admin.onboarding;

import com.cob.billing.configuration.onboarding.SchemaTenantContext;
import com.cob.billing.exception.business.OrganizationException;
import com.cob.billing.usecases.init.script.RemoveCommentsFromScript;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Component
public class CreateOrganizationDatabaseUseCase {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void create() throws OrganizationException {
        try {
            String createSchemaSql = "CREATE SCHEMA IF NOT EXISTS " + SchemaTenantContext.getCurrentTenant();
            jdbcTemplate.execute(createSchemaSql);
            jdbcTemplate.execute("SET SCHEMA '" + SchemaTenantContext.getCurrentTenant() + "'");
            jdbcTemplate.execute("SELECT pg_catalog.set_config('search_path','" + SchemaTenantContext.getCurrentTenant() + "', false);");
            executeScript("scripts/init_organization.sql");
            executeScript("scripts/lookups.sql");
        } catch (Exception e) {
            throw new OrganizationException(HttpStatus.CONFLICT, "", new Object[]{});
        }
    }

    private void executeScript(String scriptPath) throws IOException {
        ClassPathResource resource = new ClassPathResource(scriptPath);
        InputStream inputStream = resource.getInputStream();
        String sql = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        sql = RemoveCommentsFromScript.remove(sql);
        String[] sqlStatements = sql.split(";");
        for (String statement : sqlStatements) {
            if (!statement.trim().isEmpty()) {
                jdbcTemplate.execute(statement.trim());
            }
        }
    }
}
