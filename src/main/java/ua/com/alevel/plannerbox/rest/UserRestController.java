package ua.com.alevel.plannerbox.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.alevel.plannerbox.dto.UserDto;
import ua.com.alevel.plannerbox.entity.User;
import ua.com.alevel.plannerbox.exceptions.UserNotFoundException;
import ua.com.alevel.plannerbox.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/users")
@CrossOrigin
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> register(@RequestBody @Valid UserDto userDto) {
        return ResponseEntity.ok(userService.registerUser(userDto));
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<User> updateUser(@PathVariable(name = "id") Long id,
                                           @RequestBody UserDto userDto) {
        userDto.setId(id);
        return ResponseEntity.ok(userService.updateUser(userDto));
    }

    @GetMapping
    public ResponseEntity<List<User>> findAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @GetMapping(path = "/nicknames/{username}")
    public ResponseEntity<User> findUserByUsername(@PathVariable(name = "username") String username) {
        return ResponseEntity.ok(userService.findByUsername(username));
    }

    @GetMapping(value = "{id}")
    public Optional<User> findUserById(@PathVariable(name = "id") Long id) {
        return userService.findUserById(id);
    }

    @DeleteMapping(value = "{id}")
    public void deleteUser(@PathVariable(name = "id") Long id) throws UserNotFoundException {
        userService.deleteUser(id);
    }
}
