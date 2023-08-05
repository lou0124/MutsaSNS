package ohchangmin.sns.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class UserFollow {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_follow_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "following_id")
    private User following;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_id")
    private User follower;

    @Builder
    private UserFollow(User following, User follower) {
        this.following = following;
        this.follower = follower;
    }

    public static UserFollow createFollow(User following, User follower) {
        return UserFollow.builder()
                .following(following)
                .follower(follower)
                .build();
    }
}
