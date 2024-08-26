public class Program {

    public static void main(String[] args) {
        try {
            if (args.length != 2) {
                throw new IllegalArgumentException("Wrong number of arguments");
            }
            String path1 = args[0];
            String path2 = args[1];
            CalculationSimilarity calculationSimilarity = new CalculationSimilarity(path1, path2, "dictionary.txt");
            calculationSimilarity.calc();
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }
}