package com.santiago.taskmanager.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String password;
    private List<Long> projectIds;
    private List<Long> taskIds;
}
