package com.santiago.taskmanager.model;

import com.santiago.taskmanager.enums.TaskPriority;
import com.santiago.taskmanager.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String title;

    String description;

    LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    TaskStatus status;

    @Enumerated(EnumType.STRING)
    TaskPriority priority;

    @ManyToOne
    @JoinColumn(name="project_id", nullable = false)
    Project project;

}
