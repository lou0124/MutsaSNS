package ohchangmin.sns.service.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import ohchangmin.sns.domain.User;

@Builder
@AllArgsConstructor
@Getter
public class SignUpServiceRequest {
    private String username;
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