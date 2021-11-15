package ua.com.alevel.plannerbox.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import ua.com.alevel.plannerbox.dto.TaskBoardDto;
import ua.com.alevel.plannerbox.entity.TaskBoard;

@Mapper
public interface TaskBoardMapper {

    TaskBoardMapper INSTANCE = Mappers.getMapper(TaskBoardMapper.class);

    TaskBoardDto taskBoardDto(TaskBoard taskBoard);

    TaskBoard dtoToModel(TaskBoardDto taskBoardDto);

    void updateTaskBoardMapper(TaskBoardDto taskBoardDto, @MappingTarget TaskBoard taskBoard);
}
