package org.blastoffnetwork;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class Main {


    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Input file path not found. Specify the path to the input file as a command-line argument. Exiting.");
            System.exit(1);
        }

        Path filePath = Path.of(args[0]);
        String fileContents = "";

        try {
            fileContents = Files.readString(filePath);
        } catch (IOException ex) {
            System.err.printf("%s: Error reading file - does it exist?", filePath);
            System.exit(1);
        }


        try {
            System.out.println(TemplateEvaluator.evaluateWebTemplates(fileContents, filePath.toString()));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (InvalidSyntaxException e) {
            e.printStackTrace();
        }
    }
}
