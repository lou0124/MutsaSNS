package ohchangmin.sns.service;

import lombok.RequiredArgsConstructor;
import ohchangmin.sns.domain.User;
import ohchangmin.sns.domain.UserFollow;
import ohchangmin.sns.domain.UserFriend;
import ohchangmin.sns.repository.UserFriendRepository;
import ohchangmin.sns.response.FriendRequestElement;
import ohchangmin.sns.response.UserResponse;
import ohchangmin.sns.exception.NotAllowFollowSelf;
import ohchangmin.sns.exception.NotFoundUserFollow;
import ohchangmin.sns.repository.UserFollowRepository;
import ohchangmin.sns.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final UserFollowRepository userFollowRepository;
    private final UserFriendRepository userFriendRepository;

    @Transactional
    public void changeProfile(Long userId, String profileImage) {
        User user = userRepository.findByIdOrThrow(userId);
        user.changProfileImage(profileImage);
    }

    public UserResponse findUser(Long userId) {
        User user = userRepository.findByIdOrThrow(userId);
        return new UserResponse(user);
    }

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

    @Transactional
    public void requestFriends(Long fromId, Long toId) {
        User from = userRepository.findByIdOrThrow(fromId);
        User to = userRepository.findByIdOrThrow(toId);
        UserFriend userFriend = UserFriend.builder()
                .from(from)
                .to(to)
                .build();
        userFriendRepository.save(userFriend);
    }

    public List<FriendRequestElement> findRequestFriends(Long userId) {
        List<UserFriend> userFriends = userFriendRepository.findAllToId(userId);
        return createFriendRequestElement(userFriends);
    }

    private List<FriendRequestElement> createFriendRequestElement(List<UserFriend> userFriends) {
        return userFriends.stream()
                .map(FriendRequestElement::new)
                .toList();
    }
}
