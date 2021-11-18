package ua.com.alevel.plannerbox.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.plannerbox.entity.User;
import ua.com.alevel.plannerbox.entity.status.UserStatus;
import ua.com.alevel.plannerbox.exceptions.UserNotFoundException;
import ua.com.alevel.plannerbox.repository.UserRepository;
import ua.com.alevel.plannerbox.service.AdminService;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;

    @Autowired
    public AdminServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void changeUserStatus(Long id, UserStatus status) throws UserNotFoundException {
        User user = userRepository.findUserById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " does not exist"));
        log.info("User with id {} found", id);
        user.setStatus(status);
        userRepository.save(user);
    }
}
