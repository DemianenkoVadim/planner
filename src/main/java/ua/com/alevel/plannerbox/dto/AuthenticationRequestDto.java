package ua.com.alevel.plannerbox.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AuthenticationRequestDto {

    @NotEmpty(message = "Username should not be empty")
    private String username;

    @NotEmpty(message = "Password should not be empty")
    private String password;
}
