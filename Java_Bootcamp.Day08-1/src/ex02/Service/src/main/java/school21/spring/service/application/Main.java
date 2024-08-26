package school21.spring.service.application;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import school21.spring.service.config.ApplicationConfig;
import school21.spring.service.repositories.UsersRepository;
import school21.spring.service.services.UsersService;

public class Main {
    public static void main(String[] args) {
        System.out.println("-------UsersRepositoryJdbcTemplateImpl-------");
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        UsersRepository usersRepository = context.getBean("UsersRepositoryJdbcTemplateImpl", UsersRepository.class);
        UsersService usersService = context.getBean("UsersServiceImpl", UsersService.class);
        System.out.println("PASSWORD USER1: " + usersService.signUp("emailUser1"));
        System.out.println("PASSWORD USER2: " + usersService.signUp("emailUser2"));
        System.out.println("PASSWORD USER3: " + usersService.signUp("emailUser3"));
        System.out.println("FIND ALL: " + usersRepository.findAll());
    }
}
