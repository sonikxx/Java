package edu.school21.chat.repositories;

import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsersRepositoryJdbcImpl implements UsersRepository {
    private HikariDataSource dataSource;

    public UsersRepositoryJdbcImpl(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<User> findAll(int page, int size) {
        List<User> userList = new ArrayList<>();
        String query = "SELECT * FROM chat.user LIMIT ? OFFSET ?";
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement(query)) {
            statement.setInt(1, size);
            statement.setInt(2, page * size);
            statement.execute();
            ResultSet resultUser = statement.getResultSet();
            while (resultUser.next()) {
                List<Chatroom> createdRooms = findCreatedRooms(resultUser.getLong("id"));
                List<Chatroom> usedRooms = findUsedRooms(resultUser.getLong("id"));
                userList.add(new User(resultUser.getLong("id"), resultUser.getString("login"), resultUser.getString("password"), createdRooms, usedRooms));
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return userList;
    }

    private List<Chatroom> findCreatedRooms(long UserId) {
        List<Chatroom> roomList = new ArrayList<>();
        String query = "SELECT * FROM chat.room WHERE owner = ?";
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement(query)) {
            statement.setLong(1, UserId);
            ResultSet resultRooms = statement.executeQuery();
            while (resultRooms.next()) {
                roomList.add(new Chatroom(resultRooms.getLong("id"), resultRooms.getString("name"), null, null));
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return roomList;
    }

    private List<Chatroom>  findUsedRooms(long UserId) {
        List<Chatroom> roomList = new ArrayList<>();
        String query = "SELECT * FROM chat.message\n" +
                "INNER JOIN chat.room ON (chat.room.id = chat.message.room)\n" +
                "WHERE author = ?";
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement(query)) {
            statement.setLong(1, UserId);
            ResultSet resultRooms = statement.executeQuery();
            while (resultRooms.next()) {
                roomList.add(new Chatroom(resultRooms.getLong("id"), resultRooms.getString("name"), null, null));
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return roomList;
    }

}
