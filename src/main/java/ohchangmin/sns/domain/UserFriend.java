package ohchangmin.sns.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class UserFriend {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_friend_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_id")
    private User from;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_id")
    private User to;

    @Builder
    public UserFriend(User from, User to) {
        this.from = from;
        this.to = to;
    }

    public String getFromUsername() {
        return from.getUsername();
    }
}
