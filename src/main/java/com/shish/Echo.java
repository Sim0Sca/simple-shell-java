package com.shish;

import java.util.ArrayList;

public class Echo extends Command {
    private String command;
    private ArrayList<String> aliases;
    private ArrayList<String> parameters;

    public Echo(String echo) {
        command = setCommand(echo);
        this.aliases = new ArrayList<>();
        this.parameters = new ArrayList<>();
    }

    @Override
    public String getCommand() {
        return command;
    }

    @Override
    public ArrayList<String> getAliases() {
        return aliases;
    }

    @Override
    public void setAliases(String alias) {
        aliases.add(alias);
    }

    @Override
    public ArrayList<String> getParameters() {
        return parameters;
    }

    @Override
    public void setParameters(String parameter) {
        parameters.add(parameter);
    }

    @Override
    public void execute() {
        for (String parameter : parameters) {
            System.out.print(parameter + " ");
        }
        System.out.println();
        reset(aliases, parameters);
    }
}
