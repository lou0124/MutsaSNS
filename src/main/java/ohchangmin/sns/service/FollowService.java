package ohchangmin.sns.service;

import lombok.RequiredArgsConstructor;
import ohchangmin.sns.domain.User;
import ohchangmin.sns.domain.UserFollow;
import ohchangmin.sns.exception.NotAllowFollowSelf;
import ohchangmin.sns.exception.NotFoundUserFollow;
import ohchangmin.sns.repository.UserFollowRepository;
import ohchangmin.sns.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FollowService {

    private final UserRepository userRepository;
    private final UserFollowRepository userFollowRepository;

    @Transactional
    public void follow(Long followingId, Long followerId) {
        if (followingId.equals(followerId)) {
            throw new NotAllowFollowSelf();
        }

        User following = userRepository.findByIdOrThrow(followingId);
        User follower = userRepository.findByIdOrThrow(followerId);
        UserFollow userFollow = UserFollow.createFollow(following, follower);
        userFollowRepository.save(userFollow);
    }

    @Transactional
    public void unfollow(Long followingId, Long followerId) {
        UserFollow userFollow = userFollowRepository.findByFollowingIdAndFollowerId(followingId, followerId)
                .orElseThrow(NotFoundUserFollow::new);
        userFollowRepository.delete(userFollow);
    }
}
