package ua.com.alevel.plannerbox.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.com.alevel.plannerbox.dto.UserDto;
import ua.com.alevel.plannerbox.entity.User;
import ua.com.alevel.plannerbox.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1/users/")
public class UserRestController {

    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable(name = "id") Long id) {
        User user = userService.findById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
//        UserDto result = UserDto.fromUser(user);
//        return new ResponseEntity<>(result, HttpStatus.OK);
        return null;
    }

    @PostMapping(path = "register")
    public ResponseEntity register(@RequestBody @Valid UserDto user, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return register(user, bindingResult); // todo maybe I should return a page of registration ?
        try {
//            User consumer = userService.register(user.toUser());
//            return ResponseEntity.ok(UserDto.fromUser(consumer));
            return null;
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("er");
        }
    }


}
