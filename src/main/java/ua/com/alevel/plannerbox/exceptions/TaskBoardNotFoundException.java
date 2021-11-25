package ua.com.alevel.plannerbox.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TaskBoardNotFoundException extends RuntimeException {

    public TaskBoardNotFoundException(String message) {
        super(message);
    }
}
