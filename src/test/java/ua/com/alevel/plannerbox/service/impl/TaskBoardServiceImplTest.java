package ua.com.alevel.plannerbox.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.com.alevel.plannerbox.dto.TaskBoardDto;
import ua.com.alevel.plannerbox.entity.TaskBoard;
import ua.com.alevel.plannerbox.entity.User;
import ua.com.alevel.plannerbox.mapper.TaskBoardMapper;
import ua.com.alevel.plannerbox.repository.TaskBoardRepository;
import ua.com.alevel.plannerbox.repository.UserRepository;
import ua.com.alevel.plannerbox.security.SecurityContextHelper;
import ua.com.alevel.plannerbox.service.TaskBoardService;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskBoardServiceImplTest {

    @Mock
    private TaskBoardRepository taskBoardRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TaskBoardMapper taskBoardMapper;
    @Mock
    private SecurityContextHelper securityContextHelper;

    private TaskBoardService taskBoardService;


    @BeforeEach
    void setUp() {
        taskBoardService = new TaskBoardServiceImpl(
                taskBoardRepository,
                userRepository,
                taskBoardMapper,
                securityContextHelper);
    }

    @Test
    void canCreateNewTaskBoard() {
        // given
        var taskBoardId = 1L;
        TaskBoardDto testTaskBoardDto = new TaskBoardDto();

        TaskBoard testTaskBoard = new TaskBoard();
        testTaskBoard.setId(taskBoardId);
        User testUser = getTestUser();
        when(taskBoardMapper.dtoToModel(testTaskBoardDto)).thenReturn(testTaskBoard);
        when(securityContextHelper.getCurrentUser()).thenReturn(testUser);
        when(taskBoardRepository.save(testTaskBoard)).thenReturn(testTaskBoard);

        // when
        TaskBoard actualTaskBoard = taskBoardService.createTaskBoard(testTaskBoardDto);

        // then
        assertThat(actualTaskBoard.getId()).isEqualTo(testTaskBoard.getId());
        assertThat(actualTaskBoard.getCreated()).isNotNull();
        assertThat(actualTaskBoard.getUpdated()).isNotNull();
        assertThat(actualTaskBoard.getTaskAuthor()).isEqualTo(testUser);

        verify(taskBoardMapper, only()).dtoToModel(testTaskBoardDto);
        verify(taskBoardMapper, times(1)).dtoToModel(testTaskBoardDto);
        verify(securityContextHelper, times(1)).getCurrentUser();
        verify(taskBoardRepository, only()).save(testTaskBoard);
    }

    @Test
    void findAllCurrentUsersTaskBoards() {
        // given
        User testUser = getTestUser();
        when(securityContextHelper.getCurrentUser()).thenReturn(testUser);
        when(taskBoardRepository.findTaskBoardsByTaskAuthor(testUser)).thenReturn(List.of(new TaskBoard()));
        // when
        List<TaskBoard> allTasks = taskBoardService.findAllCurrentUsersTaskBoards();
        // then
        assertThat(allTasks.size()).isEqualTo(1);

        verify(securityContextHelper, times(1)).getCurrentUser();
        verify(taskBoardRepository, only()).findTaskBoardsByTaskAuthor(testUser);
    }

    @Test
    void addNewUserToCommonTaskBoard() {


    }

    private User getTestUser() {
        User testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("test");
        return testUser;
    }
}