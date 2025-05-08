package com.shish;

import java.io.File;
import java.util.ArrayList;

public class PWD extends Command {

    public PWD(String pwd) {
        setCommand(pwd);
        this.aliases = new ArrayList<>();
        this.parameters = new ArrayList<>();
    }

    @Override
    public void execute() {
        System.out.println(cwd);
    }
}
