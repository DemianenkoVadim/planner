package ua.com.alevel.plannerbox.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.com.alevel.plannerbox.entity.TaskBoard;
import ua.com.alevel.plannerbox.entity.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskBoardRepository extends JpaRepository<TaskBoard, Long> {

    Optional<TaskBoard> findTaskBoardByTaskDescription(String taskDescription);

    @Query("select t from TaskBoard t inner join User u   where t.id = ?1 and u.id = ?2")
    Optional<TaskBoard> findTaskBoardByIdAndUsers(Long taskId, Long userId);

//    Optional<TaskBoard> findTaskBoardById_UserId(Long taskId, Long userId);
}
