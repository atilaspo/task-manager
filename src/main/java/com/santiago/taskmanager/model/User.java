package com.santiago.taskmanager.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String password;

    @ManyToMany(mappedBy = "collaborators")
    private List<Project> projects;

    @ManyToMany(mappedBy = "assignedUsers")
    private List<Task> assignedTasks = new ArrayList<>();
}
