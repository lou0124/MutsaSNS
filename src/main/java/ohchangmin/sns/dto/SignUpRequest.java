package ohchangmin.sns.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ohchangmin.sns.domain.User;

@Getter
@NoArgsConstructor
public class SignUpRequest {

    @NotBlank(message = "유저아이디를 입력해야합니다.")
    private String username;

    @NotBlank(message = "패스워드를 입력해야합니다.")
    private String password;

    private String email;

    private String phone;

    public User toEntityWithEncodedPassword(String encodedPassword) {
        return User.builder()
                .username(username)
                .password(encodedPassword)
                .email(email)
                .phone(phone)
                .build();
    }
}
