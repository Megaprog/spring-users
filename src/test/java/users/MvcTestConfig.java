package users;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import users.repository.UserRepository;
import users.service.UserService;

import static org.mockito.Mockito.mock;

@Configuration
@EnableWebMvc
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
}
