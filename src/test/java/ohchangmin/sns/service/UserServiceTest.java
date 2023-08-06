package ohchangmin.sns.service;

import ohchangmin.sns.domain.User;
import ohchangmin.sns.domain.UserFollow;
import ohchangmin.sns.domain.UserFriend;
import ohchangmin.sns.exception.NotAllowFollowSelf;
import ohchangmin.sns.repository.UserFollowRepository;
import ohchangmin.sns.repository.UserFriendRepository;
import ohchangmin.sns.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired UserService userService;
    @Autowired UserRepository userRepository;
    @Autowired UserFollowRepository userFollowRepository;
    @Autowired UserFriendRepository userFriendRepository;

    @DisplayName("유저는 자신의 프로필 이미지를 변경할 수 있다.")
    @Test
    void changImage() {
        //given
        User user = User.builder()
                .username("user")
                .password("1234")
                .build();

        userRepository.save(user);

        //when
        userService.changeProfile(user.getId(), "image_path");

        //then
        User findUser = userRepository.findById(user.getId()).get();
        assertThat(findUser.getProfileImage()).isEqualTo("image_path");
    }

    @DisplayName("로그인 한 사용자는 다른 사람을 팔로우 할 수 있다.")
    @Test
    void follow() {
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
        userService.follow(user1.getId(), user2.getId());

        //then
        List<UserFollow> all = userFollowRepository.findAll();
        assertThat(all.size()).isEqualTo(1);
    }

    @DisplayName("로그인 한 사용자는 자기 자신을 팔로우 할 수 없다.")
    @Test
    void notAllowFollowSelf() {
        //given
        User user = User.builder()
                .username("user")
                .password("1234")
                .build();
        userRepository.save(user);

        //when //then
        assertThatThrownBy(() -> userService.follow(user.getId(), user.getId()))
                .isInstanceOf(NotAllowFollowSelf.class);
    }

    @DisplayName("팔로잉 한 사용자는 팔로워를 언팔로우 할 수 있습니다.")
    @Test
    void unfollow() {
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
        userFollowRepository.save(UserFollow.createFollow(user1, user2));

        //when
        userService.unfollow(user1.getId(), user2.getId());

        //then
        List<UserFollow> all = userFollowRepository.findAll();
        assertThat(all.isEmpty()).isTrue();
    }

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
        userService.requestFriends(user1.getId(), user2.getId());

        //then
        List<UserFriend> all = userFriendRepository.findAll();
        assertThat(all.size()).isEqualTo(1);
        assertThat(all.get(0))
                .extracting("from", "to")
                .contains(user1, user2);
    }
}