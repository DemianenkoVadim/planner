package ua.com.alevel.plannerbox.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.plannerbox.dto.TaskBoardDto;
import ua.com.alevel.plannerbox.entity.TaskBoard;
import ua.com.alevel.plannerbox.entity.User;
import ua.com.alevel.plannerbox.mapper.TaskBoardMapper;
import ua.com.alevel.plannerbox.repository.TaskBoardRepository;
import ua.com.alevel.plannerbox.service.TaskBoardService;
import ua.com.alevel.plannerbox.service.UserService;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskBoardServiceImpl implements TaskBoardService {

    private final TaskBoardRepository taskBoardRepository;
    private final TaskBoardMapper taskBoardMapper;
    private final EntityManager em;

//    @Autowired
//    public TaskBoardServiceImpl(TaskBoardRepository taskBoardRepository) {
//        this.taskBoardRepository = taskBoardRepository;
//    }

    @Override
    @Transactional
    public TaskBoard createTaskBoard(TaskBoardDto taskBoardDto) {
        TaskBoard taskBoard = taskBoardMapper.dtoToModel(taskBoardDto);
        List<User> users = taskBoard.getUsers().stream().map(u -> em.getReference(User.class, u.getId())).toList();
        taskBoard.setUsers(users);
        TaskBoard createdTaskBoard = taskBoardRepository.saveAndFlush(taskBoard);
        log.info("Task created - task box: {} successfully created", createdTaskBoard);
        return createdTaskBoard;
    }

    @Override
    @Transactional
    public TaskBoard updateTaskBoard(TaskBoardDto taskBoardDto) {
//        getCurrentUser
        TaskBoard taskBoard = taskBoardRepository.findTaskBoardByIdAndUsers(taskBoardDto.getId(), taskBoardDto.getUsers().stream().findFirst().get().getId())
                .orElseThrow(() -> new IllegalStateException("TaskBoard with id " + taskBoardDto.getId() + " does not exist"));
        taskBoard.setUpdated(LocalDateTime.now());
//        TaskBoardMapper.INSTANCE.updateTaskBoardMapper(taskBoardDto, taskBoard);
        taskBoardMapper.updateTaskBoardMapper(taskBoardDto, taskBoard);
        return taskBoardRepository.save(taskBoard);
    }

    @Override
    public void deleteTaskBoard(Long id) {
        boolean taskBoardExist = taskBoardRepository.existsById(id);
        if (!taskBoardExist) {
            throw new IllegalStateException("TaskBoard with id " + id + " does not exist"); // TOdo is it wright to throw the exception or i need log
        }
        taskBoardRepository.deleteById(id);
        log.info("delete - one task box with id: {} successfully deleted", id);
    }

    @Override
    public List<TaskBoard> getAllTaskBoards() {
        List<TaskBoard> allTaskBoards = taskBoardRepository.findAll();
        log.info("get All task boxes - {} task boxes found", allTaskBoards.size());
        return allTaskBoards;
    }

    @Override
    public TaskBoard findById(Long id) {
        TaskBoard oneTaskBoard = taskBoardRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(" TaskBoard with id " + id + "does not exist"));
        if (oneTaskBoard == null) {
            log.warn("find by id - task box: {} does not found by id: {}", oneTaskBoard, id);
            return null; //Todo what should i return
        }
        log.info("find by id - one task box: {} found by id: {}", oneTaskBoard, id);
        return oneTaskBoard;
    }

    @Override
    public List<TaskBoard> findTaskBoardsByStartDateTime(LocalDateTime localDateTime) {
//        List<TaskBoard> taskBoards = taskBoardRepository.findTaskBoardByStartT
        return null;
    }
}
