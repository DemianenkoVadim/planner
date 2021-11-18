package ua.com.alevel.plannerbox.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.alevel.plannerbox.dto.TaskBoardDto;
import ua.com.alevel.plannerbox.entity.TaskBoard;
import ua.com.alevel.plannerbox.exceptions.TaskBoardNotFoundException;
import ua.com.alevel.plannerbox.rest.request.ParticipantRequest;
import ua.com.alevel.plannerbox.rest.request.TaskPriorityChangeRequest;
import ua.com.alevel.plannerbox.rest.request.TaskStatusChangeRequest;
import ua.com.alevel.plannerbox.rest.request.TaskTypeChangeRequest;
import ua.com.alevel.plannerbox.service.TaskBoardService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskBoardController {

    private final TaskBoardService taskBoardService;

    @Autowired
    public TaskBoardController(TaskBoardService taskBoardService) {
        this.taskBoardService = taskBoardService;
    }

    @PostMapping
    public ResponseEntity<TaskBoard> createTaskBoard(@RequestBody @Valid TaskBoardDto taskBoardDto) {
        return ResponseEntity.ok(taskBoardService.createTaskBoard(taskBoardDto));
    }

    @GetMapping(path = "/currentUser")
    public ResponseEntity<List<TaskBoard>> finAllCurrentUserTaskBoards() {
        return ResponseEntity.ok(taskBoardService.findAllCurrentUsersTaskBoards());
    }

    @GetMapping(path = "/personals/currentUser")
    public ResponseEntity<List<TaskBoard>> findAllCurrentUsersPersonalTaskBoards() {
        return ResponseEntity.ok(taskBoardService.findAllCurrentUsersPersonalTaskBoards());
    }

    @GetMapping(path = "/commons/currentUser")
    public ResponseEntity<List<TaskBoard>> findAllCurrentUsersCommonTaskBoards() {
        return ResponseEntity.ok(taskBoardService.findAllCurrentUsersCommonTaskBoards());
    }

//    @GetMapping(path = "/personals/currentUser/priority")
//    public ResponseEntity<Optional<TaskBoard>> findAllCurrentUsersPersonalTaskBoardsSortedByPriority() {
//        return ResponseEntity.ok(taskBoardService.findAllCurrentUsersPersonalTaskBoardsSortedByPriority());
//    }

    @GetMapping
    public ResponseEntity<List<TaskBoard>> findAllTaskBoards() {
        return ResponseEntity.ok(taskBoardService.findAllTaskBoards());
    }

    @GetMapping(path = "/users/{id}")
    public ResponseEntity<List<TaskBoard>> findAllTaskBoardsByUserId(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(taskBoardService.findAllTaskBoardsByUserId(id));
    }

    @GetMapping(path = "/personals/users/{id}")
    public ResponseEntity<List<TaskBoard>> findAllPersonalTaskBoardsByUserId(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(taskBoardService.findAllPersonalTaskBoardsByUserId(id));
    }

    @GetMapping(path = "/commons/users/{id}")
    public ResponseEntity<List<TaskBoard>> findAllCommonTaskBoardsByUserId(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(taskBoardService.findAllCommonTaskBoardsByUserId(id));
    }

    @GetMapping(path = "{id}")
    public TaskBoard findTaskBoardById(@PathVariable(name = "id") Long id) throws TaskBoardNotFoundException {
        return taskBoardService.findTaskBoardById(id);
    }

    @GetMapping(path = "/startDateTime/{date}")
    public ResponseEntity<List<TaskBoard>> findTaskBoardsByStartDateTime(@PathVariable(name = "date")
                                                                         @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDateTime date) {
        return ResponseEntity.ok(taskBoardService.findTaskBoardsByStartDateTime(date));
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<TaskBoard> updateTaskBoard(@RequestBody TaskBoardDto taskBoardDto,
                                                     @PathVariable("id") Long id) {
        taskBoardDto.setId(id);
        return ResponseEntity.ok(taskBoardService.updateTaskBoard(taskBoardDto));
    }

    @PutMapping(path = "/{id}/types")
    public void changeTaskType(@PathVariable(name = "id") Long id,
                               @Valid @RequestBody TaskTypeChangeRequest request) throws TaskBoardNotFoundException {
        taskBoardService.changeTaskType(id, request.getTaskType());
    }

    @PutMapping(path = "/{id}/statuses")
    public void changeTaskStatus(@PathVariable(name = "id") Long id,
                                 @Valid @RequestBody TaskStatusChangeRequest request) throws TaskBoardNotFoundException {
        taskBoardService.changeTaskStatus(id, request.getTaskStatus());
    }

    @PutMapping(path = "/{id}/priority")
    public void changeTaskPriority(@PathVariable(name = "id") Long id,
                                   @Valid @RequestBody TaskPriorityChangeRequest request) throws TaskBoardNotFoundException {
        taskBoardService.changeTaskPriority(id, request.getTaskPriority());
    }


    @PutMapping(path = "/{id}/participant")
    public ResponseEntity<TaskBoard> addNewUserToTaskBoard(@PathVariable(name = "id") Long taskBoardId,
                                                           @RequestBody ParticipantRequest request) {
        return ResponseEntity.ok(taskBoardService.addNewUserToCommonTaskBoard(request.getParticipantId(), taskBoardId));
    }

    @DeleteMapping(path = "{id}")
    public void deleteTaskBoard(@PathVariable("id") Long id) throws TaskBoardNotFoundException {
        taskBoardService.deleteTaskBoard(id);
    }
}
