package ohchangmin.sns.service.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import ohchangmin.sns.domain.User;

@Builder
@AllArgsConstructor
@Getter
public class UserResponse {

    private final String username;
    private final String profileImage;

    public UserResponse(User user) {
        this.username = user.getUsername();
        this.profileImage = user.getProfileImage();
    }
}
