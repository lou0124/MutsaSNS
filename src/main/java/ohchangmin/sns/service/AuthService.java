package ohchangmin.sns.service;

import lombok.RequiredArgsConstructor;
import ohchangmin.sns.domain.User;
import ohchangmin.sns.exception.MisMatchedPassword;
import ohchangmin.sns.exception.NotFoundUser;
import ohchangmin.sns.exception.UsernameAlreadyExists;
import ohchangmin.sns.repository.UserRepository;
import ohchangmin.sns.request.LoginRequest;
import ohchangmin.sns.request.SignUpRequest;
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
    public void signUp(SignUpRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UsernameAlreadyExists();
        }
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = request.toEntityWithEncodedPassword(encodedPassword);
        userRepository.save(user);
    }

    public String login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(NotFoundUser::new);

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new MisMatchedPassword();
        }
        return user.getUsername();
    }
}
