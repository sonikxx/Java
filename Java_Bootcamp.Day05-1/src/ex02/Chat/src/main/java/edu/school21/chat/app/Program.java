package edu.school21.chat.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.repositories.NotSavedSubEntityException;

import java.sql.Timestamp;
import java.util.Date;
import java.util.ArrayList;

public class Program {
    public static void main(String[] args) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/chat");
        config.setUsername("postgres");
        config.setPassword("123");
        config.setDriverClassName("org.postgresql.Driver");
        HikariDataSource dataSource = new HikariDataSource(config); // пул соединений HikariCP

        MessagesRepositoryJdbcImpl messagesRepository = new MessagesRepositoryJdbcImpl(dataSource);
        User author = new User(4L, "User1", "111", new ArrayList(), new ArrayList());
        User creator = new User(2L, "User2", "222", new ArrayList(), new ArrayList());
        Chatroom room = new Chatroom(2L, "Chat2", creator, new ArrayList());
        Message message = new Message(null, author, room, "Hello",  new Timestamp(new Date().getTime()));
        messagesRepository.save(message);
        System.out.println("New message id: " + message.getId());
        System.out.println("Author with undiscovered id:");
        try {
            author.setId(1L);
            Message messageTest = new Message(null, author, room, "Bye!",  new Timestamp(new Date().getTime()));
            messagesRepository.save(messageTest);
        } catch (NotSavedSubEntityException e) {
            System.err.println("Error: " + e.getMessage());
        }
        System.out.println("Room with undiscovered id:");
        try {
            author.setId(4L);
            room.setId(20L);
            Message messageTest = new Message(null, author, room, "Hello!",  new Timestamp(new Date().getTime()));
            messagesRepository.save(messageTest);
        } catch (NotSavedSubEntityException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
