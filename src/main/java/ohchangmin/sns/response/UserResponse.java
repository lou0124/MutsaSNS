package ohchangmin.sns.response;


import lombok.Getter;
import ohchangmin.sns.domain.User;

@Getter
public class UserResponse {

    private final String username;
    private final String profileImage;

    public UserResponse(User user) {
        this.username = user.getUsername();
        this.profileImage = user.getProfileImage();
    }
}
