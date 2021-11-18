package ua.com.alevel.plannerbox.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.plannerbox.entity.User;
import ua.com.alevel.plannerbox.entity.UserRole;
import ua.com.alevel.plannerbox.repository.RoleRepository;
import ua.com.alevel.plannerbox.service.UserRoleService;
import ua.com.alevel.plannerbox.service.UserService;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserRoleServiceImpl implements UserRoleService {

    private final RoleRepository roleRepository;
    private final UserService userService;

    @Override
    public UserRole createRoleAdmin() {
        UserRole admin = new UserRole();
        admin.setRole("ADMIN");
        admin.setCreated(LocalDateTime.now());
        admin.setUpdated(LocalDateTime.now());
        log.info("Role -  {} successfully added", admin);
        return roleRepository.save(admin);
    }

    @Override
    public UserRole createRoleUser() {
        UserRole user = new UserRole();
        user.setRole("USER");
        log.info("Role -  {} successfully added", user);
        user.setCreated(LocalDateTime.now());
        user.setUpdated(LocalDateTime.now());
        return roleRepository.save(user);
    }

    @Override
    public void addRoleToUserByAdmin(String username, String roleName) {
        User user = userService.findByUsername(username);
        UserRole role = roleRepository.findByRole(roleName);
        role.setCreated(LocalDateTime.now());
        role.setUpdated(LocalDateTime.now());
        user.getRoles().add(role);
    }

    @Override
    public UserRole saveRole(UserRole role) {
        log.info("Saving a new role {} to database successfully done", role.getRole());
        return roleRepository.save(role);
    }

    @Override
    public UserRole findByRole(String role) {
        log.info("Fetching role {}", role);
        return roleRepository.findByRole(role);
    }
}
