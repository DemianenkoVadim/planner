package ua.com.alevel.plannerbox.service;

import ua.com.alevel.plannerbox.entity.User;
import ua.com.alevel.plannerbox.exceptions.UserAlreadyExistException;

import java.util.List;

public interface UserService {

    User createAdmin();

    User registerUser(User user) throws UserAlreadyExistException;

    List<User> getAllUser();

    User findByUsername(String username);

    User findUserById(Long id);

    void deleteUser(Long id);
}
