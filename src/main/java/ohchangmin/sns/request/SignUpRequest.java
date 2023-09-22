package ohchangmin.sns.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ohchangmin.sns.domain.User;
import ohchangmin.sns.service.AuthService;

import static ohchangmin.sns.service.AuthService.*;

@Getter
@NoArgsConstructor
public class SignUpRequest {

    @NotBlank(message = "유저아이디를 입력해야합니다.")
    private String username;

    @NotBlank(message = "패스워드를 입력해야합니다.")
    private String password;

    private String email;

    private String phone;

    public SignUpServiceRequest toServiceRequest() {
        return SignUpServiceRequest.builder()
                .username(username)
                .password(password)
                .email(email)
                .password(password)
                .build();
    }
}
