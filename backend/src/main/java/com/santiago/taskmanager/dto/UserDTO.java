package com.santiago.taskmanager.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String password;
    private List<Long> projectIds;
    private List<Long> taskIds;
    private List<String> roles;
}
