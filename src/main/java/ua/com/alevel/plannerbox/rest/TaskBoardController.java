package ua.com.alevel.plannerbox.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.com.alevel.plannerbox.dto.TaskBoardDto;
import ua.com.alevel.plannerbox.entity.TaskBoard;
import ua.com.alevel.plannerbox.service.TaskBoardService;

import javax.validation.Valid;

@RestController
@RequestMapping("/task")
public class TaskBoardController {

    private final TaskBoardService taskBoardService;

    @Autowired
    public TaskBoardController(TaskBoardService taskBoardService) {
        this.taskBoardService = taskBoardService;
    }

    @PostMapping
    public ResponseEntity createTaskBoard(@RequestBody @Valid TaskBoard taskBoard, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return ResponseEntity.badRequest().body("/task"); // todo return smth another
        try {
            return ResponseEntity.ok(taskBoardService.createTaskBoard(taskBoard));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Mistake");
        }
    }

    @PutMapping(path = "{taskBoardId}")
    public ResponseEntity updateTaskBoard(@PathVariable("taskBoardId") Long id,
                                          @RequestBody TaskBoardDto taskBoardDto) {
        try {
            taskBoardDto.setId(id);
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
