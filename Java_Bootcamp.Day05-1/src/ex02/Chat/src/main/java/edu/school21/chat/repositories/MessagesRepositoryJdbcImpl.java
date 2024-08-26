package edu.school21.chat.repositories;

import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;
import edu.school21.chat.models.Chatroom;

import java.sql.PreparedStatement;
import java.util.Optional;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {
    private HikariDataSource dataSource;

    public MessagesRepositoryJdbcImpl(HikariDataSource dataSource) {
       this.dataSource = dataSource;
    }

    @Override
    public Optional<Message> findById(Long id) {
        Optional<Message> messages;
        try (Statement statementMessage = dataSource.getConnection().createStatement()) {
            ResultSet resultMessage = statementMessage.executeQuery("SELECT * FROM chat.message WHERE id =" + id);
            if (!resultMessage.next())
                return Optional.empty();
            User author = findUserById(resultMessage.getLong("author"));
            Chatroom room = findRoomById(resultMessage.getLong("room"));
            return Optional.of(new Message(resultMessage.getLong("id"), author, room, resultMessage.getString("text"), resultMessage.getTimestamp("date_time")));
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return Optional.empty();
    }

    private User findUserById(Long id) {
        try(Statement statementUser = dataSource.getConnection().createStatement()) {
            ResultSet resultUser = statementUser.executeQuery("SELECT * FROM chat.user WHERE id =" + id);
            if (!resultUser.next())
                return null;
            return (new User(resultUser.getLong("id"), resultUser.getString("login"), resultUser.getString("password"), null, null));
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return null;
    }

    private Chatroom findRoomById(Long id) {
        try(Statement statementRoom = dataSource.getConnection().createStatement()) {
            ResultSet resultRoom = statementRoom.executeQuery("SELECT * FROM chat.room WHERE id =" + id);
            if (!resultRoom.next())
                return null;
            return (new Chatroom(resultRoom.getLong("id"), resultRoom.getString("name"), null, null));
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void save(Message message) {
        if (message.getAuthor().getId() == null || findUserById(message.getAuthor().getId()) == null)
                throw new NotSavedSubEntityException ("Author with id=" + message.getAuthor().getId() + " not found");
        if (message.getChatRoom().getId() == null || findRoomById(message.getChatRoom().getId()) == null)
            throw new NotSavedSubEntityException ("ChatRoom with id=" + message.getChatRoom().getId() + " not found");
        String query = "INSERT INTO chat.message(author, room, text) VALUES (?, ?, ?)";
        try (PreparedStatement statement  = dataSource.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, message.getAuthor().getId());
            statement.setLong(2, message.getChatRoom().getId());
            statement.setString(3, message.getText());
            int affectedRows = statement.executeUpdate();
            if (affectedRows != 1)
                throw new SQLException("Insertion failed");
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                long id = generatedKeys.getLong("id");
                message.setId(id);
            } else
                throw new SQLException("Couldn't assign the message id");
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
