//package ua.com.alevel.plannerbox.security.jwt;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import ua.com.alevel.plannerbox.entity.User;
//import ua.com.alevel.plannerbox.entity.UserRole;
//import ua.com.alevel.plannerbox.entity.status.UserStatus;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//public final class JwtUserFactory {
//
//    public JwtUserFactory() {
//    }
//
////    public static JwtUser create(User user) {
////        return new JwtUser(
////                user.getId(),
////                user.getUsername(),
////                user.getFirstName(),
////                user.getLastName(),
////                user.getPassword(),
////                user.getEmail(),
////                user.getUpdated(),
////                mapToGrantedAuthorities(new ArrayList<>(user.getRoles())));
////    }
//
//    private static List<GrantedAuthority> mapToGrantedAuthorities(List<UserRole> userRoles) {
//        return userRoles.stream()
//                .map(role -> new SimpleGrantedAuthority(role.getName()))
//                .collect(Collectors.toList());
//    }
//}
