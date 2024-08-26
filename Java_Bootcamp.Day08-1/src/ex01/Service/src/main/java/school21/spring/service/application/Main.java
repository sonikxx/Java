package school21.spring.service.application;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import school21.spring.service.models.User;
import school21.spring.service.repositories.UsersRepository;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        UsersRepository usersRepository = context.getBean("UsersRepositoryJdbcImpl", UsersRepository.class);
        User user1 =  new User(1L, "emailUser1");
        User user2 =  new User(2L, "emailUser2");
        User user3 =  new User(3L, "emailUser3");
        User user4 =  new User(4L, "emailUser4");
        System.out.println("-------UsersRepositoryJdbcImpl-------");
        try {
            usersRepository.save(user1);
            usersRepository.save(user2);
            usersRepository.save(user3);
            usersRepository.save(user4);
            System.out.println("FIND ALL: " + usersRepository.findAll());
            System.out.println("FIND BY ID: " + usersRepository.findById(user2.getId()));
            System.out.println("FIND BY EMAIL: " + usersRepository.findByEmail(user2.getEmail()));
            user3.setEmail("NewEmailUser3");
            usersRepository.update(user3);
            System.out.println("AFTER UPDATE: " + usersRepository.findById(user3.getId()));
            usersRepository.delete(user4.getId());
            System.out.println("FIND ALL AFTER DELETE: " + usersRepository.findAll());
            usersRepository.delete(user3.getId());
            usersRepository.delete(user2.getId());
            usersRepository.delete(user1.getId());
        } catch (RuntimeException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(-1);
        }
        System.out.println("-------UsersRepositoryJdbcTemplateImpl-------");
        usersRepository = context.getBean("UsersRepositoryJdbcTemplateImpl", UsersRepository.class);
        usersRepository.save(user1);
        usersRepository.save(user2);
        usersRepository.save(user3);
        usersRepository.save(user4);
        System.out.println("FIND ALL: " + usersRepository.findAll());
        System.out.println("FIND BY ID: " + usersRepository.findById(user2.getId()));
        System.out.println("FIND BY EMAIL: " + usersRepository.findByEmail(user2.getEmail()));
        user3.setEmail("NewEmailUser3");
        usersRepository.update(user3);
        System.out.println("AFTER UPDATE: " + usersRepository.findById(user3.getId()));
        usersRepository.delete(user4.getId());
        System.out.println("FIND ALL AFTER DELETE: " + usersRepository.findAll());
        usersRepository.delete(user3.getId());
        usersRepository.delete(user2.getId());
        usersRepository.delete(user1.getId());
    }
}
