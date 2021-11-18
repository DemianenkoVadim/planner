package ua.com.alevel.plannerbox.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import ua.com.alevel.plannerbox.dto.TaskBoardDto;
import ua.com.alevel.plannerbox.entity.TaskBoard;

@Mapper(componentModel = "spring")
public interface TaskBoardMapper {


    TaskBoardDto taskBoardDto(TaskBoard taskBoard);

    TaskBoard dtoToModel(TaskBoardDto taskBoardDto);

    void updateTaskBoardMapper(TaskBoardDto taskBoardDto, @MappingTarget TaskBoard taskBoard);
}
