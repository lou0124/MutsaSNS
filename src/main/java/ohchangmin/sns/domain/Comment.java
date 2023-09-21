package ohchangmin.sns.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ohchangmin.sns.exception.AlreadyDeletedArticle;
import ohchangmin.sns.exception.AlreadyDeletedComment;
import ohchangmin.sns.exception.UnauthorizedAccess;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@SQLDelete(sql = "UPDATE comment SET deleted = true WHERE comment_id = ?")
@Where(clause = "deleted is false")
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

    private boolean deleted = false;

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
        if (!user.isEqualsId(userId)) {
            throw new UnauthorizedAccess("다른 사용자는 해당 댓글의 기능을 사용할 수 없습니다.");
        }
    }

    public void modify(String content) {
        this.content = content;
    }
}
