package edu.school21.services;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

import javax.persistence.EntityNotFoundException;

public class UsersServiceImplTest {
    public User user;
    public final String CORRECT_LOGIN = "Sofia";
    public final String CORRECT_PASSWORD = "minion";
    public final String WRONG_LOGIN = "Sofiaa";
    public final String WRONG_PASSWORD = "Minion";
    UsersRepository mockedObject = Mockito.mock(UsersRepository.class);
    UsersServiceImpl usersService = new UsersServiceImpl(mockedObject);


    @BeforeEach
    public void init() {
        user = new User(1L, CORRECT_LOGIN, CORRECT_PASSWORD, false);
        Mockito.when(mockedObject.findByLogin(CORRECT_LOGIN)).thenReturn(user);
        Mockito.when(mockedObject.findByLogin(WRONG_LOGIN)).thenThrow(new EntityNotFoundException());
        Mockito.doNothing().when(mockedObject).update(user); // для методов, которые ничего не возвращают
    }

    @Test
    public void correctLoginPassword() {
        Assertions.assertTrue(usersService.authenticate(CORRECT_LOGIN, CORRECT_PASSWORD));
    }

    @Test
    public void wrongLogin() {
        Assertions.assertFalse(usersService.authenticate(WRONG_LOGIN, CORRECT_PASSWORD));
    }

    @Test
    public void wrongPassword() {
        Assertions.assertFalse(usersService.authenticate(CORRECT_LOGIN, WRONG_PASSWORD));
    }

    @Test
    public void alreadyAunt() {
        user.setAuthenticationStatus(true);
        Assertions.assertThrows(AlreadyAuthenticatedException.class, () -> usersService.authenticate(CORRECT_LOGIN, CORRECT_PASSWORD));
    }
}
