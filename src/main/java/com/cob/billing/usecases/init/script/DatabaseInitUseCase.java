package com.cob.billing.usecases.init.script;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Service
public class DatabaseInitUseCase {
    @Autowired
    private DataSource dataSource;

    public void runSqlScript() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(connection, new ClassPathResource("init.sql"));
        }
    }
    public void runInitClaimAdjustmentReasonCodeScript() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(connection, new ClassPathResource("claim_adjustment_reason_codes.sql"));
        }
    }
}
