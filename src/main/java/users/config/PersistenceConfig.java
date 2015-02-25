package users.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {
        "users.repository"
})
@ComponentScan(basePackages = {
        "users.service"
})
public class PersistenceConfig {
}
