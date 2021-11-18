package ua.com.alevel.plannerbox.security;

import ua.com.alevel.plannerbox.entity.User;

public interface SecurityContextHelper {

    User getCurrentUser();
}
