package ua.com.alevel.plannerbox.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.alevel.plannerbox.dto.TaskBoardDto;
import ua.com.alevel.plannerbox.dto.UserDto;
import ua.com.alevel.plannerbox.entity.TaskBoard;
import ua.com.alevel.plannerbox.entity.User;
import ua.com.alevel.plannerbox.exceptions.TaskBoardNotFoundException;
import ua.com.alevel.plannerbox.service.TaskBoardService;
import ua.com.alevel.plannerbox.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskBoardController {

    private final TaskBoardService taskBoardService;
    private final UserService userService;

    @Autowired
    public TaskBoardController(TaskBoardService taskBoardService, UserService userService) {
        this.taskBoardService = taskBoardService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<TaskBoard> createTaskBoard(@RequestBody @Valid TaskBoardDto taskBoard, Principal principal) {
        System.out.println(principal);
        User user = userService.findByUsername(principal.getName());
        UserDto uDto = new UserDto();
        uDto.setId(user.getId());
        taskBoard.setUsers(List.of(uDto));
        return ResponseEntity.ok(taskBoardService.createTaskBoard(taskBoard));
    }

    @GetMapping(path = "/allCurrentUserTasks")
    public ResponseEntity<List<TaskBoard>> finAllCurrentUserTaskBoards() {
        return ResponseEntity.ok(taskBoardService.findAllCurrentUsersTaskBoards());
    }

    @GetMapping(path = "/allPersonalCurrentUserTasks")
    public ResponseEntity<List<TaskBoard>> findAllCurrentUsersPersonalTaskBoards() {
        return ResponseEntity.ok(taskBoardService.findAllCurrentUsersPersonalTaskBoards());
    }

    @GetMapping(path = "/allCommonCurrentUserTasks")
    public ResponseEntity<List<TaskBoard>> findAllCurrentUsersCommonTaskBoards() {
        return ResponseEntity.ok(taskBoardService.findAllCurrentUsersCommonTaskBoards());
    }

//    @GetMapping(path = "/allPersonalCurrentUserTaskSortedByPriority")
//    public ResponseEntity<Optional<TaskBoard>> findAllCurrentUsersPersonalTaskBoardsSortedByPriority() {
//        return ResponseEntity.ok(taskBoardService.findAllCurrentUsersPersonalTaskBoardsSortedByPriority());
//    }

    @GetMapping(path = "/allTaskBoards")
    public ResponseEntity<List<TaskBoard>> findAllTaskBoards() {
        return ResponseEntity.ok(taskBoardService.findAllTaskBoards());
    }

    @GetMapping(path = "/allTasksByUserId/{id}")
    public ResponseEntity<List<TaskBoard>> findAllTaskBoardsByUserId(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(taskBoardService.findAllTaskBoardsByUserId(id));
    }

    @GetMapping(path = "/allPersonalTaskByUserid/{id}")
    public ResponseEntity<List<TaskBoard>> findAllPersonalTaskBoardsByUserId(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(taskBoardService.findAllPersonalTaskBoardsByUserId(id));
    }

    @GetMapping(path = "/allCommonTaskByUserid/{id}")
    public ResponseEntity<List<TaskBoard>> findAllCommonTaskBoardsByUserId(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(taskBoardService.findAllCommonTaskBoardsByUserId(id));
    }

    // TODD exception thrown, is it wright??
    @GetMapping(path = "/taskBoardById/{id}")
    public ResponseEntity<TaskBoard> findTaskBoardById(@PathVariable(name = "id") Long id) throws TaskBoardNotFoundException {
        return new ResponseEntity(taskBoardService.findTaskBoardById(id), HttpStatus.OK);
    }

    @GetMapping(path = "/taskByStartDateTime{date}")
    public ResponseEntity<List<TaskBoard>> findTaskBoardsByStartDateTime(@PathVariable(name = "date") LocalDateTime date) {
        return ResponseEntity.ok(taskBoardService.findTaskBoardsByStartDateTime(date));
    }

    @PutMapping(path = "{taskBoardId}")
    public ResponseEntity updateTaskBoard(@PathVariable("taskBoardId") Long id,
                                          @RequestBody TaskBoardDto taskBoardDto, Principal principal) {
        try {
            taskBoardDto.setId(id);
            User user = userService.findByUsername(principal.getName());
            UserDto uDto = new UserDto();
            uDto.setId(user.getId());
            taskBoardDto.setUsers(List.of(uDto));
            return ResponseEntity.ok(taskBoardService.updateTaskBoard(taskBoardDto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Mistake");
        }
    }

    // TODO Exception is thrown is it wright
    @DeleteMapping(path = "/deleteTaskBoard{id}")
    public void deleteTaskBoard(@PathVariable("id") Long id) throws TaskBoardNotFoundException {
        taskBoardService.deleteTaskBoard(id);
    }
}
