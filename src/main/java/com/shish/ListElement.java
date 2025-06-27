package com.shish;

import com.sun.jna.LastErrorException;
import com.sun.jna.Library;
import com.sun.jna.Native;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Path;

public class ListElement {
    private String name;
    private long dimension;
    private static boolean showHidden;
    private static boolean showDimension;
    private double blockSize;

    public ListElement(String name) {
        this.name = name;
        dimension = 0;
        showHidden = false;
        showDimension = false;
    }

    public double getDimension() {
        return dimension;
    }

    public void setDimension() throws IOException {
        File file = new File(Command.getCwd() + "/" + this.name);
        Path filePath = file.toPath();

        // Gestione dei link simbolici
        if (Files.isSymbolicLink(filePath)) {
            try {
                if (!Files.exists(filePath.toRealPath())) {
                    dimension = 0;
                    return;
                }
            } catch (IOException e) {
                dimension = 0;
                return;
            }
        }

        Stat stat = new Stat();
        int result = CStat.INSTANCE.stat(Command.getCwd() + "/" + this.name, stat);

        if (result == 0) {
            dimension = stat.st_blocks / 2;
        } else {
            System.err.println("Errore durante l'esecuzione di stat.");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static boolean isShowHidden() {
        return showHidden;
    }

    public static void setShowHidden(boolean showHidden) {
        ListElement.showHidden = showHidden;
    }

    public static boolean isShowDimension() {
        return showDimension;
    }

    public static void setShowDimension(boolean showDimension) {
        ListElement.showDimension = showDimension;
    }

    @Override
    public String toString() {
        if (!ListElement.isShowHidden() && name.startsWith(".")) {
            return "";
        } else if (ListElement.isShowDimension()) {
            return getDimension() + " " + getName();
        } else {
            return getName();
        }
    }
}
