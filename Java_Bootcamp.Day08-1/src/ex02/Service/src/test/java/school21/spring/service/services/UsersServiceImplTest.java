package school21.spring.service.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.ApplicationContext;
import school21.spring.service.config.TestApplicationConfig;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UsersServiceImplTest {
    private static UsersService usersServiceJdbc;
    private static UsersService usersServiceJdbcTemplate;

    @BeforeAll
    public static void beforeAll() {
        ApplicationContext context = new AnnotationConfigApplicationContext(TestApplicationConfig.class);
        usersServiceJdbc = context.getBean("usersServiceJdbc", UsersService.class);
        usersServiceJdbcTemplate = context.getBean("usersServiceJdbcTemplate", UsersService.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"emailUser1", "emailUser2", "emailUser2"})
    public void testUsersServiceJdbc(String email) {
        assertNotNull(usersServiceJdbc.signUp(email));
    }

    @ParameterizedTest
    @ValueSource(strings = {"emailUser1", "emailUser2", "emailUser2"})
    public void testUsersServiceJdbcTemplate(String email) {
        assertNotNull(usersServiceJdbcTemplate.signUp(email));
    }

}
