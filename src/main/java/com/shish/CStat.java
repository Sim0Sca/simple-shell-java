package com.shish;

import com.sun.jna.Library;
import com.sun.jna.Native;

public interface CStat extends Library {
    // JNA will automatically load the C library
    CStat INSTANCE = Native.load("c", CStat.class);

    // Mapping the C function: int stat(const char*, struct stat*)
    int stat(String pathname, Stat statbuf);
}
