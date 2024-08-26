package edu.school21.chat.app;

import java.util.Optional;
import java.util.Scanner;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;
import edu.school21.chat.models.Message;

public class Program {
    public static void main(String[] args) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/chat");
        config.setUsername("postgres");
        config.setPassword("123");
        config.setDriverClassName("org.postgresql.Driver");
        HikariDataSource dataSource = new HikariDataSource(config); // пул соединений HikariCP

        Scanner in = new Scanner(System.in);
        System.out.println("Enter a message ID");
        System.out.print("-> ");
        long id = Long.parseLong(in.nextLine());
        MessagesRepositoryJdbcImpl messagesRepository = new MessagesRepositoryJdbcImpl(dataSource);
        Optional<Message> result = messagesRepository.findById(id);
        if (result.isPresent())
            System.out.println(result.get());
        else
            System.out.println("Message with id=" + id + " not found");
    }
}
