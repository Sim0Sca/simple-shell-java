package com.shish;

import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;


@Structure.FieldOrder({
        "st_dev", "st_ino", "st_nlink",
        "st_mode", "st_uid", "st_gid", "__pad0",
        "st_rdev", "st_size", "st_blksize", "st_blocks",
        "st_atim", "st_mtim", "st_ctim",
        "__glibc_reserved0", "__glibc_reserved1", "__glibc_reserved2"
})
public class Stat extends Structure {
    public long st_dev;         // dev_t
    public long st_ino;         // ino_t
    public long st_nlink;       // nlink_t
    public int  st_mode;        // mode_t
    public int  st_uid;         // uid_t
    public int  st_gid;         // gid_t
    public int  __pad0;         // padding
    public long st_rdev;        // dev_t
    public long st_size;        // off_t
    public long st_blksize;     // blksize_t
    public long st_blocks;      // blkcnt_t
    public Timespec st_atim;    // last access time
    public Timespec st_mtim;    // last mod time
    public Timespec st_ctim;    // last status change time
    public long __glibc_reserved0;  // riservati per compatibilità
    public long __glibc_reserved1;
    public long __glibc_reserved2;
}
