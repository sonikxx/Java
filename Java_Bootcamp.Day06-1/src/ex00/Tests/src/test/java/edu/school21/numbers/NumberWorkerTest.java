package edu.school21.numbers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class NumberWorkerTest {
    NumberWorker numberWorker;

    @BeforeEach
    public void beforeInit() {
        numberWorker = new NumberWorker();
    }

    @ParameterizedTest
    @ValueSource(ints = { 2, 73, 109, 569, 859 })
    public void isPrimeForPrimes(int number) {
        assertTrue(numberWorker.isPrime(number));
    }

    @ParameterizedTest
    @ValueSource(ints = { 10, 4, 228, 396, 703})
    public void isPrimeForNotPrimes(int number) {
        assertFalse(numberWorker.isPrime(number));
    }

    @ParameterizedTest
    @ValueSource(ints = { -102121, -123, -1, 0, 1 })
    public void isPrimeForIncorrectNumbers(int number) {
        assertThrows(IllegalNumberException.class, () -> {
            numberWorker.isPrime(number); // lambda expression
        });
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv")
    public void CsvFileDigitsSum(int real, int expected) {
        assertEquals(numberWorker.digitsSum(real), expected);
    }

}
