package ua.com.alevel.plannerbox.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.alevel.plannerbox.entity.TaskBoard;

import java.util.Optional;

@Repository
public interface TaskBoardRepository extends JpaRepository<TaskBoard, Long> {

    Optional<TaskBoard> findTaskBoardByTaskDescription(String taskDescription);

}
