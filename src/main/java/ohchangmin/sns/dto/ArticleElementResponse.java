package ohchangmin.sns.dto;

import lombok.Getter;
import ohchangmin.sns.domain.Article;

import java.util.Objects;

@Getter
public class ArticleElementResponse {

    private final Long id;

    private final String username;

    private final String title;

    private String thumbnail;

    public ArticleElementResponse(Article article) {
        this.id = article.getId();
        this.username = article.getUser().getUsername();
        this.title = article.getTitle();
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = Objects.requireNonNullElse(thumbnail, "기본 이미지 경로");
    }
}
