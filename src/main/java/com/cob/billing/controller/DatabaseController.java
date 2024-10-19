package com.cob.billing.controller;

import com.cob.billing.usecases.init.script.DatabaseInitUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/db")
public class DatabaseController {
    @Autowired
    DatabaseInitUseCase databaseInitUseCase;

    @GetMapping("/run-init-sql-script")
    public String runInitScript() {
        try {
            databaseInitUseCase.init();
            return "SQL script executed successfully!";
        } catch (Exception e) {
            return "Failed to execute SQL script: " + e.getMessage();
        }
    }
    @GetMapping("/run-clear-sql-script")
    public String runClearScript(){
        try {
            databaseInitUseCase.clear();
            return "SQL script executed successfully!";
        } catch (Exception e) {
            return "Failed to execute SQL script: " + e.getMessage();
        }
    }
}
