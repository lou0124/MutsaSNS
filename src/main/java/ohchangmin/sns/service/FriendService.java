package ohchangmin.sns.service;

import lombok.RequiredArgsConstructor;
import ohchangmin.sns.domain.User;
import ohchangmin.sns.domain.UserFriend;
import ohchangmin.sns.exception.NotAllowFollowSelf;
import ohchangmin.sns.exception.NotFoundUserFriend;
import ohchangmin.sns.repository.UserFriendRepository;
import ohchangmin.sns.repository.UserRepository;
import ohchangmin.sns.response.FriendRequestElement;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FriendService {

    private final UserRepository userRepository;
    private final UserFriendRepository userFriendRepository;

    @Transactional
    public void requestFriends(Long fromId, Long toId) {
        if (fromId.equals(toId)) {
            throw new NotAllowFollowSelf();
        }

        User from = userRepository.findByIdOrThrow(fromId);
        User to = userRepository.findByIdOrThrow(toId);
        saveUserFriend(from, to);
    }

    public List<FriendRequestElement> findRequestFriends(Long userId) {
        List<UserFriend> userFriends = userFriendRepository.findAllToId(userId);
        return createFriendRequestElement(userFriends);
    }

    @Transactional
    public void acceptFriend(Long userId, Long requestId) {
        UserFriend friendRequest = userFriendRepository.findByIdWithUsers(requestId)
                .orElseThrow(NotFoundUserFriend::new);
        friendRequest.verifyUser(userId);
        saveUserFriend(friendRequest.getTo(), friendRequest.getFrom());
    }

    @Transactional
    public void rejectFriend(Long userId, Long requestId) {
        UserFriend friendRequest = userFriendRepository.findByIdWithUsers(requestId)
                .orElseThrow(NotFoundUserFriend::new);
        friendRequest.verifyUser(userId);
        userFriendRepository.delete(friendRequest);
    }

    private List<FriendRequestElement> createFriendRequestElement(List<UserFriend> userFriends) {
        return userFriends.stream()
                .map(FriendRequestElement::new)
                .toList();
    }

    private void saveUserFriend(User from, User to) {
        UserFriend userFriend = UserFriend.builder()
                .from(from)
                .to(to)
                .build();
        userFriendRepository.save(userFriend);
    }
}
