package ua.com.alevel.plannerbox.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.com.alevel.plannerbox.entity.User;
import ua.com.alevel.plannerbox.entity.UserRole;
import ua.com.alevel.plannerbox.entity.status.UserStatus;
import ua.com.alevel.plannerbox.exception.UserAlreadyExistException;
import ua.com.alevel.plannerbox.repository.RoleRepository;
import ua.com.alevel.plannerbox.repository.UserRepository;
import ua.com.alevel.plannerbox.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository; //TODO why is not Autowired
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User register(User user) throws UserAlreadyExistException {
        UserRole roleUser = roleRepository.findByName("USER");
        user.setUsername(user.getUsername());
        userNameValidity(user);
        user.setFirstName(user.getFirstName());
        user.setLastName(user.getLastName());
        user.setEmail(user.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of(roleUser));
        user.setStatus(UserStatus.ACTIVE);
        user.setCreated(LocalDateTime.now());
        user.setUpdated(LocalDateTime.now());
        User registeredUser = userRepository.save(user);
        log.info("register - user: {} successfully registered", registeredUser);
        return registeredUser;
    }

    private void userNameValidity(User user) throws UserAlreadyExistException {
        User consumerName = userRepository.findByUsername(user.getUsername());
        if (getAll().contains(consumerName)) {
            throw new UserAlreadyExistException("username is already taken");
        }
    }

    @Override
    public List<User> getAll() {
        List<User> result = userRepository.findAll();
        log.info("getAll - {} users found", result.size());//
        return result;
    }

    @Override
    public User findByUsername(String username) {
        User result = userRepository.findByUsername(username);
        log.info("findByUsername - user: {} found by username: {}", result, username);
        return result;
    }

    @Override
    public User findById(Long id) {
        User result = userRepository.findById(id).orElse(null);

        if (result == null) {
            log.warn("findById - no user find by id: {}", id);
            return null; // TODO
        }
        log.info("findById - user: found by id: {}", result);
        return result;
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
        log.info("delete - user with id: {} successfully delete", id);
    }
}
