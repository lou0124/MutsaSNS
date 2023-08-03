package ohchangmin.sns.service;

import lombok.RequiredArgsConstructor;
import ohchangmin.sns.domain.User;
import ohchangmin.sns.exception.UsernameAlreadyExists;
import ohchangmin.sns.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void signUp(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UsernameAlreadyExists();
        }
        userRepository.save(user);
    }
}
