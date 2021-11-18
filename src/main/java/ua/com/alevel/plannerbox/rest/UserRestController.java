package ua.com.alevel.plannerbox.rest;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.alevel.plannerbox.dto.UserDto;
import ua.com.alevel.plannerbox.entity.User;
import ua.com.alevel.plannerbox.exceptions.UserAlreadyExistException;
import ua.com.alevel.plannerbox.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/users/")
public class UserRestController {

    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "register")
    public ResponseEntity<User> register(@RequestBody @Valid User user) throws UserAlreadyExistException {
        return ResponseEntity.ok().body(userService.registerUser(user));
//            User consumer = userService.register(user.toUser());
//            return ResponseEntity.ok(UserDto.fromUser(consumer));
    }

//    @PostMapping(path = "/role/save")
//    public ResponseEntity<UserRole> saveRole(@RequestBody UserRole user) {
//        return ResponseEntity.ok().body(userService.saveRole(user));
//    }
//
//    @PostMapping(path = "/role/addToUser")
//    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form) {
//        userService.addRoleToUser(form.getUsername(), form.getRoleName());
//        return ResponseEntity.ok().build();
//    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok().body(userService.getAllUser());
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable(name = "id") Long id) {
        User user = userService.findUserById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
//        UserDto result = UserDto.fromUser(user);
//        return new ResponseEntity<>(result, HttpStatus.OK);
        return null;
    }
}



@Data
class RoleToUserForm {
    private String username;
    private String roleName;
}