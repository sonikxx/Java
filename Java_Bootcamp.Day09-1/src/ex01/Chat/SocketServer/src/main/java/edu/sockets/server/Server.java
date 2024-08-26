package edu.school21.sockets.server;

import edu.school21.sockets.models.User;
import edu.school21.sockets.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

import lombok.Getter;

@Component("Server")
public class Server {
    private UsersService usersService;
    private ServerSocket serverSocket;
    private List<ClientThread> clients = new ArrayList<>();

    @Autowired
    public Server(UsersService usersService) {
        this.usersService = usersService;
    }

    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                Socket client = serverSocket.accept();
                ClientThread clientThread = new ClientThread(usersService, client);
                clients.add(clientThread);
                clientThread.start();
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            System.exit(-1);
        }
    }

    public class ClientThread extends Thread {
        private UsersService usersService;
        @Getter
        private Socket client;
        private BufferedReader in;
        @Getter
        private PrintWriter out;
        private boolean runClient;

        public ClientThread(UsersService usersService, Socket client) {
            this.usersService = usersService;
            this.client = client;
        }

        @Override
        public void run() {
            try {
                InputStream inputStream = client.getInputStream();
                in = new BufferedReader(new InputStreamReader(inputStream));
                OutputStream outputStream = client.getOutputStream();
                out = new PrintWriter(outputStream, true); // auto flush()
                out.println("Hello from Server!");
                runClient = true;
                while (runClient) {
                    out.println("Enter command:");
                    String clientCommand = in.readLine();
                    switch (clientCommand) {
                        case "signUp":
                            signUp();
                            break;
                        case "signIn":
                            signIn();
                            break;
                        case "Exit":
                            stopClient();
                            break;
                        default:
                            out.println("Wrong :( try again");
                    }
                }
            } catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
                System.exit(-1);
            }
        }

        private void signUp() {
            try {
                out.println("Enter username:");
                String userName = in.readLine();
                out.println("Enter password:");
                String userPassword = in.readLine();
                usersService.signUp(userName, userPassword);
                out.println("Successful signUp!");
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                out.println("Error: " + e.getMessage());
            }
        }

        private void signIn() {
            try {
                out.println("Enter username:");
                String userName = in.readLine();
                out.println("Enter password:");
                String userPassword = in.readLine();
                if (usersService.signIn(userName, userPassword) != null) {
                    out.println("Successful signIn!");
                    Optional<User> user = usersService.getUser(userName);
                    if (user.isPresent())
                        sendMessages(user.get());
                } else {
                    out.println("Invalid username or password :(");
                    stopClient();
                }
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                out.println("Error: " + e.getMessage());
            }
        }

        private void sendMessages(User sender) {
            try {
                out.println("Start messaging:");
                while (runClient) {
                    String text = in.readLine();
                    if (text.equals("Exit"))
                        stopClient();
                    else {
                        usersService.createMessage(sender, text);
                        for (ClientThread otherClient: clients)
                            otherClient.getOut().println(sender.getEmail() + ": " + text);
                    }
                }
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                out.println("Error: " + e.getMessage());
            }
        }

        private void stopClient() {
            this.runClient = false;
            out.println("You have left the chat.");
            synchronized (clients) {
                Iterator<ClientThread> iterator = clients.iterator();
                while (iterator.hasNext()) {
                    ClientThread otherClient = iterator.next();
                    if (client.equals(otherClient.getClient())) {
                        iterator.remove();
                    }
                }
            }
            try {
                client.close();
                in.close();
                out.close();
            } catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            ClientThread that = (ClientThread) obj;
            return this.client.equals(that.client);
        }

        @Override
        public int hashCode() {
            return Objects.hash(client);
        }

    }

}