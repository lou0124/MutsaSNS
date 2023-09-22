package ohchangmin.sns.service.response;

import lombok.Getter;
import ohchangmin.sns.domain.Article;

import java.util.Objects;

@Getter
public class ArticleElement {

    private final Long id;

    private final String username;

    private final String title;

    private String thumbnail;

    public ArticleElement(Article article) {
        this.id = article.getId();
        this.username = article.getUsername();
        this.title = article.getTitle();
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = Objects.requireNonNullElse(thumbnail, "기본 이미지 경로");
    }
}
