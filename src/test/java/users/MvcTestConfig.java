package users;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import users.model.User;
import users.repository.UserRepository;
import users.service.UserService;
import users.validator.UserValidator;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {
        "users.controller"
})
public class MvcTestConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    UserRepository userRepository() {
        return mock(UserRepository.class);
    }

    @Bean
    UserService userService() {
        return mock(UserService.class);
    }

    @Bean
    UserValidator userValidator() {
        final UserValidator userValidator = mock(UserValidator.class);
        when(userValidator.supports(User.class)).thenReturn(true);
        return userValidator;
    }
}
