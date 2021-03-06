package ua.com.alevel.plannerbox.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ua.com.alevel.plannerbox.entity.status.TaskPriority;
import ua.com.alevel.plannerbox.entity.status.TaskStatus;
import ua.com.alevel.plannerbox.entity.status.TaskType;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskBoardDto {

    private Long id;

    @NotEmpty(message = "Task description should not be empty")
    @Size(min = 2, max = 30, message = "Description should be between 2 adn 30 characters")
    private String taskDescription;

    @FutureOrPresent(message = "The start date must be present or future")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startDate;

    @Future(message = "The end date must be greater than the existing one")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endDate;

    @NotNull(message = "Field status should not be empty")
    private TaskStatus status;

    @NotNull(message = "Field priority should not be empty")
    private TaskPriority priority;

    @NotNull(message = "Field type should not be empty")
    private TaskType type;

    private Set<UserDto> users;
}
