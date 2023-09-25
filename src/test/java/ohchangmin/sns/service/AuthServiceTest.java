package ohchangmin.sns.service;

import ohchangmin.sns.domain.User;
import ohchangmin.sns.exception.MisMatchedPassword;
import ohchangmin.sns.exception.NotFoundUser;
import ohchangmin.sns.exception.UsernameAlreadyExists;
import ohchangmin.sns.repository.UserRepository;
import ohchangmin.sns.service.request.SignUpServiceRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class AuthServiceTest {

    @Autowired AuthService authService;
    @Autowired UserRepository userRepository;
    @Autowired PasswordEncoder passwordEncoder;

    @DisplayName("회원가입시 회원등록이 된다.")
    @Test
    void signup() {
        //given
        SignUpServiceRequest request = SignUpServiceRequest.builder()
                .username("user")
                .password("1234")
                .email("abc@example.com")
                .phone("010-0000-0000")
                .build();
        //when
        authService.signUp(request);

        //then
        List<User> all = userRepository.findAll();
        assertThat(all.size()).isEqualTo(1);
        assertThat(all.get(0).getUsername()).isEqualTo("user");
    }

    @DisplayName("중복된 이름을 가진 회원이 존재할 시 회원가입을 하면 예외가 발생한다.")
    @Test
    void signupSameUsername() {
        //given
        User user = User.builder()
                .username("user")
                .password("1234")
                .build();

        userRepository.save(user);

        SignUpServiceRequest request = SignUpServiceRequest.builder()
                .username("user")
                .password("1234")
                .build();

        //when //then
        assertThatThrownBy(() -> authService.signUp(request))
                .isInstanceOf(UsernameAlreadyExists.class);
    }

    @DisplayName("등록된 회원은 로그인을 하여 유저이름을 얻을 수 있다.")
    @Test
    void login() {
        //given
        User user = User.builder()
                .username("user")
                .password(passwordEncoder.encode("1234"))
                .build();

        userRepository.save(user);

        //when
        String username = authService.login("user", "1234");

        //then
        assertThat(user.getUsername()).isEqualTo(username);
    }

    @DisplayName("로그인시 비밀번호가 일치하지 않으면 예외가 발생한다.")
    @Test
    void loginMisMatchPassword() {
        //given
        User user = User.builder()
                .username("user")
                .password(passwordEncoder.encode("1234"))
                .build();

        userRepository.save(user);

        //when //then
        assertThatThrownBy(() -> authService.login("user", "12345"))
                .isInstanceOf(MisMatchedPassword.class);
    }

    @DisplayName("존재 하지 않는 아이디로 로그인시 예외가 발생한다.")
    @Test
    void loginNotFoundUser() {
        //when //then
        assertThatThrownBy(() -> authService.login("user", "12345"))
                .isInstanceOf(NotFoundUser.class);
    }
}