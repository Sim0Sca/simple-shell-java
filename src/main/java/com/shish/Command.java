package com.shish;

import java.util.ArrayList;

public abstract class Command {

    static ArrayList<Command> commands = new ArrayList<>();
    static final String pathEnv = System.getenv("PATH"); // PATH variable

    public String command = "";
    public ArrayList<String> aliases = new ArrayList<>();
    public ArrayList<String> parameters = new ArrayList<>();
    public abstract void execute();
    public abstract String getCommand();

    /**
     * Return the parameters in the initial state in order to be reused later
     * @param aliases
     * @param parameters
     */
    public void reset(ArrayList<String> aliases, ArrayList<String> parameters) {
        aliases.clear();
        parameters.clear();
    };

    /**
     * Valid for getting and setting commands
     * @param command
     * @return
     */
    public String setCommand(String command) {
        return command;
    }
    public abstract ArrayList<String> getAliases();
    public abstract void setAliases(String alias);
    public abstract ArrayList<String> getParameters();
    public abstract void setParameters(String parameter);

    public String getPathEnv() {
        return pathEnv;
    }

    public static void addCommands() {
        commands.add(new Echo("echo"));
        commands.add(new Type("type"));
        commands.add(new PWD("pwd"));
        // TODO: Add other commands...
    };

    /**
     * Split all the inputs in the parts:
     * Command (String)
     * Aliases (List of strings)
     * Parameters (List of strings)
     * @param input
     * @return
     */
    public static Command inputSplitter(String input) {
        Command actualCmd = null;
        String[] command = input.split(" ");

        if ((actualCmd = isCommand(command[0])) != null) { // Check if the command exist
            // Add parameters to the object
            for (int i = 1; i < command.length; i++) {
                actualCmd.setParameters(command[i]);
            }
        } else {
            System.out.println(input + ": not found");
        }

        return actualCmd;
    }

    /**
     * Look for the command and return it
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
}
