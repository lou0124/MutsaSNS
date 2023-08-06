package ohchangmin.sns.service;

import ohchangmin.sns.domain.User;
import ohchangmin.sns.domain.UserFollow;
import ohchangmin.sns.exception.NotAllowFollowSelf;
import ohchangmin.sns.repository.UserFollowRepository;
import ohchangmin.sns.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class FollowServiceTest {

    @Autowired UserRepository userRepository;
    @Autowired UserFollowRepository userFollowRepository;
    @Autowired
    FollowService followService;

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
        followService.follow(user1.getId(), user2.getId());

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
        assertThatThrownBy(() -> followService.follow(user.getId(), user.getId()))
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
        followService.unfollow(user1.getId(), user2.getId());

        //then
        List<UserFollow> all = userFollowRepository.findAll();
        assertThat(all.isEmpty()).isTrue();
    }

}