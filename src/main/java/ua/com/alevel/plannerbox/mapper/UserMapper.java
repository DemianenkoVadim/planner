package ua.com.alevel.plannerbox.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ua.com.alevel.plannerbox.dto.UserDto;
import ua.com.alevel.plannerbox.entity.User;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto userToDto(Optional<User> user);

    User userFromDto(UserDto userDto);

    void updateUser(UserDto userDto, @MappingTarget User user);
}
