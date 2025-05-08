package com.shish;

import java.util.ArrayList;

public class Echo extends Command {

    public Echo(String echo) {
        setCommand(echo);
        this.aliases = new ArrayList<>();
        this.parameters = new ArrayList<>();
    }

    @Override
    public void execute() {
        for (String parameter : parameters) {
            System.out.print(parameter + " ");
        }
        System.out.println();
        reset();
    }
}
