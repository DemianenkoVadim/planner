package ua.com.alevel.plannerbox.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ua.com.alevel.plannerbox.entity.status.TaskPriority;
import ua.com.alevel.plannerbox.entity.status.TaskStatus;
import ua.com.alevel.plannerbox.entity.status.TaskType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class TaskBoard extends BaseEntity {

    @Column(name = "description")
    private String taskDescription;

    @Column(name = "task_start_date")
    private LocalDateTime startDate;

    @Column(name = "task_end_date")
    private LocalDateTime endDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Column(name = "priority")
    @Enumerated(EnumType.STRING)
    private TaskPriority priority;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private TaskType type;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_task",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "task_id", referencedColumnName = "id")})
    @ToString.Exclude
    private List<User> users;
}
