import java.io.*;
import java.net.*;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ThreadForDownload implements Runnable {
    private Map<Integer, String> mapDownloadFile;
    private Queue<Integer> queueDownloadFile;
    private Integer i;

    public ThreadForDownload(Map<Integer, String> mapDownloadFile, Queue<Integer> queueDownloadFile, Integer i) {
        this.mapDownloadFile = mapDownloadFile;
        this.queueDownloadFile = queueDownloadFile;
        this.i = i + 1;
    }

    @Override
    public void run() {
        while (true) {
            Integer id;
            synchronized (queueDownloadFile) {
                if (queueDownloadFile.isEmpty()) {
                    break;
                }
                id = queueDownloadFile.poll(); // elem from head of queue
            }
            try {
                URL url = new URL(mapDownloadFile.get(id));
                System.out.println("Thread-" + i + " start download file number " + id);
                InputStream inputStream = url.openStream(); // open connection to this URL and returns an InputStream
                                                            // for reading from that connection
                Files.copy(inputStream, new File(Paths.get(url.getPath()).getFileName().toString()).toPath());
                System.out.println("Thread-" + i + " finished download file number " + id);
            } catch (Exception e) {
                System.err.println(e.getMessage());
                System.exit(-1);
            }
        }
    }
}