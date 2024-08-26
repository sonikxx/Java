public class MyThread implements Runnable {

    private int[] array;
    private Integer start;
    private Integer end;
    private Integer i;
    private Integer sum;

    public MyThread(int[] array, Integer start, Integer end, Integer i) {
        this.array = array;
        this.start = start;
        this.end = end;
        this.i = i;
        this.sum = 0;
    }

    public Integer getSum() {
        return sum;
    }

    @Override
    public void run() {
        for (int k = start; k <= end; ++k) {
            sum += array[k];
        }
        System.out.println("Thread " + i + ": from " + start + " to " + end + " sum is " + sum);
        Summarization.sum += sum;
    }
}