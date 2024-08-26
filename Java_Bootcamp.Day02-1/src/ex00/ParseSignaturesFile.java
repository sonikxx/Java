import java.io.*;
import java.util.*;

public class ParseSignaturesFile {
    private Map<String, String> signatures;

    public ParseSignaturesFile() {
        signatures = new HashMap<>();
    }

    public Map<String, String> getSignatures() {
        return signatures;
    }

    public void readFile(String path) {
        try (FileInputStream fin = new FileInputStream(path);) {// try-with-resources
            Scanner scanner = new Scanner(fin);
            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split(", ");
                signatures.put(line[1], line[0]);
            }
            scanner.close();
        } catch (IOException e) {
            System.err.println(path + " not found");
        }

    }

}