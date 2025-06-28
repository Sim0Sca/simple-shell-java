package com.shish;

import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

public class Timespec extends Structure {
    public long tv_sec;  // seconds
    public long tv_nsec; // nanoseconds

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList("tv_sec", "tv_nsec");
    }
}
