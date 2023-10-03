package com.tucanoo.crm.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDTO {
    Long id;

    @NotBlank
    String role;

    @NotBlank
    String username;

    @NotBlank
    String fullName;

    String password;

    Boolean enabled = false;
}
