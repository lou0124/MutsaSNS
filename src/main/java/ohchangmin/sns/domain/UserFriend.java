package ohchangmin.sns.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ohchangmin.sns.exception.UnauthorizedAccess;

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
    private UserFriend(User from, User to) {
        this.from = from;
        this.to = to;
    }

    public String getFromUsername() {
        return from.getUsername();
    }

    public void verifyUser(Long toId) {
        if (!this.to.isEqualsId(toId)) {
            throw new UnauthorizedAccess("해당 사용자에 대한 친구 요청이 아닙니다.");
        }
    }
}
