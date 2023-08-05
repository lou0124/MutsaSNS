package ohchangmin.sns.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
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

    @Builder
    private SignUpRequest(String username, String password, String email, String phone) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }

    public User toEntityWithEncodedPassword(String encodedPassword) {
        return User.builder()
                .username(username)
                .password(encodedPassword)
                .email(email)
                .phone(phone)
                .build();
    }
}
