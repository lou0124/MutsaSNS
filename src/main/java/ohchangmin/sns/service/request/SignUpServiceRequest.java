package ohchangmin.sns.service.request;

import lombok.Builder;
import lombok.Getter;
import ohchangmin.sns.domain.User;

@Getter
public class SignUpServiceRequest {

    private final String username;
    private final String password;
    private final String email;
    private final String phone;

    @Builder
    public SignUpServiceRequest(String username, String password, String email, String phone) {
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