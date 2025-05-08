package com.shish;

import java.io.File;
import java.util.ArrayList;

public abstract class Command {

    static ArrayList<Command> commands = new ArrayList<>();
    static final String pathEnv = System.getenv("PATH"); // PATH variable


    protected String command = "";
    protected ArrayList<String> aliases = new ArrayList<>();
    protected ArrayList<String> parameters = new ArrayList<>();

    protected abstract void execute();

    protected static File currentPath = new File("");
    protected static String cwd = currentPath.getAbsolutePath();

    /**
     * Return the parameters in the initial state in order to be reused later
     */
    public void reset() {
        aliases.clear();
        parameters.clear();
    }

    ;


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

    public String getPathEnv() {
        return pathEnv;
    }

    public static void addCommands() {
        commands.add(new Echo("echo"));
        commands.add(new Type("type"));
        commands.add(new PWD("pwd"));
        commands.add(new CD("cd"));
        // TODO: Add other commands...
    }

    ;

    /**
     * Split all the inputs in the parts:
     * Command (String)
     * Aliases (List of strings)
     * Parameters (List of strings)
     *
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
}
