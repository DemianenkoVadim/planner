package ua.com.alevel.plannerbox.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.plannerbox.dto.UserDto;
import ua.com.alevel.plannerbox.entity.User;
import ua.com.alevel.plannerbox.entity.status.UserStatus;
import ua.com.alevel.plannerbox.exceptions.UserAlreadyExistException;
import ua.com.alevel.plannerbox.exceptions.UserNotFoundException;
import ua.com.alevel.plannerbox.mapper.UserMapper;
import ua.com.alevel.plannerbox.repository.RoleRepository;
import ua.com.alevel.plannerbox.repository.UserRepository;
import ua.com.alevel.plannerbox.security.SecurityContextHelper;
import ua.com.alevel.plannerbox.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final SecurityContextHelper securityContextHelper;
    private final UserMapper userMapper;

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
        admin.setUpdated(LocalDateTime.now());
        log.info("Admin - {} successfully registered", admin.getUsername());
        return userRepository.save(admin);
    }

    @Override
    public User registerUser(UserDto userDto) {
        if (userRepository.findByUsername(userDto.getUsername()) != null) {
            throw new UserAlreadyExistException("Username already exist");
        }
        User user = userMapper.userFromDto(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setStatus(UserStatus.ACTIVE);
        user.setCreated(LocalDateTime.now());
        user.setUpdated(LocalDateTime.now());
        user.setRoles(Set.of(roleRepository.findByRole("USER")));
        User registeredConsumer = userRepository.save(user);
        log.info("User: {} successfully registered", registeredConsumer.getUsername());
        return registeredConsumer;
    }

    @Override
    public User findByUsername(String username) {
        log.info("Fetching user {}", username);
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> findAllUsers() {
        List<User> allUsers = userRepository.findAll();
        log.info("Fetching all users");
        return allUsers;
    }

    @Override
    public Optional<User> findUserById(Long id) {
        log.info("Start find user by id: {}", id);
        Optional<User> user = userRepository.findUserById(id);
        if (user.isEmpty()) {
            log.warn("User with id {} does not exist", id);
        }
        return user;
    }

    @Override
    public void deleteUser(Long id) throws UserNotFoundException {
        Optional<User> user = userRepository.findUserById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User with id " + id + " does not found");
        }
        log.info("Start delete user by id: {}", id);
        userRepository.deleteById(id);
        log.info("User with id: {} successfully delete", id);
    }

    @Override
    public User updateUser(UserDto userDto) {
        String currentUser = securityContextHelper.getCurrentUser().getUsername();
        User user = userRepository.findByUsernameAndId(currentUser, userDto.getId());
        user.setUpdated(LocalDateTime.now());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userMapper.updateUser(userDto, user);
        log.info("User with username {} and user id: {} successfully updated", currentUser, userDto.getId());
        return userRepository.save(user);
    }
}
