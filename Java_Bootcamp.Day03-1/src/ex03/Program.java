import java.util.*;

public class Program {
    public static void main(String[] args) {
        if (args.length != 1 || !args[0].startsWith("--threadsCount=")) {
            System.err.println("Usage: java Program.java --threadsCount=3");
            System.exit(-1);
        }
        Integer threadsCount = Integer.parseInt(args[0].split("=")[1]);
        if (threadsCount <= 0) {
            System.err.println("Invalid arguments");
            System.exit(-1);
        }
        ReaderFile readerFile = new ReaderFile();
        Queue<Integer> queueDownloadFile = new LinkedList<>();
        Map<Integer, String> mapdownloadFile = readerFile.pars("files_urls.txt", queueDownloadFile);
        createThreads(mapdownloadFile, queueDownloadFile, threadsCount);
    }

    private static void createThreads(Map<Integer, String> mapdownloadFile, Queue<Integer> queueDownloadFile,
            Integer threadsCount) {
        Thread[] threads = new Thread[threadsCount];
        for (int i = 0; i < threadsCount; i++) {
            threads[i] = new Thread(new ThreadForDownload(mapdownloadFile, queueDownloadFile, i));
            threads[i].start();
        }
        for (int i = 0; i < threadsCount; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}