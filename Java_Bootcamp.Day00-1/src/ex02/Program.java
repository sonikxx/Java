import java.util.Scanner;

class Program {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int number = in.nextInt();
        int counter = 0;
        while (number != 42) {
            if (isPrime(sumDigit(number)))
                ++counter;
            number = in.nextInt();
        }
        System.out.println("Count of coffee-request – " + counter);
        in.close();
    }

    static int sumDigit(int n) {
        int sum = 0;
        while (n != 0) {
            sum += n % 10;
            n /= 10;
        }
        return sum;
    }

    static boolean isPrime(int n) {
        boolean res = true;
        int iter = 2;
        for (; iter * iter <= n; ++iter) {
            if (n % iter == 0) {
                res = false;
                break;
            }
        }
        return res;
    }
}