package ohchangmin.sns.dto;

import lombok.Getter;
import ohchangmin.sns.domain.Article;

@Getter
public class ArticleResponse {

    private Long id;

    private String username;

    private String title;

    private String thumbnail;

    public ArticleResponse(Article article) {
        this.id = article.getId();
        this.username = article.getUser().getUsername();
        this.title = article.getTitle();
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
