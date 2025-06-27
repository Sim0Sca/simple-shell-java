package com.shish;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

public interface CStat extends Library {
    // JNA caricherà automaticamente la libreria C
    CStat INSTANCE = Native.load("c", CStat.class);

    // Mapping della funzione C: int stat(const char*, struct stat*)
    int stat(String pathname, Stat statbuf);
}
