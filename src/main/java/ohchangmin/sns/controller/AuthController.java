package ohchangmin.sns.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ohchangmin.sns.request.LoginRequest;
import ohchangmin.sns.request.SignUpRequest;
import ohchangmin.sns.jwt.JwtTokenUtils;
import ohchangmin.sns.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtTokenUtils jwtTokenUtils;

    @PostMapping("/signup")
    public void signUp(@RequestBody @Valid SignUpRequest request) {
        authService.signUp(request);
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody @Valid LoginRequest request) {
        String username = authService.login(request);
        String token = jwtTokenUtils.generateToken(username);
        return Map.of("token", token);
    }
}

