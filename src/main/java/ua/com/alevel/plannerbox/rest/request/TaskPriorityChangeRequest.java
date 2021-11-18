package ua.com.alevel.plannerbox.rest.request;

import lombok.Data;
import ua.com.alevel.plannerbox.entity.status.TaskPriority;

import javax.validation.constraints.NotNull;

@Data
public class TaskPriorityChangeRequest {

    @NotNull
    private TaskPriority taskPriority;
}
