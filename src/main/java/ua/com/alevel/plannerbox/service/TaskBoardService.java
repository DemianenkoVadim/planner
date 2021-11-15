package ua.com.alevel.plannerbox.service;

import ua.com.alevel.plannerbox.dto.TaskBoardDto;
import ua.com.alevel.plannerbox.entity.TaskBoard;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskBoardService {

    TaskBoard createTaskBoard(TaskBoardDto taskBoard);

    TaskBoard updateTaskBoard(TaskBoardDto taskBoardDto);

    void deleteTaskBoard(Long id);

    List<TaskBoard> getAllTaskBoards();

    TaskBoard findById(Long id);

    List<TaskBoard> findTaskBoardsByStartDateTime(LocalDateTime localDateTime);
}