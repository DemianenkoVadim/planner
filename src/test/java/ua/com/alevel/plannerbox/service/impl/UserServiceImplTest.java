package ua.com.alevel.plannerbox.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ua.com.alevel.plannerbox.dto.UserDto;
import ua.com.alevel.plannerbox.entity.User;
import ua.com.alevel.plannerbox.entity.UserRole;
import ua.com.alevel.plannerbox.entity.status.UserStatus;
import ua.com.alevel.plannerbox.exceptions.UserAlreadyExistException;
import ua.com.alevel.plannerbox.mapper.UserMapper;
import ua.com.alevel.plannerbox.repository.RoleRepository;
import ua.com.alevel.plannerbox.repository.UserRepository;
import ua.com.alevel.plannerbox.security.SecurityContextHelper;
import ua.com.alevel.plannerbox.service.UserService;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private SecurityContextHelper securityContextHelper;
    @Mock
    private UserMapper userMapper;

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(
                userRepository,
                roleRepository,
                passwordEncoder,
                securityContextHelper,
                userMapper);
    }

    @Test
    void registerUserWithExistedUserName() {
        User testUser = new User();
        testUser.setUsername("test");

        when(userRepository.findByUsername("test")).thenReturn(testUser);

        UserDto userDto = new UserDto();
        userDto.setUsername("test");

        assertThatThrownBy(() -> userService.registerUser(userDto)).isInstanceOf(UserAlreadyExistException.class);

        verify(userRepository, times(1)).findByUsername(testUser.getUsername());
    }

    @Test
    void registerUserWithNewUserName() {

        User testUser = new User();
        testUser.setUsername("test");

        UserDto userDto = new UserDto();
        userDto.setUsername("test");
        userDto.setPassword("test");

        when(userRepository.findByUsername("test")).thenReturn(null);
        when(userMapper.userFromDto(userDto)).thenReturn(testUser);
        when(passwordEncoder.encode(userDto.getPassword())).thenReturn("test");
        when(roleRepository.findByRole(anyString())).thenReturn(new UserRole());
        when(userRepository.save(any())).thenReturn(testUser);

        User user = userService.registerUser(userDto);
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(user.getCreated()).isNotNull();
        assertThat(user.getUpdated()).isNotNull();

        verify(userRepository, times(1)).findByUsername(testUser.getUsername());
        verify(userRepository, times(1)).save(testUser);
    }
}
