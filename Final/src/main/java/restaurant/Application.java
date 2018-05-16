package restaurant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("restaurant.model")
@EnableJpaRepositories({"restaurant.repository"})
@ComponentScan({"restaurant.model", "restaurant.repository", "restaurant.service.user", "restaurant.service.patient", "restaurant.service.consultation", "restaurant.service.card", "restaurant.service.ingredient", "restaurant.service.dish", "restaurant.service.orderr", "restaurant.service.employee", "restaurant.controller", "restaurant.config"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

    }
}
