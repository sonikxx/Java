package edu.school21.sockets.repositories;

import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component("MessageRepositoryImpl")
public class MessageRepositoryImpl implements MessageRepository {
    private JdbcTemplate dataSource;

    @Autowired
    public MessageRepositoryImpl(DataSource dataSource) {
        this.dataSource = new JdbcTemplate(dataSource);
        initTable();
    }

    private void initTable() {
        String queryDrop = "DROP TABLE IF EXISTS chat.message";
        String queryCreate = "CREATE TABLE IF NOT EXISTS chat.message (\n" +
                "\tid SERIAl PRIMARY KEY,\n" +
                "\tsender INTEGER REFERENCES chat.user(id),\n" +
                "\ttext VARCHAR(200),\n" +
                "\ttime TIMESTAMP\n" +
                ");\n";
        dataSource.execute(queryDrop);
        dataSource.execute(queryCreate);
    }

    @Override
    public Optional<Message> findById(Long id) {
        String query = "SELECT * FROM chat.message WHERE id = ?";
        Message message = this.dataSource.queryForObject(query, new Object[]{id}, new RowMapper<Message>() {
            public Message mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Message(rs.getLong("id"), rs.getLong("senderId"), rs.getString("text"), rs.getTimestamp("time"));
            }
        });
        return Optional.ofNullable(message);
    }

    @Override
    public List<Message> findAll() {
        String query = "SELECT * FROM chat.message";
        return this.dataSource.query(query,
                (rs, rowNum) -> new Message(rs.getLong("id"), rs.getLong("senderId"), rs.getString("text"), rs.getTimestamp("time")));
    }

    @Override
    public void save(Message entity) {
        if (entity == null)
            throw new RuntimeException("Invalid save null entity");
        String query = "INSERT INTO chat.message (sender, text, time) VALUES(?, ?, ?)";
        int affectedRows = this.dataSource.update(query, new Object[]{entity.getSenderId(), entity.getText(), entity.getTime()});
        if (affectedRows != 1)
            throw new RuntimeException("Invalid save message id=" + entity.getId());
    }

    @Override
    public void update(Message entity) {
        if (entity == null)
            throw new RuntimeException("Invalid update null entity");
        String query = "UPDATE chat.message SET sender = ?, text = ?, time = ? WHERE id = ?";
        int affectedRows = this.dataSource.update(query, new Object[]{entity.getSenderId(), entity.getText(), entity.getTime(), entity.getId()});
        if (affectedRows != 1)
            throw new RuntimeException("Invalid update message id=" + entity.getId());
    }

    @Override
    public void delete(Long id) {
        String query = "DELETE FROM chat.message where id = ?";
        int affectedRows = this.dataSource.update(query, new Object[]{id});
        if (affectedRows != 1)
            throw new RuntimeException("Invalid delete message id=" + id);
    }
}
