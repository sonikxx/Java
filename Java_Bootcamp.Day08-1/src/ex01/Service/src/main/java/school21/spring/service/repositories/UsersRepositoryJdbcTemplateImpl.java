package school21.spring.service.repositories;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import school21.spring.service.models.User;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcTemplateImpl implements UsersRepository {
    NamedParameterJdbcTemplate dataSource;

    public UsersRepositoryJdbcTemplateImpl(DataSource dataSource) {
        this.dataSource = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Optional<User> findById(Long id) {
        String query = "SELECT * FROM service WHERE id = :id";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource("id", id);
        User user = this.dataSource.queryForObject(query, namedParameters,
                new RowMapper<User>() {
                    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return new User(rs.getLong(1), rs.getString(2));
                    }
                });
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String query = "SELECT * FROM service WHERE email = :email";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource("email", email);
        User user = this.dataSource.queryForObject(query, namedParameters,
                (rs, rowNum) -> new User(rs.getLong(1), rs.getString(2)));
        return Optional.ofNullable(user);
    }

    @Override
    public List<User> findAll() {
        String query = "SELECT * FROM service";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        return this.dataSource.query(query, namedParameters,
                (rs, rowNum) -> new User(rs.getLong(1), rs.getString(2)));
    }

    @Override
    public void save(User entity) {
        if (entity == null)
            throw new RuntimeException("Invalid save null entity");
        String query = "INSERT INTO service (id, email) VALUES(:id, :email)";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id", entity.getId());
        namedParameters.addValue("email", entity.getEmail());
        int affectedRows = this.dataSource.update(query, namedParameters);
        if (affectedRows != 1)
            throw new RuntimeException("Invalid save user id=" + entity.getId());
    }

    @Override
    public void update(User entity) {
        if (entity == null)
            throw new RuntimeException("Invalid update null entity");
        String query = "UPDATE service SET email = :email WHERE id = :id";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id", entity.getId());
        namedParameters.addValue("email", entity.getEmail());
        int affectedRows = this.dataSource.update(query, namedParameters);
        if (affectedRows != 1)
            throw new RuntimeException("Invalid update user id=" + entity.getId());
    }

    @Override
    public void delete(Long id) {
        String query = "DELETE FROM service where id = :id";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id", id);
        int affectedRows = this.dataSource.update(query, namedParameters);
        if (affectedRows != 1)
            throw new RuntimeException("Invalid delete user id=" + id);
    }
}
