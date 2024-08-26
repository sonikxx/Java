package edu.school21.sockets.services;

import edu.school21.sockets.models.Message;
import edu.school21.sockets.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.UsersRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Component("UsersServiceImpl")
public class UsersServiceImpl implements UsersService {
    private UsersRepository usersRepository;
    private PasswordEncoder passwordEncoder;
    private MessageRepository messageRepository;

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository, PasswordEncoder passwordEncoder, MessageRepository messageRepository) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.messageRepository = messageRepository;
    }

    @Override
    public void signUp(String email, String password) {
        if (usersRepository.findByEmail(email).isPresent())
            throw new RuntimeException("User with login " + email +" already exist");
        User user = new User(email, passwordEncoder.encode(password));
        usersRepository.save(user);
    }

    @Override
    public Long signIn(String email, String password) {
        Optional<User> user = usersRepository.findByEmail(email);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword()) )
            return user.get().getId();
        return null;
    }

    @Override
    public void createMessage(User sender, String text) {
        LocalDateTime now = LocalDateTime.now();
        messageRepository.save(new Message(sender.getId(), text, Timestamp.valueOf(now)));
    }

    @Override
    public Optional<User> getUser(String email) {
        return usersRepository.findByEmail(email);
    }
}
