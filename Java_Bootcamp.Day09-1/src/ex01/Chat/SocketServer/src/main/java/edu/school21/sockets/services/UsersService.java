package edu.school21.sockets.services;

import edu.school21.sockets.models.User;

import java.util.Optional;

public interface UsersService {
    void signUp(String email, String password);
    Long signIn(String email, String password);
    void createMessage(User sender, String text);
    Optional<User> getUser(String email);
}
