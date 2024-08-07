package com.cob.billing.controller;

import com.cob.billing.usecases.init.script.DatabaseInitUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/init")
public class DatabaseInitController {
    @Autowired
    DatabaseInitUseCase databaseInitUseCase;

    @GetMapping("/run-sql-script")
    public String runInitScript() {
        try {
            databaseInitUseCase.runSqlScript();
            return "SQL script executed successfully!";
        } catch (Exception e) {
            return "Failed to execute SQL script: " + e.getMessage();
        }
    }
}
