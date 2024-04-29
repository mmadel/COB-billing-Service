package com.cob.billing.configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CreateRedirectFile {
    public static  void create(){
        String inputFile = "C:\\cob\\documents\\billing\\redirect.txt";
        String outputFile = "C:\\cob\\documents\\billing\\redirect_1.txt";
        try (Stream<String> lines = Files.lines(Paths.get(inputFile))) {
            List<String> duplicatedLines = lines.flatMap(line -> Stream.of(line + " " +"https://billing-service-e4526add3402.herokuapp.com"+ line + "  200"))
                    .collect(Collectors.toList());

            Files.write(Paths.get(outputFile), duplicatedLines);
            System.out.println("Duplicated lines with space added have been written to " + outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
