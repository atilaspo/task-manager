package com.santiago.taskmanager.dto;

import com.santiago.taskmanager.enums.TaskPriority;
import com.santiago.taskmanager.enums.TaskStatus;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskDTO {
    private Long id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private TaskStatus status;
    private TaskPriority priority;
    private Long projectId;
    private List<Long> assignedUsersId;
}
