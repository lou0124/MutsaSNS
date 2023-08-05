package ohchangmin.sns.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ArticleImage extends BaseTime {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    @Column(nullable = false)
    private String imageUrl;

    public ArticleImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Long getArticleId() {
        return article.getId();
    }

    public void verifyUser(Long userId) {
        article.verifyUser(userId);
    }
}
