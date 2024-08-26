package edu.school21.chat.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.UsersRepositoryJdbcImpl;
import edu.school21.chat.repositories.NotSavedSubEntityException;

import java.util.List;

public class Program {
    public static void main(String[] args) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/chat");
        config.setUsername("postgres");
        config.setPassword("123");
        config.setDriverClassName("org.postgresql.Driver");
        HikariDataSource dataSource = new HikariDataSource(config); // пул соединений HikariCP
        UsersRepositoryJdbcImpl usersRepository = new UsersRepositoryJdbcImpl(dataSource);
        List<User> userList = usersRepository.findAll(1, 3);
        for (User user: userList) {
            System.out.println(user);
        }
    }
}
