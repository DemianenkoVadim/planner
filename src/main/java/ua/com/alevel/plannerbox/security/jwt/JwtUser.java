//package ua.com.alevel.plannerbox.security.jwt;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.User;
//
//import java.util.Collection;
//import java.util.Date;
//
//public class JwtUser extends User {
//
//    private final Long id;
//    private final String username;
//    private final String firstName;
//    private final String lastName;
//    private final String password;
//    private final String email;
//    private final Date lastPasswordResetDate;
//    private final Collection<? extends GrantedAuthority> authorities;
//
//    public JwtUser(Long id,
//                   String username,
//                   String firstName,
//                   String lastName,
//                   String password,
//                   String email,
//                   Date lastPasswordResetDate,
//                   Collection<? extends GrantedAuthority> authorities) {
//        this.id = id;
//        this.username = username;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.password = password;
//        this.email = email;
//        this.lastPasswordResetDate = lastPasswordResetDate;
//        this.authorities = authorities;
//    }
//
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    @JsonIgnore
//    public Long getId() {
//        return id;
//    }
//
//    @JsonIgnore
//    public Date getLastPasswordResetDate() {
//        return lastPasswordResetDate;
//    }
//
//    @Override
//    public String getUsername() {
//        return username;
//    }
//
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return authorities;
//    }
//
//    @JsonIgnore
//    @Override
//    public String getPassword() {
//        return password;
//    }
//
//    @JsonIgnore
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @JsonIgnore
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @JsonIgnore
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return false;
//    }
//}
