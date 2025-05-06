package com.shish;

import java.util.ArrayList;

public class CD extends Command {

    @Override
    public void execute() {

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
        return null;
    }

    @Override
    public void setParameters(String parameter) {

    }
}
