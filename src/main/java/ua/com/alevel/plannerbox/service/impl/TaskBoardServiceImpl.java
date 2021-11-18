package ua.com.alevel.plannerbox.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.plannerbox.dto.TaskBoardDto;
import ua.com.alevel.plannerbox.entity.TaskBoard;
import ua.com.alevel.plannerbox.entity.User;
import ua.com.alevel.plannerbox.exceptions.TaskBoardNotFoundException;
import ua.com.alevel.plannerbox.mapper.TaskBoardMapper;
import ua.com.alevel.plannerbox.repository.TaskBoardRepository;
import ua.com.alevel.plannerbox.security.SecurityContextHelper;
import ua.com.alevel.plannerbox.service.TaskBoardService;

import java.time.LocalDateTime;
import java.util.List;

import static ua.com.alevel.plannerbox.entity.status.TaskType.COMMON;
import static ua.com.alevel.plannerbox.entity.status.TaskType.PERSONAL;

@Service
@Slf4j
@Transactional
public class TaskBoardServiceImpl implements TaskBoardService {

    private final TaskBoardRepository taskBoardRepository;
    private final TaskBoardMapper taskBoardMapper;
    private final SecurityContextHelper securityContextHelper;

    @Autowired
    public TaskBoardServiceImpl(TaskBoardRepository taskBoardRepository,
                                TaskBoardMapper taskBoardMapper,
                                SecurityContextHelper securityContextHelper) {
        this.taskBoardRepository = taskBoardRepository;
        this.taskBoardMapper = taskBoardMapper;
        this.securityContextHelper = securityContextHelper;
    }

    // common
    @Override
    public TaskBoard createTaskBoard(TaskBoardDto taskBoardDto) {
        TaskBoard taskBoard = taskBoardMapper.dtoToModel(taskBoardDto);
        taskBoard.setTaskAuthor(securityContextHelper.getCurrentUser());
        TaskBoard createdTaskBoard = taskBoardRepository.save(taskBoard);
        log.info("Task board: {} successfully created", createdTaskBoard);
        return createdTaskBoard;
    }

    //common
    @Override
    public List<TaskBoard> findAllCurrentUsersTaskBoards() {
        User currentTaskAuthor = securityContextHelper.getCurrentUser();
        List<TaskBoard> allTaskBoards = taskBoardRepository.findTaskBoardsByTaskAuthor(currentTaskAuthor);
        log.info("Find all current user task boxes - {} task boxes found", allTaskBoards); // TODO style log is wright???
        return allTaskBoards;
    }

    // common
    @Override
    public List<TaskBoard> findAllCurrentUsersPersonalTaskBoards() {
        User currentTaskAuthor = securityContextHelper.getCurrentUser();
        List<TaskBoard> allPersonalTaskBoards = taskBoardRepository.findTaskBoardsByTaskAuthorAndType(currentTaskAuthor, PERSONAL);
        log.info("Find all current user personal task boxes - {} task boxes found", allPersonalTaskBoards);
        return allPersonalTaskBoards;
    }

    //common
    @Override
    public List<TaskBoard> findAllCurrentUsersCommonTaskBoards() {
        User currentTaskAuthor = securityContextHelper.getCurrentUser();
        List<TaskBoard> allCommonTaskBoards = taskBoardRepository.findTaskBoardsByTaskAuthorAndType(currentTaskAuthor, COMMON);
        log.info("Find all current user common task boxes - {} task boxes found", allCommonTaskBoards);
        return allCommonTaskBoards;
    }

    //common
    //TODO check
//    @Override
//    public Optional<TaskBoard> findAllCurrentUsersPersonalTaskBoardsSortedByPriority() {
//        User currentTaskAuthor = securityContextHelper.getCurrentUser();
//        Optional<TaskBoard> allTaskBoard = taskBoardRepository.findTaskBoardsByTaskAuthorAndOrderByPriorityAsc(currentTaskAuthor);
//        log.info("Find all current user personal task boxes sorted by priority - {} task boxes found", allTaskBoard);
//        return allTaskBoard;
//    }

