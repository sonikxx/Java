import java.util.*;
import java.io.*;
import java.nio.file.*;

public class CommandLine {

    private static CommandLine instance;

    private String path;

    public static CommandLine getInstance(String path) {
        if (instance == null) {
            instance = new CommandLine();
        }
        instance.path = path;
        return instance;
    }

    public void ls() {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(path))) {
            for (Path entry : stream) {
                System.out.print(entry.getFileName() + " ");
                if (Files.isDirectory(entry)) {
                    // long sizeFolder = Files.walk(entry)
                    // .filter(p -> p.toFile().isFile())
                    // .mapToLong(p -> p.toFile().length())
                    // .sum();
                    System.out.print(getFolderSize(entry.toFile()) / 1000.0 + " KB\n");
                } else
                    System.out.print(Files.size(entry) / 1000.0 + " KB\n");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }
    }

    private long getFolderSize(File folder) {
        long length = 0;
        File[] files = folder.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile())
                length += files[i].length();
            else
                length += getFolderSize(files[i]);
        }
        return length;
    }

    public void cd(String cdPath) {
        try {
            Path curPath = Paths.get(instance.path);
            Path newPath = curPath.resolve(cdPath).toRealPath();
            if (Files.isDirectory(newPath)) {
                instance.path = newPath.toString();
                System.out.println(instance.path);
            } else
                throw new IllegalArgumentException(cdPath);
        } catch (Exception e) {
            System.err.println("Folder " + e.getMessage() + " doesn't exist");
            System.exit(-1);
        }
    }

    public void mv(String what, String where) {
        try {
            Path curPath = Paths.get(instance.path);
            Path whatPath = curPath.resolve(what).toRealPath();
            if (Files.notExists(whatPath))
                throw new IllegalArgumentException(what);
            Path wherePath = curPath.resolve(where);
            if (Files.isDirectory(wherePath)) {
                Path target = wherePath.toRealPath().resolve(Paths.get(what).getFileName());
                Files.move(whatPath, target, StandardCopyOption.REPLACE_EXISTING);
            } else
                Files.move(whatPath, wherePath);
        } catch (Exception e) {
            System.err.println(e.getClass());
            System.err.println(e.getMessage());
            System.exit(-1);
        }
    }
}
