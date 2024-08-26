package school21.spring.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import school21.spring.service.models.User;
import school21.spring.service.repositories.UsersRepository;

import java.security.SecureRandom;

@Component("UsersServiceImpl")
public class UsersServiceImpl implements UsersService {
    private static Long id = 0L;
    private UsersRepository usersRepository;

    @Autowired
    public UsersServiceImpl(@Qualifier("UsersRepositoryJdbcTemplateImpl") UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public String signUp(String email) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(20);
        for (int i = 0; i < 20; ++i) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }
        String generatedPassword = sb.toString();
        long newId = ++id;
        usersRepository.save(new User(newId, email, generatedPassword));
        return generatedPassword;
    }
}
