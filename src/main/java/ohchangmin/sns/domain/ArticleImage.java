package ohchangmin.sns.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ArticleImage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    @Column(nullable = false)
    private String imageUrl;

    private boolean thumbnail;

    public ArticleImage(String imageUrl) {
        this.imageUrl = imageUrl;
        this.thumbnail = false;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public void setThumbnail(boolean thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Long getArticleId() {
        return article.getId();
    }

    public void verifyUser(Long userId) {
        article.verifyUser(userId);
    }
}
