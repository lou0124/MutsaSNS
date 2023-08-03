package ohchangmin.sns.service;

import lombok.RequiredArgsConstructor;
import ohchangmin.sns.domain.User;
import ohchangmin.sns.exception.NotFoundUser;
import ohchangmin.sns.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void changeProfile(Long userId, String profileImage) {
        User user = userRepository.findById(userId)
                .orElseThrow(NotFoundUser::new);

        user.changProfileImage(profileImage);
    }
}
