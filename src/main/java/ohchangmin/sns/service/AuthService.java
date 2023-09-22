package ohchangmin.sns.service;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ohchangmin.sns.domain.User;
import ohchangmin.sns.exception.MisMatchedPassword;
import ohchangmin.sns.exception.NotFoundUser;
import ohchangmin.sns.exception.UsernameAlreadyExists;
import ohchangmin.sns.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signUp(SignUpServiceRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UsernameAlreadyExists();
        }
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = request.toEntityWithEncodedPassword(encodedPassword);
        userRepository.save(user);
    }

    public String login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(NotFoundUser::new);

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new MisMatchedPassword();
        }
        return user.getUsername();
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class SignUpServiceRequest {
        private String username;
        private String password;
        private String email;
        private String phone;

        public User toEntityWithEncodedPassword(String encodedPassword) {
            return User.builder()
                    .username(username)
                    .password(encodedPassword)
                    .email(email)
                    .phone(phone)
                    .build();
        }
    }
}
