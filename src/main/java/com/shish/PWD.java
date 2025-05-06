package com.shish;

import java.io.File;
import java.util.ArrayList;

public class PWD extends Command {
    private String command;
    private ArrayList<String> aliases;
    private ArrayList<String> parameters;
    File currentPath;
    String cwd;

    public PWD(String pwd) {
        command = setCommand(pwd);
        this.aliases = new ArrayList<>();
        this.parameters = new ArrayList<>();
        currentPath = new File("");
        cwd = currentPath.getAbsolutePath();
    }

    @Override
    public void execute() {
        System.out.println(cwd);
    }

    public void reset() {
        aliases.clear();
        parameters.clear();
        currentPath = null;
        cwd = "";
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
