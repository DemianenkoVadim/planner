package ua.com.alevel.plannerbox.service;

import ua.com.alevel.plannerbox.dto.TaskBoardDto;
import ua.com.alevel.plannerbox.entity.TaskBoard;
import ua.com.alevel.plannerbox.entity.status.TaskPriority;
import ua.com.alevel.plannerbox.entity.status.TaskStatus;
import ua.com.alevel.plannerbox.entity.status.TaskType;

import java.time.LocalDateTime;
import java.util.List;


public interface TaskBoardService {

    TaskBoard createTaskBoard(TaskBoardDto taskBoard);

    List<TaskBoard> findAllCurrentUsersTaskBoards();

    List<TaskBoard> findAllTaskBoards();

    List<TaskBoard> findAllCurrentUsersPersonalTaskBoards();

    List<TaskBoard> findAllCurrentUsersCommonTaskBoards();

    TaskBoard addNewUserToCommonTaskBoard(Long userId, Long taskBoardId);

    TaskBoard updateTaskBoard(TaskBoardDto taskBoardDto);

    void deleteTaskBoard(Long id);

    TaskBoard findTaskBoardById(Long id);

    List<TaskBoard> findAllTaskBoardsByUserId(Long id);

    List<TaskBoard> findAllPersonalTaskBoardsByUserId(Long id);

    List<TaskBoard> findAllCommonTaskBoardsByUserId(Long id);

    List<TaskBoard> findTaskBoardsByStartDateTime(LocalDateTime localDateTime);

    void changeTaskType(Long id, TaskType taskType);

    void changeTaskStatus(Long id, TaskStatus taskStatus);

    void changeTaskPriority(Long id, TaskPriority taskPriority);
}