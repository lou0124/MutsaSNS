package ohchangmin.sns.service;

import lombok.RequiredArgsConstructor;
import ohchangmin.sns.domain.User;
import ohchangmin.sns.repository.UserRepository;
import ohchangmin.sns.response.UserResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void changeProfile(Long userId, String profileImage) {
        User user = userRepository.findByIdOrThrow(userId);
        user.changProfileImage(profileImage);
    }

    public UserResponse findUser(Long userId) {
        User user = userRepository.findByIdOrThrow(userId);
        return new UserResponse(user);
    }
}
