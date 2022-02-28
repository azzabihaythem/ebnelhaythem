package com.medical.ebnelhaythem.dto;


import lombok.*;

import java.util.Date;
import java.util.List;

@Data
public class UserDto extends BasicDto {

    private String login;

    private String password;

    private String firstName;

    private String lastName;

    private Date birthDate;

    private boolean active;

    private Date creationDate;

    private Date updateDate;

    private List<RoleDto> roles;
}
