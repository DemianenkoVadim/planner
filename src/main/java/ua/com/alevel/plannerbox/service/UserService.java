package ua.com.alevel.plannerbox.service;

import ua.com.alevel.plannerbox.dto.UserDto;
import ua.com.alevel.plannerbox.entity.User;
import ua.com.alevel.plannerbox.exceptions.UserAlreadyExistException;
import ua.com.alevel.plannerbox.exceptions.UserNotFoundException;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User createAdmin();

    User registerUser(UserDto userDto) throws UserAlreadyExistException;

    List<User> findAllUsers();

    User findByUsername(String username);

    Optional<User> findUserById(Long id);

    void deleteUser(Long id) throws UserNotFoundException;

    User updateUser(UserDto userDto);
}
