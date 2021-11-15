package ua.com.alevel.plannerbox.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.alevel.plannerbox.entity.UserRole;

@Repository
public interface RoleRepository extends JpaRepository<UserRole, Long> {

    UserRole findByName(String name);
}
