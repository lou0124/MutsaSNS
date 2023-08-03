package ohchangmin.sns.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ohchangmin.sns.domain.User;
import ohchangmin.sns.dto.SignUpRequest;
import ohchangmin.sns.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public void signUp(@RequestBody @Valid SignUpRequest request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = request.toEntityWithEncodedPassword(encodedPassword);
        userService.signUp(user);
    }
}
