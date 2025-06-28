package com.shish;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.UserPrincipal;
import java.util.ArrayList;
import java.util.Arrays;

public class StatCommand extends Command {

    Stat stat;

    public StatCommand(String statCommand) {
        setCommand(statCommand);
        this.aliases = new ArrayList<>();
        this.parameters = new ArrayList<>();
        stat = new Stat();
    }

    @Override
    protected void execute() throws IOException {
        if (parameters.isEmpty()) {
            System.out.println("stat: missing operator");
        } else {
            //Stat stat = new Stat();
            int result = CStat.INSTANCE.stat(Command.getCwd() + "/" + parameters.getFirst(), stat);

            if (result == 0) {
                // Take attributes
                System.out.println(this);
            } else {
                System.err.println("Error during stat execution.");
            }
        }

        reset();
    }

    @Override
    public String toString() {
        File file = new File(Command.getCwd() + "/" + parameters.getFirst());
        Path filePath = Path.of(file.toString());

        UserPrincipal owner = null;
        String group = null;

        try {
            owner = Files.getOwner(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            group = Files.getAttribute(filePath, "posix:group").toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return "" +
                "File: " + file.getName() + "\n" +
                "Dim. " + stat.st_size + " Blocks: " + stat.st_blocks / 2 + " I/O block: " + stat.st_blksize + "\n" +
                "Device: " + Long.toHexString(stat.st_dev) + "/" + stat.st_dev + " Inode: " + stat.st_ino + " Coll.: " + stat.st_nlink + "\n" +
                "Access: " + getPermission(stat.st_mode) +
                "Uid: ( " + stat.st_uid + "/ " + owner.toString() + ") Gid: ( " + stat.st_gid + "/ " + group + ")" + "\n" +
                "Last access: " + TimestampFormatter.formatTimestamp(stat.st_atim.tv_sec) + "\n" +
                "Last edit: " + TimestampFormatter.formatTimestamp(stat.st_mtim.tv_sec) + "\n" +
                "Status change: " + TimestampFormatter.formatTimestamp(stat.st_ctim.tv_sec) + "\n";
    }

    public static String getPermission(int mode) {
        StringBuilder permissions = new StringBuilder();
        int[] number_perm = {0, 0, 0};

        if ((mode & 0xF000) == 0x4000) permissions.append('d'); // Directory
        else if ((mode & 0xF000) == 0x8000) permissions.append('-'); // File
        else if ((mode & 0xF000) == 0xA000) permissions.append('?'); // Symbolic link
        else permissions.append('?'); // Others not recognised

        // User permissions
        if ((mode & 0x0100) != 0) {
            permissions.append('r');
            number_perm[0] += 4;
        } else {
            permissions.append('-');
        }
        if ((mode & 0x0080) != 0) {
            permissions.append('w');
            number_perm[0] += 2;
        } else {
            permissions.append('-');
        }
        if ((mode & 0x0040) != 0) {
            if ((mode & 0x0800) != 0) {
                permissions.append('s');
                number_perm[0] += 1;
            } else {
                permissions.append('x');
                number_perm[0] += 1;
            }
        } else {
            permissions.append('-');
        }

        // Group permissions
        if ((mode & 0x0020) != 0) {
            permissions.append('r');
            number_perm[1] += 4;
        } else {
            permissions.append('-');
        }
        if ((mode & 0x0010) != 0) {
            permissions.append('w');
            number_perm[1] += 2;
        } else {
            permissions.append('-');
        }
        if ((mode & 0x0008) != 0) {
            if ((mode & 0x0200) != 0) {
                permissions.append('s');
                number_perm[1] += 1;
            } else {
                permissions.append('x');
                number_perm[1] += 1;
            }
        } else {
            permissions.append('-');
        }

        // Other permissions
        if ((mode & 0x0004) != 0) {
            permissions.append('r');
            number_perm[2] += 4;
        } else {
            permissions.append('-');
        }
        if ((mode & 0x0002) != 0) {
            permissions.append('w');
            number_perm[2] += 2;
        } else {
            permissions.append('-');
        }
        if ((mode & 0x0001) != 0) {
            if ((mode & 0x0200) != 0) {
                permissions.append('s');
                number_perm[2] += 1;
            } else {
                permissions.append('x');
                number_perm[2] += 1;
            }
        } else {
            permissions.append('-');
        }

        return "(0" + number_perm[0] + number_perm[1] + number_perm[2] + "/" + permissions + ")";
    }
}
