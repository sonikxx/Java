package edu.school21.numbers;

public class NumberWorker {
    public boolean isPrime(int number) {
        if (number <= 1) {
            throw new IllegalNumberException("Prime number must be > 1");
        }
        boolean isPrime = true;
        int iter = 2;
        for (; iter * iter <= number; ++iter) {
            if (number % iter == 0) {
                isPrime = false;
                break;
            }
        }
        return isPrime;
    }

    public int digitsSum(int number) {
        int sum = 0;
        while (number != 0) {
            sum += number % 10;
            number /= 10;
        }
        return sum;
    }
}
