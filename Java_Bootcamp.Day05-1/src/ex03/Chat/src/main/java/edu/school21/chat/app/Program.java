package edu.school21.chat.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;
import edu.school21.chat.models.Message;
import edu.school21.chat.repositories.NotSavedSubEntityException;

import java.util.Optional;


public class Program {
    public static void main(String[] args) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/chat");
        config.setUsername("postgres");
        config.setPassword("123");
        config.setDriverClassName("org.postgresql.Driver");
        HikariDataSource dataSource = new HikariDataSource(config); // пул соединений HikariCP
        MessagesRepositoryJdbcImpl messagesRepository = new MessagesRepositoryJdbcImpl(dataSource);
        Optional<Message> messageOptional = messagesRepository.findById(4L);
        if (messageOptional.isPresent()) {
            Message message = messageOptional.get();
            message.setText("Bye!");
            message.setDate(null);
//            message.getAuthor().setId(2L);
            message.setAuthor(null);
            try {
                messagesRepository.update(message);
            } catch (NotSavedSubEntityException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }

    }
}
