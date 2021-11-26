package ua.com.alevel.plannerbox.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.plannerbox.dto.TaskBoardDto;
import ua.com.alevel.plannerbox.entity.TaskBoard;
import ua.com.alevel.plannerbox.entity.User;
import ua.com.alevel.plannerbox.entity.status.TaskPriority;
import ua.com.alevel.plannerbox.entity.status.TaskStatus;
import ua.com.alevel.plannerbox.entity.status.TaskType;
import ua.com.alevel.plannerbox.exceptions.TaskBoardNotFoundException;
import ua.com.alevel.plannerbox.mapper.TaskBoardMapper;
import ua.com.alevel.plannerbox.repository.TaskBoardRepository;
import ua.com.alevel.plannerbox.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final TaskBoardMapper taskBoardMapper;
    private final SecurityContextHelper securityContextHelper;

    @Autowired
    public TaskBoardServiceImpl(TaskBoardRepository taskBoardRepository,
                                UserRepository userRepository, TaskBoardMapper taskBoardMapper,
                                SecurityContextHelper securityContextHelper) {
        this.taskBoardRepository = taskBoardRepository;
        this.userRepository = userRepository;
        this.taskBoardMapper = taskBoardMapper;
        this.securityContextHelper = securityContextHelper;
    }

    @Override
    public TaskBoard createTaskBoard(TaskBoardDto taskBoardDto) {
        TaskBoard taskBoard = taskBoardMapper.dtoToModel(taskBoardDto);
        taskBoard.setTaskAuthor(securityContextHelper.getCurrentUser());
        taskBoard.setCreated(LocalDateTime.now());
        taskBoard.setUpdated(LocalDateTime.now());
        TaskBoard createdTaskBoard = taskBoardRepository.save(taskBoard);
        log.info("Task board: {} successfully created", createdTaskBoard);
        return createdTaskBoard;
    }

    @Override
    public List<TaskBoard> findAllCurrentUsersTaskBoards() {
        User currentTaskAuthor = securityContextHelper.getCurrentUser();
        List<TaskBoard> allTaskBoards = taskBoardRepository.findTaskBoardsByTaskAuthor(currentTaskAuthor);
        log.info("Find all current user task boxes - {} task boxes found", allTaskBoards);
        return allTaskBoards;
    }

    @Override
    public List<TaskBoard> findAllCurrentUsersPersonalTaskBoards() {
        User currentTaskAuthor = securityContextHelper.getCurrentUser();
        List<TaskBoard> allPersonalTaskBoards = taskBoardRepository.findTaskBoardsByTaskAuthorAndType(currentTaskAuthor, PERSONAL);
        log.info("Find all current user personal task boxes - {} task boxes found", allPersonalTaskBoards);
        return allPersonalTaskBoards;
    }

    @Override
    public List<TaskBoard> findAllCurrentUsersCommonTaskBoards() {
        User currentTaskAuthor = securityContextHelper.getCurrentUser();
        List<TaskBoard> allCommonTaskBoards = taskBoardRepository.findTaskBoardsByTaskAuthorAndType(currentTaskAuthor, COMMON);
        log.info("Find all current user common task boxes - {} task boxes found", allCommonTaskBoards);
        return allCommonTaskBoards;
    }

    @Override
    public List<TaskBoard> findAllTaskBoards() {
        List<TaskBoard> allTaskBoards = taskBoardRepository.findAll();
        log.info("Fetching all task boards");
        return allTaskBoards;
    }

    @Override
    public List<TaskBoard> findAllTaskBoardsByUserId(Long userId) {
        List<TaskBoard> allUserTaskBoards = taskBoardRepository.findTaskBoardByTaskAuthorId(userId);
        log.info("All task boards by user id: {} successfully find", userId);
        return allUserTaskBoards;
    }

    @Override
    public List<TaskBoard> findAllPersonalTaskBoardsByUserId(Long userId) {
        List<TaskBoard> allPersonalTaskBoards = taskBoardRepository.findTaskBoardsByTaskAuthorIdAndType(userId, PERSONAL);
        log.info("All personal task boards by user with id {} successfully found", userId);
        return allPersonalTaskBoards;
    }

    @Override
    public List<TaskBoard> findAllCommonTaskBoardsByUserId(Long userId) {
        List<TaskBoard> allCommonTaskBoards = taskBoardRepository.findTaskBoardsByTaskAuthorIdAndType(userId, COMMON);
        log.info("All common task boards by user with id {}  successfully found", userId);
        return allCommonTaskBoards;
    }

    @Override
    public TaskBoard findTaskBoardById(Long id) {
        TaskBoard oneTaskBoard = taskBoardRepository.findById(id)
                .orElseThrow(() -> new TaskBoardNotFoundException("Task board with id " + id + " does not exist"));
        log.info("Task box: {} found by id: {}", oneTaskBoard, id);
        return oneTaskBoard;
    }

    @Override
    public List<TaskBoard> findTaskBoardsByStartDateTime(LocalDateTime dateTime) {
        User currentTaskAuthor = securityContextHelper.getCurrentUser();
        List<TaskBoard> startDateTimeTaskBoards = taskBoardRepository.findTaskBoardsByStartDateAndTaskAuthor(dateTime, currentTaskAuthor);
        log.info("Find all task board with start date {} by username {}", dateTime, currentTaskAuthor.getUsername());
        return startDateTimeTaskBoards;
    }

    @Override
    public void changeTaskType(Long id, TaskType taskType) {
        TaskBoard taskBoard = taskBoardRepository.findById(id)
                .orElseThrow(() -> new TaskBoardNotFoundException("Task board with id: " + id + " does not exist"));
        log.info("Task board with id {} found", id);
        taskBoard.setType(taskType);
        taskBoardRepository.save(taskBoard);
    }

    @Override
    public void changeTaskStatus(Long id, TaskStatus taskStatus) {
        TaskBoard taskBoard = taskBoardRepository.findById(id)
                .orElseThrow(() -> new TaskBoardNotFoundException("Task board with id: " + id + " does not exist"));
        log.info("Task board with id: {} found", id);
        taskBoard.setStatus(taskStatus);
        taskBoardRepository.save(taskBoard);
    }

    @Override
    public void changeTaskPriority(Long id, TaskPriority taskPriority) {
        TaskBoard taskBoard = taskBoardRepository.findById(id)
                .orElseThrow(() -> new TaskBoardNotFoundException("Task board with id: " + id + " does not exist"));
        log.info("Task board with id: {} found", id);
        taskBoard.setPriority(taskPriority);
        taskBoardRepository.save(taskBoard);
    }

    @Override
    public TaskBoard addNewUserToCommonTaskBoard(Long userId, Long taskBoardId) {
        User currentTaskAuthor = securityContextHelper.getCurrentUser();
        TaskBoard taskBoard = taskBoardRepository.findTaskBoardByTaskAuthorIdAndId(currentTaskAuthor.getId(), taskBoardId);
        if (taskBoard == null) {
            throw new TaskBoardNotFoundException("Task board is not found");
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User with id: " + userId + " does not found"));
        taskBoard.setUpdated(LocalDateTime.now());
        taskBoard.getUsers().add(user);
        taskBoard = taskBoardRepository.save(taskBoard);
        log.info("New user with id: {} is added to task board with id: {}", userId, taskBoardId);
        return taskBoard;
    }

    @Override
    public TaskBoard updateTaskBoard(TaskBoardDto taskBoardDto) {
        User currentTaskAuthor = securityContextHelper.getCurrentUser();
        TaskBoard taskBoard = taskBoardRepository.findTaskBoardByTaskAuthorAndId(currentTaskAuthor, taskBoardDto.getId());
        taskBoard.setUpdated(LocalDateTime.now());
        taskBoardMapper.updateTaskBoardMapper(taskBoardDto, taskBoard);
        log.info("Task board with id {} task author {} successfully updated", taskBoardDto.getId(), currentTaskAuthor.getUsername());
        return taskBoardRepository.save(taskBoard);
    }

    @Override
    public void deleteTaskBoard(Long id) throws TaskBoardNotFoundException {
        User currentTaskAuthor = securityContextHelper.getCurrentUser();
        TaskBoard taskBoardExist = taskBoardRepository.findTaskBoardByTaskAuthorAndId(currentTaskAuthor, id);
        if (taskBoardExist == null) {
            log.warn("Task board with id: {} does not exist for current username {} ", id, currentTaskAuthor.getUsername());
            throw new TaskBoardNotFoundException("Task board with id " + id + " does not exist");
        }
        taskBoardRepository.deleteByTaskAuthorAndId(currentTaskAuthor, id);
        log.info("Task board with id: {} successfully deleted", id);
    }
}
