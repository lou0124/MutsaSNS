package ohchangmin.sns.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTime {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    @Lob
    private String content;

    public String getUsername() {
        return user.getUsername();
    }

    @Builder
    private Comment(User user, Article article, String content) {
        this.user = user;
        this.article = article;
        this.content = content;
    }

    public static Comment createComment(User user, Article article, String content) {
        Comment comment = Comment.builder()
                .user(user)
                .article(article)
                .content(content)
                .build();

        article.addComment(comment);
        return comment;
    }

    public void verifyUser(Long userId) {
        user.checkEquals(userId);
    }

    public void modify(String content) {
        this.content = content;
    }
}
