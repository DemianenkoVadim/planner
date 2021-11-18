package ua.com.alevel.plannerbox.service;

import ua.com.alevel.plannerbox.dto.TaskBoardDto;
import ua.com.alevel.plannerbox.entity.TaskBoard;
import ua.com.alevel.plannerbox.exceptions.TaskBoardNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskBoardService {

    TaskBoard createTaskBoard(TaskBoardDto taskBoard);

    List<TaskBoard> findAllCurrentUsersTaskBoards();

//    Optional<TaskBoard> findAllCurrentUsersPersonalTaskBoardsSortedByPriority();

    List<TaskBoard> findAllTaskBoards();

    List<TaskBoard> findAllCurrentUsersPersonalTaskBoards();

    List<TaskBoard> findAllCurrentUsersCommonTaskBoards();

    TaskBoard updateTaskBoard(TaskBoardDto taskBoardDto);

    void deleteTaskBoard(Long id) throws TaskBoardNotFoundException;

    TaskBoard findTaskBoardById(Long id) throws TaskBoardNotFoundException;

    List<TaskBoard> findAllTaskBoardsByUserId(Long id);

    List<TaskBoard> findAllPersonalTaskBoardsByUserId(Long id);

    List<TaskBoard> findAllCommonTaskBoardsByUserId(Long id);

    List<TaskBoard> findTaskBoardsByStartDateTime(LocalDateTime localDateTime);
}