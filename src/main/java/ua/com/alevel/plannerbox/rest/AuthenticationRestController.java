package ua.com.alevel.plannerbox.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ua.com.alevel.plannerbox.dto.AuthenticationRequestDto;
import ua.com.alevel.plannerbox.entity.User;
import ua.com.alevel.plannerbox.security.jwt.JwtTokenProvider;
import ua.com.alevel.plannerbox.service.UserService;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/auth/")
@CrossOrigin
@RequiredArgsConstructor
public class AuthenticationRestController {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("login")
    public Map<String, String> login(@RequestBody @Valid AuthenticationRequestDto requestDto) {
        String username = requestDto.getUsername();
        User user = userService.findByUsername(username);
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        if (user == null || user.getPassword().equals(encodedPassword)) {
            throw new BadCredentialsException("Invalid username or password");
        }
        String token = jwtTokenProvider.createToken(username, user.getRoles());
        return Map.of(
                "username", username,
                "token", token
        );
    }
}
