package edu.school21.sockets.client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private Scanner scanner;

    public Client(int port) {
        try {
            scanner = new Scanner(System.in);
            this.socket = new Socket("localhost", port);
            InputStream inputStream = socket.getInputStream();
            in = new BufferedReader(new InputStreamReader(inputStream));
            OutputStream outputStream = socket.getOutputStream();
            out = new PrintWriter(outputStream, true); // auto flush()
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(-1);
        }
    }

    public void start() {
        try {
            while (true) {
                String response = in.readLine();
                System.out.println(response);
                if (response.startsWith("Error:"))
                    System.exit(-1);
                if (response.equals("Successful!")) {
                    closeAll();
                    break;
                }
                String message = scanner.nextLine();
                out.println(message);
            }
        } catch (Exception e) {
            closeAll();
            System.err.println("Error: " + e.getMessage());
            System.exit(-1);
        }
    }

    private void closeAll() {
        try {
            if (in != null)
                in.close();
            if (out != null)
                out.close();
            if (socket != null)
                socket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }
    }
}
