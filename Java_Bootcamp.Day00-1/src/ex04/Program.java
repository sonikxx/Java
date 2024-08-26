import java.util.Scanner;

class Program {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
        int[] countChar = new int[65535];
        for (char c : input.toCharArray()) {
            ++countChar[c];
        }
        char[] topChar = new char[10];
        int[] topCount = new int[10];
        for (int i = 0; i < 10; ++i) {
            int max = 0;
            int indMax = 0;
            for (int j = 0; j < 65535; ++j) {
                if (countChar[j] > max) {
                    indMax = j;
                    max = countChar[j];
                }
            }
            topCount[i] = max;
            topChar[i] = (char) indMax;
            countChar[indMax] = 0;
        }
        if (topChar[0] > 999) {
            System.err.println("Illegal Argument");
            in.close();
            System.exit(-1);
        }
        printGraph(topChar, topCount);
        in.close();
    }

    static void printGraph(char[] topChar, int[] topCount) {
        double step = topCount[0] / 10.0;
        for (int i = 0; i < 11; ++i) {
            for (int j = 0; j < 10; ++j) {
                if (topCount[j] >= step * (11 - i)) {
                    System.out.print(" # ");
                } else if (topCount[j] >= step * (10 - i)) {
                    if (topCount[j] < 10)
                        System.out.print(" ");
                    System.out.print(topCount[j] + " ");
                }
            }
            System.out.println();
        }
        for (int j = 0; j < 10; ++j) {
            System.out.print(" " + topChar[j] + " ");
        }
        System.out.println();
    }
}