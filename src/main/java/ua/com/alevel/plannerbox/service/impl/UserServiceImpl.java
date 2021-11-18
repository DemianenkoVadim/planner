package ua.com.alevel.plannerbox.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.plannerbox.entity.User;
import ua.com.alevel.plannerbox.entity.UserRole;
import ua.com.alevel.plannerbox.entity.status.UserStatus;
import ua.com.alevel.plannerbox.exceptions.UserAlreadyExistException;
import ua.com.alevel.plannerbox.repository.UserRepository;
import ua.com.alevel.plannerbox.service.UserRoleService;
import ua.com.alevel.plannerbox.service.UserService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRoleService userRoleService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User createAdmin() {
        User admin = new User();
        admin.setUsername("admin");
        admin.setFirstName("admin");
        admin.setLastName("admin");
        admin.setEmail("admin@gmail.com");
        admin.setPassword(passwordEncoder.encode("adminPlanner"));
        admin.setStatus(UserStatus.ACTIVE);
        admin.setCreated(LocalDateTime.now());
        log.info("Admin - {} successfully registered", admin.getUsername());
        return userRepository.save(admin);
    }

    // TODO add role "USER" for default
    @Override
    public User registerUser(User user) {
        user.setUsername(user.getUsername());
        user.setFirstName(user.getFirstName());
        user.setLastName(user.getLastName());
        user.setEmail(user.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(UserStatus.ACTIVE);
        user.setCreated(LocalDateTime.now());
        log.info("User: {} successfully registered", user.getUsername());
        return userRepository.save(user);
    }

    public Set<UserRole> addDefaultUserRole() {
        return Collections.singleton(userRoleService.findByRole("USER"));
    }

    @Override
    public User findByUsername(String username) {
        log.info("Fetching user {}", username);
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> getAllUser() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }

    @Override
    public User findUserById(Long id) {
        User result = userRepository.findById(id).orElse(null);
        if (result == null) {
            log.warn("User with id {} does not exist", id);
            throw new UsernameNotFoundException("User with id " + id + " does not exist");
        }
        log.info("Fetching user by id: {}", result);
        return result;
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
        log.info("User with id: {} successfully delete", id);
    }

    private void userNameValidity(User user) throws UserAlreadyExistException {
        User consumerName = userRepository.findByUsername(user.getUsername());
        if (getAllUser().contains(consumerName)) {
            throw new UserAlreadyExistException("username is already taken"); // TODO or log.warn???
        }
    }
}
