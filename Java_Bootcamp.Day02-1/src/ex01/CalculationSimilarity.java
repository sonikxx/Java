import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

public class CalculationSimilarity {
    private String file1;
    private String file2;
    private String output;
    private Set<String> dictionary;

    public CalculationSimilarity(String file1, String file2, String output) {
        this.file1 = file1;
        this.file2 = file2;
        this.output = output;
        dictionary = new HashSet<>();
    }

    public String getFile1() {
        return file1;
    }

    public String getFile2() {
        return file2;
    }

    public String getOutput() {
        return output;
    }

    public void setFile1(String file1) {
        this.file1 = file1;
    }

    public void setFile2(String file2) {
        this.file2 = file2;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public void calc() {
        try (BufferedWriter bWriter = new BufferedWriter(new FileWriter(output))) {
            readFile(file1);
            readFile(file2);
            writeDictionary(bWriter);
            int[] vector1 = getArray(file1);
            int[] vector2 = getArray(file2);
            printSimilarity(vector1, vector2);
        } catch (IOException e) {
            System.err.println("Output file " + output + " not found");
            System.exit(-1);
        }
    }

    private void readFile(String path) {
        try (BufferedReader bReader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = bReader.readLine()) != null) {
                String[] splitLine = line.split(" ");
                for (String word : splitLine) {
                    dictionary.add(word);
                }
            }
        } catch (IOException e) {
            System.err.println(path + " not found");
            System.exit(-1);
        }
    }

    private void writeDictionary(BufferedWriter bWriter) {
        try {
            for (String word : dictionary) {
                bWriter.write(word + " ");
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }
    }

    private int[] getArray(String path) {
        int[] res = new int[dictionary.size()];
        try (BufferedReader bReader = new BufferedReader(new FileReader(path))) {
            String line;
            String[] dictionareArray = dictionary.toArray(new String[0]); // <T> T[] toArray(T[] a)
            while ((line = bReader.readLine()) != null) {
                String[] splitLine = line.split(" ");
                for (String word : splitLine) {
                    for (int i = 0; i < dictionareArray.length; ++i) {
                        if (dictionareArray[i].equals(word)) {
                            res[i]++;
                            break;
                        }

                    }
                }
            }
        } catch (IOException e) {
            System.err.println(path + " not found");
            System.exit(-1);
        }
        return res;
    }

    private void printSimilarity(int[] vector1, int[] vector2) {
        double similarity = 0;
        double normA = 0;
        double normB = 0;
        for (int i = 0; i < vector1.length; ++i) {
            similarity += vector1[i] * vector2[i];
            normA += Math.pow(vector1[i], 2);
            normB += Math.pow(vector2[i], 2);
        }
        similarity /= (Math.sqrt(normA) * Math.sqrt(normB));
        System.out.println("Similarity = " + similarity);
        // System.out.println("Similarity = " + String.format("%.2f", similarity));
        // System.out.println("Similarity = " + Math.floor(similarity * 100.00) /
        // 100.00);
    }

}