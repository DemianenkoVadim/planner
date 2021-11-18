package ua.com.alevel.plannerbox.rest.request;

import lombok.Data;
import ua.com.alevel.plannerbox.entity.status.UserStatus;

import javax.validation.constraints.NotNull;

@Data
public class UserStatusChangeRequest {

    @NotNull
    private UserStatus userStatus;
}
