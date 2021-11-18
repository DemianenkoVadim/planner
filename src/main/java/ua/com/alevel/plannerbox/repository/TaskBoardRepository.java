package ua.com.alevel.plannerbox.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.alevel.plannerbox.entity.TaskBoard;
import ua.com.alevel.plannerbox.entity.User;
import ua.com.alevel.plannerbox.entity.status.TaskType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskBoardRepository extends JpaRepository<TaskBoard, Long> {

    List<TaskBoard> findAll();

    List<TaskBoard> findTaskBoardsByTaskAuthor(User taskAuthor);

    List<TaskBoard> findTaskBoardsByTaskAuthorAndType(User taskAuthor, TaskType type);

//    Optional<TaskBoard> findTaskBoardsByTaskAuthorAndOrderByPriorityAsc(User taskAuthor); // TODO

    List<TaskBoard> findTaskBoardByStartDateAndTaskAuthor(LocalDateTime startDate, User taskAuthor);

    TaskBoard findTaskBoardByTaskAuthorAndId(User taskAuthor, Long id);

    List<TaskBoard> findTaskBoardByTaskAuthorId(Long id);

    List<TaskBoard> findTaskBoardsByTaskAuthorIdAndType(Long taskAuthorId, TaskType priority);

    TaskBoard findTaskBoardById(Long id);

    void deleteByTaskAuthorAndId(User taskAuthor, Long id);

    List<TaskBoard> findTaskBoardByTaskDescription(String taskDescription);
}

