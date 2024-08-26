package edu.school21.services;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;
import javax.persistence.EntityNotFoundException;

public class UsersServiceImpl {
    UsersRepository usersRepository;

    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }
    boolean authenticate(String login, String password) {
        User user;
        try {
            user = usersRepository.findByLogin(login);
        } catch (EntityNotFoundException e) {
            System.err.println("Error: user with login=" + login + " not found");
            return false;
        }
        if (user.getAuthenticationStatus())
            throw new AlreadyAuthenticatedException();
        if (user.getPassword().equals(password)) {
            user.setAuthenticationStatus(true);
            try {
                usersRepository.update(user);
            } catch (EntityNotFoundException e) {
                System.err.println("Error: user with login=" + login + " not found");
                return false;
            }
            return true;
        }
        return false;
    }
}
