package ohchangmin.sns.dto;

import lombok.Getter;
import ohchangmin.sns.domain.Article;
import ohchangmin.sns.domain.ArticleImage;
import ohchangmin.sns.domain.Comment;

import java.util.List;

@Getter
public class ArticleResponse {

    private Long id;

    private String username;

    private String title;

    private String content;

    private List<String> articleImagesUrl;

    private List<CommentResponse> comments;

    private long like;


    public ArticleResponse(Article article, List<Comment> comments, List<ArticleImage> articleImages, long like) {
        this.id = article.getId();
        this.username = article.getUsername();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.like = like;
        this.articleImagesUrl = articleImages.stream()
                .map(ArticleImage::getImageUrl)
                .toList();
        this.comments = comments.stream()
                .map(CommentResponse::new)
                .toList();
    }

    private static class CommentResponse {

        private String username;
        private String content;

        private CommentResponse(Comment comment) {
            username = comment.getUsername();
            content = comment.getContent();
        }
    }
}
