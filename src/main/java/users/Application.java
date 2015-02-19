package users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;
import users.model.User;
import users.model.UserRole;
import users.repository.UserRepository;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

@Configuration
@ComponentScan
@EnableAutoConfiguration(exclude = org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class)
@EnableJpaRepositories //only to help Idea recognize repository beans
public class Application implements CommandLineRunner {

    public static void main(String[] args) throws MalformedURLException, UnsupportedEncodingException {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        userRepository.save(new User("admin", passwordEncoder.encode("admin"), "admin@gmail.com", UserRole.Editor));
    }
}
