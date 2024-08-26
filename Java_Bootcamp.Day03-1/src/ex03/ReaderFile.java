import java.io.*;
import java.util.*;

class ReaderFile {
    public Map<Integer, String> pars(String fileName, Queue<Integer> queueRes) {
        Map<Integer, String> mapRes = new HashMap<>();
        try (BufferedReader bReader = new BufferedReader(new FileReader(fileName))) {
            String input;
            while ((input = bReader.readLine()) != null) {
                String[] line = input.split(" ");
                mapRes.put(Integer.parseInt(line[0]), line[1]);
                queueRes.add(Integer.parseInt(line[0]));
            }
        } catch (IOException e) {
            System.err.println(fileName + " not found");
            System.exit(-1);
        }
        return mapRes;
    }
}