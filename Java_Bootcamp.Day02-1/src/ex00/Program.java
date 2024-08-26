import java.util.*;

class Program {
    public static void main(String[] args) {
        ParseSignaturesFile parseSignaturesFile = new ParseSignaturesFile();
        parseSignaturesFile.readFile("./signatures.txt");
        CheckSignature checkSignature = new CheckSignature(parseSignaturesFile.getSignatures());
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String path = scanner.nextLine();
            if (path.equals("42")) {
                break;
            }
            checkSignature.checkSignature(path, "./result.txt");

        }
        scanner.close();
    }
}