    //admin
    @Override
    public List<TaskBoard> findAllTaskBoards() {
        List<TaskBoard> allTaskBoards = taskBoardRepository.findAll();// TODO method to find next one Optional????
        log.info("Find all current user task boxes - {} task boxes found", allTaskBoards);
        return allTaskBoards;
    }

    // admin
    @Override
    public List<TaskBoard> findAllTaskBoardsByUserId(Long userId) {
        List<TaskBoard> allUserTaskBoards = taskBoardRepository.findTaskBoardByTaskAuthorId(userId);
        log.info("All task boards by user id: {} successfully find", userId);
        return allUserTaskBoards;
    }

    // admin
    @Override
    public List<TaskBoard> findAllPersonalTaskBoardsByUserId(Long userId) {
        List<TaskBoard> allPersonalTaskBoards = taskBoardRepository.findTaskBoardsByTaskAuthorIdAndType(userId, PERSONAL);
        log.info("All personal task boards by user with id {} successfully found", userId);
        return allPersonalTaskBoards;
    }

    // admin
    @Override
    public List<TaskBoard> findAllCommonTaskBoardsByUserId(Long userId) {
        List<TaskBoard> allCommonTaskBoards = taskBoardRepository.findTaskBoardsByTaskAuthorIdAndType(userId, COMMON);
        log.info("All common task boards by user with id {}  successfully found", userId);
        return allCommonTaskBoards;
    }

    //admin
    //TODO doestn work?????
    @Override
    public TaskBoard findTaskBoardById(Long id) throws TaskBoardNotFoundException {
        TaskBoard taskBoard = taskBoardRepository.findTaskBoardById(id);
        return taskBoard;

//        TaskBoard oneTaskBoard = taskBoardRepository.findById(id)
//                .orElseThrow(() -> new TaskBoardNotFoundException(" TaskBoard with id " + id + "does not exist"));
//        if (oneTaskBoard == null) {
//            log.warn("Task board id: {} does not found by id: {}", oneTaskBoard, id);
//            throw new TaskBoardNotFoundException("task box id: " + id + "does not found");
//        }
//        log.info("Task box: {} found by id: {}", oneTaskBoard, id);
//        return oneTaskBoard;
    }

    //common
    //TODO doesnt work

    @Override
    public List<TaskBoard> findTaskBoardsByStartDateTime(LocalDateTime dateTime) {
        User currentTaskAuthor = securityContextHelper.getCurrentUser();
        List<TaskBoard> startDateTimeTaskBoards = taskBoardRepository.findTaskBoardByStartDateAndTaskAuthor(dateTime, currentTaskAuthor);
        log.info("Find all task board with start date {} by username {}", dateTime, currentTaskAuthor.getUsername());
        return startDateTimeTaskBoards;
    }

    //common
    //TODO check
    @Override
    public TaskBoard updateTaskBoard(TaskBoardDto taskBoardDto) {
        User currentTaskAuthor = securityContextHelper.getCurrentUser();
        TaskBoard taskBoard = taskBoardRepository.findTaskBoardByTaskAuthorAndId(currentTaskAuthor, taskBoardDto.getId());
        taskBoard.setUpdated(LocalDateTime.now());
        taskBoardMapper.updateTaskBoardMapper(taskBoardDto, taskBoard);
        log.info("Task board with id {} task author {} successfully updated", taskBoardDto.getId(), currentTaskAuthor.getUsername());
        return taskBoardRepository.save(taskBoard);
    }

    // common
    @Override
    public void deleteTaskBoard(Long id) throws TaskBoardNotFoundException {
        User currentTaskAuthor = securityContextHelper.getCurrentUser();
        boolean taskBoardExist = taskBoardRepository.existsById(id);
        if (!taskBoardExist) {
            log.warn("Task board with id: {} does not exist", id);
            throw new TaskBoardNotFoundException("Task board with id " + id + " does not exist"); // TODO is it wright to throw the exception or i need log
        }
        taskBoardRepository.deleteByTaskAuthorAndId(currentTaskAuthor, id);
        log.info("Task board with id: {} successfully deleted", id);
    }
}
