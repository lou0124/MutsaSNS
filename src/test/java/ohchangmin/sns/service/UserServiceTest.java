package ohchangmin.sns.service;

import ohchangmin.sns.domain.User;
import ohchangmin.sns.exception.UsernameAlreadyExists;
import ohchangmin.sns.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired UserService userService;

    @Autowired UserRepository userRepository;

    @DisplayName("회원가입시 회원등록이 된다.")
    @Test
    void signup() {
        //given
        User user = User.builder()
                .username("user")
                .password("1234")
                .build();

        //when
        userService.signUp(user);

        //then
        List<User> all = userRepository.findAll();
        assertThat(all.size()).isEqualTo(1);
        assertThat(all.get(0).getId()).isEqualTo(user.getId());
    }

    @DisplayName("중복된 이름을 가진 회원이 존재할 시 회원가입을 하면 예외가 발생한다.")
    @Test
    void signupSameUsername() {
        //given
        User user1 = User.builder()
                .username("user1")
                .password("1234")
                .build();

        userRepository.save(user1);

        User user2 = User.builder()
                .username("user1")
                .password("1234")
                .build();

        //when //then
        assertThatThrownBy(() -> userService.signUp(user2))
                .isInstanceOf(UsernameAlreadyExists.class);
    }

}