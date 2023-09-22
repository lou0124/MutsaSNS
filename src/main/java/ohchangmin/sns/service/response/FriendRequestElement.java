package ohchangmin.sns.service.response;

import lombok.Getter;
import ohchangmin.sns.domain.UserFriend;

@Getter
public class FriendRequestElement {

    private final Long id;

    private final String username;

    public FriendRequestElement(UserFriend userFriend) {
        this.id = userFriend.getId();
        this.username = userFriend.getFromUsername();
    }
}
