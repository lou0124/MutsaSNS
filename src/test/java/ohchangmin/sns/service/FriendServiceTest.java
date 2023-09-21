package ohchangmin.sns.service;

import ohchangmin.sns.domain.User;
import ohchangmin.sns.domain.UserFriend;
import ohchangmin.sns.repository.UserFriendRepository;
import ohchangmin.sns.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class FriendServiceTest {

    @Autowired UserRepository userRepository;
    @Autowired FriendService friendService;
    @Autowired UserFriendRepository userFriendRepository;

    @DisplayName("로그인 한 사용자는 친구 요청을 할 수 있다.")
    @Test
    void requestFriends() {
        //given
        User user1 = User.builder()
                .username("user1")
                .password("1234")
                .build();
        User user2 = User.builder()
                .username("user2")
                .password("1234")
                .build();
        userRepository.save(user1);
        userRepository.save(user2);

        //when
        friendService.requestFriends(user1.getId(), user2.getId());

        //then
        List<UserFriend> all = userFriendRepository.findAll();
        assertThat(all.size()).isEqualTo(1);
        assertThat(all.get(0))
                .extracting("from", "to", "request")
                .contains(user1, user2, true);
    }

    @DisplayName("사용자는 친구 요청을 수락 할 수 있다.")
    @Test
    void requestFriendAccept() {
        //given
        User user1 = User.builder()
                .username("user1")
                .password("1234")
                .build();
        User user2 = User.builder()
                .username("user2")
                .password("1234")
                .build();
        userRepository.save(user1);
        userRepository.save(user2);
        UserFriend userFriend = UserFriend.builder()
                .from(user1)
                .to(user2)
                .request(true)
                .build();
        userFriendRepository.save(userFriend);

        //when
        friendService.acceptFriend(user2.getId(), userFriend.getId());

        //then
        List<UserFriend> all = userFriendRepository.findAll();
        assertThat(all.size()).isEqualTo(2);
        assertThat(all.get(0).getTo()).isEqualTo(all.get(1).getFrom());
        assertThat(all.get(1).getTo()).isEqualTo(all.get(0).getFrom());
        assertThat(all.get(0).isRequest()).isFalse();
        assertThat(all.get(1).isRequest()).isFalse();
    }

    @DisplayName("사용자는 친구 요청을 거절 할 수 있다.")
    @Test
    void requestFriendReject() {
        //given
        User user1 = User.builder()
                .username("user1")
                .password("1234")
                .build();
        User user2 = User.builder()
                .username("user2")
                .password("1234")
                .build();
        userRepository.save(user1);
        userRepository.save(user2);
        UserFriend userFriend = UserFriend.builder()
                .from(user1)
                .to(user2)
                .request(true)
                .build();
        userFriendRepository.save(userFriend);

        //when
        friendService.rejectFriend(user2.getId(), userFriend.getId());

        //then
        List<UserFriend> all = userFriendRepository.findAll();
        assertThat(all.isEmpty()).isTrue();
    }
}