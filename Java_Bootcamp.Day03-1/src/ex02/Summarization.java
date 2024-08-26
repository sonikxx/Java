import java.util.Random;

public class Summarization {
    private Integer arraySize;
    private Integer threadsCount;
    public static Integer sum = 0;

    private int[] array;

    public Summarization(Integer arraySize, Integer threadsCount) {
        sum = 0;
        this.arraySize = arraySize;
        this.threadsCount = threadsCount;
        this.array = new int[arraySize];
        Random rn = new Random();
        Integer max = 1000;
        Integer min = -1000;
        Integer n = max - min + 1;
        for (int i = 0; i < arraySize; i++) {
            this.array[i] = rn.nextInt() % n;
        }
    }

    public void calculateSum() {
        sumWithoutThreads();
        Integer step = arraySize / threadsCount;
        Thread[] threads = new Thread[threadsCount];
        Integer finishPrev = -1;
        for (int i = 0; i < threadsCount; ++i) {
            if (i == threadsCount - 1) {
                threads[i] = new Thread(new MyThread(array, finishPrev + 1, arraySize - 1, i + 1));
                break;
            } else {
                threads[i] = new Thread(new MyThread(array, finishPrev + 1, finishPrev + step, i + 1));
                finishPrev += step;
            }
        }
        for (int i = 0; i < threadsCount; i++) {
            threads[i].start();
        }
        for (int i = 0; i < threadsCount; ++i) { // for sum t is not printed before the threads are calculated
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Sum by threads: " + sum);
    }

    private void sumWithoutThreads() {
        Integer sum = 0;
        for (int i = 0; i < arraySize; i++) {
            sum += array[i];
        }
        System.out.println("Sum: " + sum);
    }
}