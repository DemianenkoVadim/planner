package ua.com.alevel.plannerbox.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleDto {

    private Long id;

    @NotEmpty(message = "Role name should not be empty")
    @Size(min = 2, max = 30, message = "Role name should be between 2 and 30 characters")
    private String roleName;
}
