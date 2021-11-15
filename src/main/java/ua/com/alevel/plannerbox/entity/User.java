package ua.com.alevel.plannerbox.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ua.com.alevel.plannerbox.entity.status.UserStatus;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class User extends BaseEntity {

    @Column(name = "username")
    private String username;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<UserRole> roles;

    @ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<TaskBoard> task;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private UserStatus status;
}
