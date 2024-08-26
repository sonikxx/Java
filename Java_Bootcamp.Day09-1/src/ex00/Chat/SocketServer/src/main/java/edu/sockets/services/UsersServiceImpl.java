package edu.school21.sockets.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.UsersRepository;

@Component("UsersServiceImpl")
public class UsersServiceImpl implements UsersService {
    private UsersRepository usersRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void signUp(String email, String password) {
        User user = new User(email, passwordEncoder.encode(password));
        usersRepository.save(user);
    }
}
