package ua.com.alevel.plannerbox.service;

import ua.com.alevel.plannerbox.entity.UserRole;

public interface UserRoleService {

    UserRole createRoleAdmin();

    UserRole createRoleUser();

    UserRole findByRole(String role);

    void addRoleToUserByAdmin(String username, String roleName);

    UserRole saveRole(UserRole role);
}
