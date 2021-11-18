package ua.com.alevel.plannerbox.rest.request;

import lombok.Data;
import ua.com.alevel.plannerbox.entity.status.TaskStatus;

import javax.validation.constraints.NotNull;

@Data
public class TaskStatusChangeRequest {

    @NotNull
    private TaskStatus taskStatus;
}
