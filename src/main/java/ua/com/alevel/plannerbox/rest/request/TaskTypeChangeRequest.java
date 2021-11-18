package ua.com.alevel.plannerbox.rest.request;

import lombok.Data;
import ua.com.alevel.plannerbox.entity.status.TaskType;

import javax.validation.constraints.NotNull;

@Data
public class TaskTypeChangeRequest {

    @NotNull
    private TaskType taskType;
}
