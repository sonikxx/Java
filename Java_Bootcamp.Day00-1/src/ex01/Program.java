import java.util.Scanner;

class Program {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int number = in.nextInt();
        if (number <= 1) {
            System.err.println("Illegal Argument");
            in.close();
            System.exit(-1);
        }
        boolean isPrime = true;
        int iter = 2;
        for (; iter * iter <= number; ++iter) {
            if (number % iter == 0) {
                isPrime = false;
                break;
            }
        }
        System.out.println((isPrime ? "true " : "false ") + (iter - 1));
        in.close();
    }
}