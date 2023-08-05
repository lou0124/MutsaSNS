package ohchangmin.sns.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "like_article")
@Entity
@Getter
@NoArgsConstructor
public class Like extends BaseTime {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    @Builder
    private Like(User user, Article article) {
        this.user = user;
        this.article = article;
    }

    public static Like createLike(User user, Article article) {
        Like like = Like.builder()
                .user(user)
                .article(article)
                .build();

        article.addLike(like);
        return like;
    }
}
