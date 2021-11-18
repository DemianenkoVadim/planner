package ua.com.alevel.plannerbox.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ua.com.alevel.plannerbox.entity.User;
import ua.com.alevel.plannerbox.repository.UserRepository;

@Component
public class SecurityContextHelperImpl implements SecurityContextHelper {

    private final UserRepository userRepository;

    public SecurityContextHelperImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getCurrentUser() {
        UserDetails principal = (UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        return userRepository.findByUsername(principal.getUsername());
    }
}
