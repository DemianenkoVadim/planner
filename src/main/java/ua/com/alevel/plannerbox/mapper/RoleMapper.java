package ua.com.alevel.plannerbox.mapper;

import org.mapstruct.Mapper;
import ua.com.alevel.plannerbox.dto.RoleDto;
import ua.com.alevel.plannerbox.dto.UserDto;
import ua.com.alevel.plannerbox.entity.User;
import ua.com.alevel.plannerbox.entity.UserRole;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    UserDto roleToDto(UserRole role);

    User userFromDto(RoleDto userDto);
}
