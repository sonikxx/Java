package school21.spring.service.repositories;

import school21.spring.service.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcImpl implements UsersRepository {
    DataSource dataSource;

    public UsersRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<User> findById(Long id) {
        String query = "SELECT * FROM service WHERE id = ?";
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            if (!result.next()) {
                result.close();
                return Optional.empty();
            }
            Optional<User> user = Optional.of(new User(id, result.getString(2)));
            result.close();
            return user;
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(-1);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String query = "SELECT * FROM service WHERE email = ?";
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement(query)) {
            statement.setString(1, email);
            ResultSet result = statement.executeQuery();
            if (!result.next()) {
                result.close();
                return Optional.empty();
            }
            Optional<User> user = Optional.of(new User(result.getLong(1), email));
            result.close();
            return user;
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(-1);
        }
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        List<User> listUser = new ArrayList<>();
        String query = "SELECT * FROM service";
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement(query)) {
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                User user = new User(result.getLong(1), result.getString(2));
                listUser.add(user);
            }
            result.close();
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(-1);
        }
        return listUser;
    }

    @Override
    public void save(User entity) {
        if (entity == null)
            throw new RuntimeException("Invalid save null entity");
        String query = "INSERT INTO service (id, email) VALUES(?, ?)";
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement(query)) {
            statement.setLong(1, entity.getId());
            statement.setString(2, entity.getEmail());
            int affectedRows = statement.executeUpdate();
            if (affectedRows != 1) {
                throw new RuntimeException("Invalid save user id=" + entity.getId());
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(-1);
        }
    }

    @Override
    public void update(User entity) {
        if (entity == null)
            throw new RuntimeException("Invalid update null entity");
        String query = "UPDATE service SET email = ? WHERE id = ?";
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement(query)) {
            statement.setLong(2, entity.getId());
            statement.setString(1, entity.getEmail());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0)
                throw new RuntimeException("Invalid update user id=" + entity.getId());
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(-1);
        }
    }

    @Override
    public void delete(Long id) {
        String query = "DELETE FROM service WHERE id = ?";
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement(query)) {
            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0)
                throw new RuntimeException("Invalid delete user id=" + id);
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(-1);
        }
    }
}
