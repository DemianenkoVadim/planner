package ua.com.alevel.plannerbox.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        // given
        User testUser = new User();
        testUser.setUsername("test");
        // when
        when(userRepository.findByUsername("test")).thenReturn(testUser);
        // then
        UserDto userDto = new UserDto();
        userDto.setUsername("test");

        assertThatThrownBy(() -> userService.registerUser(userDto)).isInstanceOf(UserAlreadyExistException.class);

        verify(userRepository, times(1)).findByUsername(testUser.getUsername());
    }

    @Test
    void registerUserWithNewUserName() {
        // given
        User testUser = new User();
        testUser.setUsername("test");

        UserDto userDto = new UserDto();
        userDto.setUsername("test");
        userDto.setPassword("test");

        // when
        when(userRepository.findByUsername("test")).thenReturn(null);
        when(userMapper.userFromDto(userDto)).thenReturn(testUser);
        when(passwordEncoder.encode(userDto.getPassword())).thenReturn("test");
        when(roleRepository.findByRole(anyString())).thenReturn(new UserRole());
        when(userRepository.save(any())).thenReturn(testUser);

        // then
        User user = userService.registerUser(userDto);
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(user.getCreated()).isNotNull();
        assertThat(user.getUpdated()).isNotNull();

        verify(userRepository, times(1)).findByUsername(testUser.getUsername());
        verify(userRepository, times(1)).save(testUser);
    }

//    @Test
//    void testFindUserById() {
//        var testUser = new User();
//        testUser.setId(2L);
//
////        UserDto userDto = new UserDto();
////        userDto.setEmail("email@gmail.com");
////        userDto.setPassword("password");
//
//
//        when(userRepository.findUserById(2L)).thenReturn(Optional.empty());
//
//        var user = userService.findUserById(2L);
//
//        verify(userRepository, times(1)).findById(testUser.getId());
//

//        Long existingUserId = 1L;
//        Long notExistingUserId = 213L;
//        var testUser = new User(
//                "username",
//                "firstName",
//                "lastName",
//                "email@gmail.com",
//                "password",
//                UserStatus.ACTIVE);
//
//
//        when(userRepository.findUserById(notExistingUserId)).thenReturn(Optional.empty());
//
//        Optional<User> notExistedUser = userService.findUserById(notExistingUserId);
//
//        assertThat(notExistedUser).isEmpty();

//        verify(userRepository, times(1)).findById(notExistingUserId);
//    }
//
//    @Test
//    void shouldReturnListOfUsers() {
//        var username = "test";
//        var password = "password";
//
//        var testUser = new User();
//
//        testUser.setUsername(username);
//        testUser.setPassword(password);
//
//        List<User> userList = new ArrayList<>();
//        userList.add(testUser);
//
//        when(userRepository.findAll()).thenReturn(userList.stream().toList());
//
//        assertThat(userList.size()).isGreaterThan(0);
//
//        verify(userRepository, times(1)).findAll();
//    }
//
//
//    @Test
//    @Disabled
//    void deleteUser() {
//    }
//
//    @Test
//    @Disabled
//    void updateUser() {
//        String username = "test";
////        String firstName = "test";
////        String lastName = "test";
////        String email = "test@gmail.com";
////        String password = "test";
////        UserStatus userStatus = UserStatus.ACTIVE;
//
//    }
//
//    @Test
//    void testFinByUsernameAndId() {
//        // given
//        User testUser = new User();
//        testUser.setUsername("test");
//        testUser.setId(1L);
//
//        UserDto testUserDto = new UserDto();
//        testUserDto.setId(1L);
//        testUserDto.setUsername("test");
//
//        String currentUser = securityContextHelper.getCurrentUser().getUsername();
//        // when
//        when(userRepository.findByUsernameAndId(currentUser, testUser.getId())).thenReturn(testUser);
//        // then
//        UserDto userDto = new UserDto();
//        userDto.setUsername("test");
//        userDto.setId(1L);
//
////        assertThatExceptionOfType(UserNotFoundException.class)
////                .isThrownBy(() -> userService.findUserById());
//
//        verify(userRepository, times(1)).findByUsername(testUser.getUsername());
//    }
}
