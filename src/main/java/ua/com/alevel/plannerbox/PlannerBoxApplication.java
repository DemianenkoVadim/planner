package ua.com.alevel.plannerbox;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ua.com.alevel.plannerbox.service.UserRoleService;
import ua.com.alevel.plannerbox.service.UserService;
import ua.com.alevel.plannerbox.util.FirstAccesses;


@SpringBootApplication
public class PlannerBoxApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlannerBoxApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(UserService userService, UserRoleService userRoleService) {
        return new FirstAccesses().firstRunApplication(userService, userRoleService);
    }
}

