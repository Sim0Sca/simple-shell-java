package com.shish;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

public class LS extends Command {

    public LS(String ls) {
        setCommand(ls);
        this.aliases = new ArrayList<>();
        this.parameters = new ArrayList<>();
    }

    @Override
    protected void execute() throws IOException {
        String dir = cwd;
        File cwdObj = new File(cwd);
        String[] files = cwdObj.list();

        if (files != null) {
            // Probably not the best solution, bu the simplest for me
            Stream<String> sorted = Arrays.stream(files).sorted(); // Sorting
            Object[] array_sort = sorted.toArray(); // Conversion to array
            ArrayList<ListElement> dir_sorted = new ArrayList<>(); // Conversion to strings
            for (Object el : array_sort) {
                dir_sorted.add(new ListElement(el.toString()));
            }

            // Aliases
            for (String alias : aliases) {
                if (alias.contains("a")) {
                    // Show hidden files
                    ListElement.setShowHidden(true);
                }

                if (alias.contains("s")) {
                    ListElement.setShowDimension(true);
                    for (ListElement el : dir_sorted) {
                        el.setDimension();
                    }
                }
            }

            // Print list
            for (ListElement el : dir_sorted) {
                if (!el.toString().isEmpty()) {
                    System.out.println(el.toString());
                }
            }

        }

        reset();
    }

    // TODO: Add . and .. to -s
    // TODO: Add total dimension to -s
    // TODO: -a (size readable format)
    // TODO: -l (column format)
}
