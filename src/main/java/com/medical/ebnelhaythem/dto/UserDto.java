package com.medical.ebnelhaythem.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
