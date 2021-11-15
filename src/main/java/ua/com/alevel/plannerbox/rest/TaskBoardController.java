package ua.com.alevel.plannerbox.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.*;
import ua.com.alevel.plannerbox.dto.TaskBoardDto;
import ua.com.alevel.plannerbox.dto.UserDto;
import ua.com.alevel.plannerbox.entity.TaskBoard;
import ua.com.alevel.plannerbox.entity.User;
import ua.com.alevel.plannerbox.service.TaskBoardService;
import ua.com.alevel.plannerbox.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
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

    @DeleteMapping(path = "{taskBoardId}")
    public void deleteTaskBoard(@PathVariable("taskBoardId") Long id) {
        taskBoardService.deleteTaskBoard(id);
    }
}
