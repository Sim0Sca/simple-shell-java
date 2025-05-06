package com.shish;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Type extends Command {
    private String command;
    private ArrayList<String> aliases;
    private ArrayList<String> parameters;

    public Type(String type) {
        command = setCommand(type);
        this.aliases = new ArrayList<>();
        this.parameters = new ArrayList<>();
    }

    @Override
    public void execute() {
        if (!parameters.isEmpty()) {
            Command check = Command.isCommand(parameters.getFirst());
            if (check != null) {
                System.out.println(parameters.getFirst() + " is a shell builtin command");
            } else {
                String isPath = checkPath(parameters.getFirst());
                if (isPath.isEmpty()) {
                    System.out.println(parameters.getFirst() + " not found");
                } else {
                    List<String> findProgramStream = Arrays.asList(isPath.split(":"));
                    System.out.println(findProgramStream.get(0) + " is " // result
                            + findProgramStream.get(1) // currentPath
                            + "/" + findProgramStream.get(2)); // el
                }
            }
            reset(aliases, parameters);
        } else {
            System.out.println();
        }
    }

    private String checkPath(String command) {
        boolean pathFound = false;
        // Convert the String to a List
        List<String> paths = Arrays.asList(pathEnv.split(":"));

        File currentPath;
        // Loop trough paths
        for (String path : paths) {
            currentPath = new File(path);
            String[] pathList = currentPath.list();
            if (pathList != null) {
                for (String el : pathList) {
                    if (command.equals(el)) {
                        pathFound = true;
                        return command + ":" + currentPath + ":" + el;
                    }
                }
                if (pathFound) { // File found
                    break;
                }
            }
        }
        if (!pathFound) {
            return "";
        }

        return "";
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
}
