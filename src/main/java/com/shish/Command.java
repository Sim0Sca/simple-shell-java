package com.shish;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Command {

    static ArrayList<Command> commands = new ArrayList<>();
    static final String pathEnv = System.getenv("PATH"); // PATH variable


    protected String command = "";
    protected ArrayList<String> aliases = new ArrayList<>();
    protected ArrayList<String> parameters = new ArrayList<>();

    protected abstract void execute();

    protected static File currentPath = new File(System.getProperty("user.home"));
    protected static String cwd = currentPath.getAbsolutePath();

    private static Echo echo;
    private static Type type;
    private static PWD pwd;

    public static String getCwd() {
        return cwd;
    }

    private static Command cd;

    public static Echo getEcho() {
        return echo;
    }

    public static Type getType() {
        return type;
    }

    public static PWD getPwd() {
        return pwd;
    }

    public static Command getCd() {
        return cd;
    }

    /**
     * Return the parameters in the initial state in order to be reused later
     */
    public void reset() {
        aliases.clear();
        parameters.clear();
    }


    public String getCommand() {
        return command;
    }

    /**
     * Valid for getting and setting commands
     *
     * @param command
     * @return
     */
    public void setCommand(String command) {
        this.command = command;
    }

    public ArrayList<String> getAliases() {
        return aliases;
    }

    public void setAliases(String alias) {
        aliases.add(alias);
    }

    public ArrayList<String> getParameters() {
        return parameters;
    }

    public void setParameters(String parameter) {
        parameters.add(parameter);
    }

    public static String getPathEnv() {
        return pathEnv;
    }

    public static void addCommands() {
        commands.add(echo = new Echo("echo"));
        commands.add(type = new Type("type"));
        commands.add(pwd = new PWD("pwd"));
        commands.add(cd = new CD("cd"));
        // TODO: Add other commands...
    }



    /**
     * Split all the inputs in the parts:
     * Command (String)
     * Aliases (List of strings)
     * Parameters (List of strings)
     *
     * @param input
     * @return
     */
    public static Command inputSplitter(String input) throws IOException, InterruptedException {
        Command actualCmd = null;
        String[] command = input.split(" ");
        ArrayList<String> commandSwap = new ArrayList<>();
        commandSwap.add(command[0]);
        boolean isEdited = false;

        StringBuilder tmpParameter = new StringBuilder();
        // Look for parameters delimited with " and merge it
        for (int i = 0; i < command.length; i++) {
            if (command[i].startsWith("\"") || command[i].startsWith("'")) {
                // Look for the other "
                for (int j = i; j < command.length; j++) {
                    if (command[j].endsWith("\"") || command[j].endsWith("'")) {
                        tmpParameter.append(command[j]);
                        break;
                    } else {
                        tmpParameter.append(command[j]).append(" ");
                    }
                }
                commandSwap.add(tmpParameter.toString());
                // Add the parameter
                isEdited = true;
            }
        }

        if (isEdited) {
            command = new String[commandSwap.size()];
            for (int i = 0; i < commandSwap.size(); i++) {
                command[i] = commandSwap.get(i);
            }
        }

        int quotesIndex;
        StringBuilder tmpBuilder;
        boolean isDeleted = false;
        for (int i = 0; i < command.length; i++) { // Remove the " from the parameters
            tmpBuilder = new StringBuilder(command[i]);
            while ((quotesIndex = tmpBuilder.indexOf("\"")) != -1) {
                tmpBuilder.deleteCharAt(quotesIndex);
                isDeleted = true;
            }
            while ((quotesIndex = tmpBuilder.indexOf("'")) != -1) {
                tmpBuilder.deleteCharAt(quotesIndex);
                isDeleted = true;
            }
            if (isDeleted) {
                command[i] = tmpBuilder.toString();
            }
        }

        if ((actualCmd = isCommand(command[0])) != null) { // Check if the command exist
            // Add parameters to the object
            for (int i = 1; i < command.length; i++) {
                actualCmd.setParameters(command[i]);
            }
        } else { // Check for PATH's programs
            String program = type.checkPath(command[0]);
            if (!program.isEmpty()) {
                executePath(program, input);
            } else {
                System.out.println(input + ": not found");
            }
            //System.out.println(input + ": not found");
        }

        return actualCmd;
    }

    /**
     * Look for the command and return it
     *
     * @param command
     * @return
     */
    public static Command isCommand(String command) {
        for (Command objCmd : commands) {
            if (command.equals(objCmd.getCommand())) {
                return objCmd;
            }
        }
        return null;
    }

    // FIXME: It doesn't work with other programs that require input (like nano)
    public static void executePath(String program, String input) throws IOException, InterruptedException {
        List<String> findProgramStream = Arrays.asList(program.split(":"));
        File currentProgram = new File(findProgramStream.get(1)
                + "/" + findProgramStream.get(2));
        // Check if the file is executable
        if (currentProgram.canExecute()) {
            Runtime run = Runtime.getRuntime();

            // Take the parameters of the input
            String[] commandWithArgs = input.split(" ");

            if (commandWithArgs.length > 1) {
                // Add the current working dir to the command
                List<String> list = new ArrayList<>(Arrays.stream(commandWithArgs).toList());
                String tmp = list.getLast();
                list.removeLast();
                if (tmp.startsWith("-")) { // It is a flag
                    list.add(cwd);
                    list.add(tmp);
                } else { // It is a parameter
                    list.add(cwd + "/" + tmp);
                }
                commandWithArgs = list.toArray(new String[0]);
            } else {
                List<String> list = new ArrayList<>(Arrays.stream(commandWithArgs).toList());
                list.add(cwd);
                commandWithArgs = list.toArray(new String[0]);
            }

            Process proc = run.exec(commandWithArgs);
            BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Manage the errors
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
            while ((line = errorReader.readLine()) != null) {
                System.err.println(line);
            }

            // Wait for the termination of the process
            proc.waitFor();
            // break;
        }
    }
}
