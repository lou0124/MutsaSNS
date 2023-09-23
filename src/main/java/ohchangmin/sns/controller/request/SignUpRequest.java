package ohchangmin.sns.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ohchangmin.sns.service.request.SignUpServiceRequest;

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
    private SignUpRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public SignUpServiceRequest toServiceRequest() {
        return SignUpServiceRequest.builder()
                .username(username)
                .password(password)
                .email(email)
                .password(password)
                .build();
    }
}
