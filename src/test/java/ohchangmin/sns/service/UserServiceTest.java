package ohchangmin.sns.service;

import ohchangmin.sns.domain.User;
import ohchangmin.sns.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired UserService userService;

    @Autowired UserRepository userRepository;

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

}