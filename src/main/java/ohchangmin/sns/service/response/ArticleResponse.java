package ohchangmin.sns.service.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import ohchangmin.sns.domain.Article;
import ohchangmin.sns.domain.ArticleImage;
import ohchangmin.sns.domain.Comment;

import java.util.List;
@Builder
@AllArgsConstructor
@Getter
public class ArticleResponse {

    private final Long id;

    private final String username;

    private final String title;

    private final String content;

    private final List<ArticleImageResponse> articleImages;

    private final List<CommentResponse> comments;

    private final long like;

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
                .filter(comment -> !comment.isDeleted())
                .map(CommentResponse::new)
                .toList();
    }

    @Builder
    @AllArgsConstructor
    @Data
    public static class ArticleImageResponse {

        private Long id;
        private String imagesUrl;

        private ArticleImageResponse(ArticleImage articleImage) {
            id = articleImage.getId();
            imagesUrl = articleImage.getImageUrl();
        }
    }

    @Builder
    @AllArgsConstructor
    @Data
    public static class CommentResponse {

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
