package com.shish;

import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.print("$ "); // Commands input

        Scanner scanner = new Scanner(System.in);
        String input = "";
        String result = ""; // Take the checkParameter String
        String pathEnv = System.getenv("PATH"); // PATH variable
        File dirObj = null; // For changing directory
        Command actualCmd = null;
        Command.addCommands();

        // Check if the command is inserted
        while (!(input = scanner.nextLine()).isEmpty()) {
            if (input.startsWith("exit")) {
                break;
            } else {
                actualCmd = Command.inputSplitter(input);
                if (actualCmd != null) {
                    actualCmd.execute();
                }
            }
            System.out.print("$ "); // New input
        }

    }
}