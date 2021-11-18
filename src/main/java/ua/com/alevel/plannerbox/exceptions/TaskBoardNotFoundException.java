package ua.com.alevel.plannerbox.exceptions;

public class TaskBoardNotFoundException extends RuntimeException {

    public TaskBoardNotFoundException(String message) {
        super(message);
    }
}
