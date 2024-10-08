package com.cob.billing.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
public class RedirectsFileGenerator {
    private static final String BASE_URL = " https://cob-billing-service-318f1a73fb4a.herokuapp.com";
    private static final String STATUS_CODE = " 200"; // Notice the space before 200

    public static void main(String[] args) {
        String inputFile = "C:\\cob\\cob-billing-service\\src\\main\\resources\\endpoints";  // Your input file containing endpoints
        String outputFile = "C:\\cob\\cob-billing-service\\src\\main\\resources\\_redirects"; // The output file to write to

        try (
                BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                String formattedLine = line.replaceAll("\\{(\\w+)}", ":$1");
                String redirectLine = formattedLine+ BASE_URL + formattedLine + STATUS_CODE;
                writer.write(redirectLine);
                writer.newLine(); // Write a new line after each redirect line
            }
            System.out.println("File _redirects generated successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
