package com.cob.billing.usecases.init.script;

public class RemoveCommentsFromScript {
    public static String remove(String sql) {
        // Remove single-line comments (--) and multi-line comments (/* */)
        // Regex explanation:
        // 1. `--.*?(\r?\n|$)` matches single-line comments (everything after "--" until a newline)
        // 2. `/\*.*?\*/` matches multi-line comments (anything between "/*" and "*/")
        sql = sql.replaceAll("--.*?(\r?\n|$)", "") // Remove single-line comments
                .replaceAll("/\\*.*?\\*/", "");  // Remove multi-line comments
        return sql;
    }
}
