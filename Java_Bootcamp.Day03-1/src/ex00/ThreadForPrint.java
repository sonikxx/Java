public class ThreadForPrint implements Runnable {

    private String print;
    private Integer count;

    public ThreadForPrint(String print, Integer count) {
        this.print = print;
        this.count = count;
    }

    @Override
    public void run() {
        for (int i = 0; i < count; i++) {
            System.out.println(print);
        }
    }
}