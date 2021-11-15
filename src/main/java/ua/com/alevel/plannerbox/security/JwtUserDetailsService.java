package ua.com.alevel.plannerbox.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.com.alevel.plannerbox.entity.User;
import ua.com.alevel.plannerbox.entity.UserRole;
import ua.com.alevel.plannerbox.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public JwtUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("user with username: " + username + " not found");
        }

        org.springframework.security.core.userdetails.User user1 =
                new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapToGrantedAuthorities(user.getRoles()));

        log.info("loadUserByUserName - user with username: {} successfully loaded", username);
        return user1;
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<UserRole> userRoles) {
        return userRoles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
