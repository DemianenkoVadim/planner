package ua.com.alevel.plannerbox.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.com.alevel.plannerbox.dto.AdminUserDto;
import ua.com.alevel.plannerbox.entity.User;
import ua.com.alevel.plannerbox.exceptions.UserNotFoundException;
import ua.com.alevel.plannerbox.rest.request.UserStatusChangeRequest;
import ua.com.alevel.plannerbox.service.AdminService;
import ua.com.alevel.plannerbox.service.UserService;

import java.util.Optional;

@RestController
@RequestMapping(value = "api/v1/admin/")
public class AdminRestController {

    private final UserService userService;
    private final AdminService adminService;

    @Autowired
    public AdminRestController(UserService userService, AdminService adminService) {
        this.userService = userService;
        this.adminService = adminService;
    }

    @GetMapping(value = "users/{id}")
    public Optional<AdminUserDto> getUserById(@PathVariable(name = "id") Long id) {
        Optional<User> user = userService.findUserById(id);
        return user.map(AdminUserDto::fromUser);
    }

    @PutMapping(path = "/users/statuses/{id}")
    public void changeUserStatus(@PathVariable(name = "id") Long id,
                                 @RequestBody UserStatusChangeRequest request) throws UserNotFoundException {
        adminService.changeUserStatus(id, request.getUserStatus());
    }
}

