package ohchangmin.sns.dto;

import lombok.Data;
import lombok.Getter;
import ohchangmin.sns.domain.Article;
import ohchangmin.sns.domain.ArticleImage;
import ohchangmin.sns.domain.Comment;

import java.util.List;

@Getter
@Data
public class ArticleResponse {

    private Long id;

    private String username;

    private String title;

    private String content;

    private List<ArticleImageResponse> articleImages;

    private List<CommentResponse> comments;

    private long like;

    public ArticleResponse(Article article) {
        this.id = article.getId();
        this.username = article.getUsername();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.like = article.getLikesCount();
        this.articleImages = article.getArticleImages().stream()
                .map(ArticleImageResponse::new)
                .toList();
        this.comments = article.getComments().stream()
                .map(CommentResponse::new)
                .toList();
    }

    @Data
    private static class ArticleImageResponse {

        private Long id;
        private String imagesUrl;

        private ArticleImageResponse(ArticleImage articleImage) {
            id = articleImage.getId();
            imagesUrl = articleImage.getImageUrl();
        }
    }

    @Data
    private static class CommentResponse {

        private Long id;
        private String username;
        private String content;

        private CommentResponse(Comment comment) {
            id = comment.getId();
            username = comment.getUsername();
            content = comment.getContent();
        }
    }
}
