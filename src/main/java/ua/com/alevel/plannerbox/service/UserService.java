package ua.com.alevel.plannerbox.service;

import ua.com.alevel.plannerbox.entity.User;
import ua.com.alevel.plannerbox.exception.UserAlreadyExistException;

import java.util.List;

public interface UserService {

    User register(User user) throws UserAlreadyExistException;

    List<User> getAll();

    User findByUsername(String username);

    User findById(Long id);

    void delete(Long id);
}
