import java.util.Random;

public class Program {
    public static void main(String[] args) {
        if (args.length != 2 || !args[0].startsWith("--arraySize=") || !args[1].startsWith("--threadsCount=")) {
            System.err.println("Usage: java Program --arraySize=13 --threadsCount=3");
            System.exit(-1);
        }
        Integer arraySize = Integer.parseInt(args[0].split("=")[1]);
        Integer threadsCount = Integer.parseInt(args[1].split("=")[1]);
        if (arraySize > 2000000 || arraySize <= 0 || threadsCount > arraySize || threadsCount <= 0) {
            System.err.println("Invalid arguments");
            System.exit(-1);
        }
        Summarization calc = new Summarization(arraySize, threadsCount);
        calc.calculateSum();
    }
}