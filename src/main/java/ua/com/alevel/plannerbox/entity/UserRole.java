package ua.com.alevel.plannerbox.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "roles")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
//@RequiredArgsConstructor
public class UserRole extends BaseEntity {

    public UserRole(String role) {
        this.role = role;
    }

    @Column(name = "role")
    private String role;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY) // todo ???
    @ToString.Exclude
    private List<User> users;
}
