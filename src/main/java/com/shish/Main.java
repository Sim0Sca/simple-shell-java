package com.shish;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        String input = "";
        Command actualCmd = null;
        Command.addCommands();

        System.out.print(Command.getCwd() + "$ "); // Commands input

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
            System.out.print(Command.getCwd() + "$ "); // New input
        }

    }
}