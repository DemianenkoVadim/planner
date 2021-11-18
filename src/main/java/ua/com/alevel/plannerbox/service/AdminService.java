package ua.com.alevel.plannerbox.service;

import ua.com.alevel.plannerbox.entity.status.UserStatus;
import ua.com.alevel.plannerbox.exceptions.UserNotFoundException;

public interface AdminService {

    void changeUserStatus(Long id, UserStatus status) throws UserNotFoundException;
}
