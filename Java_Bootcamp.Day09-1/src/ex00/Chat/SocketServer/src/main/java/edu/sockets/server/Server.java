package edu.school21.sockets.server;

import edu.school21.sockets.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

@Component("Server")
public class Server {
    private UsersService usersService;
    private BufferedReader in;
    private  PrintWriter out;
    private ServerSocket serverSocket;
    private Socket socket;

    @Autowired
    public Server(UsersService usersService) {
        this.usersService = usersService;
    }

    public void start(int port) {
        try {
            serverSocket= new ServerSocket(port);
            // Блокирует до тех пор, пока не возникнет соединение:
            socket = serverSocket.accept();
            InputStream inputStream = socket.getInputStream();
            in = new BufferedReader(new InputStreamReader(inputStream));
            OutputStream outputStream = socket.getOutputStream();
            out = new PrintWriter(outputStream, true); // auto flush()
            out.println("Hello from Server!");
            String inputMessage = in.readLine();
            if (!inputMessage.equals("signUp"))
                throw new RuntimeException("usage signUp");
            out.println("Enter username:");
            String userName = in.readLine();
            out.println("Enter password:");
            String userPassword = in.readLine();
            usersService.signUp(userName, userPassword);
            out.println("Successful!");
            closeAll();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            out.println("Error: " + e.getMessage());
            closeAll();
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
            if (serverSocket != null)
                serverSocket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }
    }
}
