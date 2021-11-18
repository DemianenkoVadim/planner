package ua.com.alevel.plannerbox.util;

import org.springframework.boot.CommandLineRunner;
import ua.com.alevel.plannerbox.service.UserRoleService;
import ua.com.alevel.plannerbox.service.UserService;

import java.util.Optional;

public record FirstAccess() {

    public CommandLineRunner firstRunApplication(UserService userService, UserRoleService userRoleService) {
        return args -> {
            Optional.ofNullable(userRoleService.findByRole("ADMIN"))
                    .orElseGet(userRoleService::createRoleAdmin);

            Optional.ofNullable(userRoleService.findByRole("USER"))
                    .orElseGet(userRoleService::createRoleUser);

            Optional.ofNullable(userService.findByUsername("admin"))
                    .orElseGet(userService::createAdmin);

            userRoleService.addRoleToUserByAdmin("admin", "ADMIN");
            userRoleService.addRoleToUserByAdmin("admin", "USER");
        };
    }
}
