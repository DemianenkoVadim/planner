package ua.com.alevel.plannerbox.mapper;

import org.mapstruct.Mapper;
import ua.com.alevel.plannerbox.dto.UserDto;
import ua.com.alevel.plannerbox.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto userDto(User user);

    User userToModel(UserDto userDto);
}
