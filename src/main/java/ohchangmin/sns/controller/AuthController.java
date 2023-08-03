package ohchangmin.sns.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ohchangmin.sns.dto.LoginRequest;
import ohchangmin.sns.dto.SignUpRequest;
import ohchangmin.sns.utils.JwtTokenUtils;
import ohchangmin.sns.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;

    @PostMapping("/signup")
    public void signUp(@RequestBody @Valid SignUpRequest request) {
        userService.signUp(request);
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody @Valid LoginRequest request) {
        Long userId = userService.login(request);
        String token = jwtTokenUtils.generateToken(String.valueOf(userId));
        return Map.of("token", token);
    }
}

