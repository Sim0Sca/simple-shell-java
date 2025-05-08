package com.shish;

import java.io.File;

public class CD extends Command {

    File dirObj; // For changing directory

    public CD(String cd) {
        setCommand(cd);
        dirObj = null;
    };

    public String getPreviousDirectory(String cwd) {
        String previousDir = "";
        int index = cwd.lastIndexOf("/");
        previousDir = cwd.substring(0, index);
        return previousDir;
    }

    @Override
    protected void execute() {
        String dir = "";
        if (!parameters.getFirst().isEmpty()) {
            dir = parameters.getFirst();
            if (dir.startsWith("..")) {
                int index = 0, count = 0; // These var are not really necessary, but I will keep them anyway
                while ((index = dir.indexOf("..", index)) != -1 ){
                    count++;
                    cwd = getPreviousDirectory(cwd);
                    index++;
                }
                dirObj = new File(cwd); // Doesn't add the new directory
            } else if (dir.startsWith(".")) {
                dir = dir.substring(2);
                dirObj = new File(cwd + "/" + dir);
            } else if (dir.startsWith("/")) { // Absolute parth
                dirObj = new File(cwd + "/" + dir);
            } else { // Nothing before dir name
                dirObj = new File(cwd + "/" + dir);
            }
        }

        //System.out.println("dirObj " + dirObj);
        if (dirObj.exists()) {
            cwd = dirObj.getAbsolutePath();
        } else {
            System.out.println("cd: " + dir + ": No such file or directory");
        }

        reset();
    }
}
