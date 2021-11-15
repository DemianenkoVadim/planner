package ua.com.alevel.plannerbox.mapper;

import ua.com.alevel.plannerbox.dto.UserDto;
import ua.com.alevel.plannerbox.entity.User;

public interface UserMapper {

    UserDto userDto(User user);

    User userToModel(UserDto userDto);
}
