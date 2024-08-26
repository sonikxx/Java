import java.util.*;
import java.io.*;
import java.nio.file.*;

public class Program {
    public static void main(String args[]) {
        if (args.length != 1 || !args[0].startsWith("--current-folder=")) {
            System.err.println("Wrong number of arguments");
            System.exit(-1);
        }
        String path = args[0].split("=")[1];
        try (Scanner scanner = new Scanner(System.in)) {
            if (!Files.exists(Paths.get(path)))
                throw new IllegalArgumentException("Folder " + path + " doesn't exist");
            String line = scanner.nextLine();
            CommandLine commandLine = CommandLine.getInstance(path);
            while (!line.equals("exit")) {
                switch (line.split(" ")[0]) {
                    case "ls":
                        if (line.split(" ").length != 1)
                            throw new IllegalArgumentException("Wrong number of arguments");
                        commandLine.ls();
                        break;
                    case "cd":
                        if (line.split(" ").length != 2)
                            throw new IllegalArgumentException("Wrong number of arguments");
                        commandLine.cd(line.split(" ")[1]);
                        break;
                    case "mv":
                        if (line.split(" ").length != 3)
                            throw new IllegalArgumentException("Wrong number of arguments");
                        commandLine.mv(line.split(" ")[1], line.split(" ")[2]);
                        break;
                    default:
                        throw new IllegalArgumentException("Wrong command");
                }
                line = scanner.nextLine();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }
    }
}