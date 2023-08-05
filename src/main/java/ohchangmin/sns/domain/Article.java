package ohchangmin.sns.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ohchangmin.sns.exception.AlreadyDeletedArticle;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Article extends BaseTime {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "article", cascade = CascadeType.ALL)
    private List<ArticleImage> articleImages = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "article", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "article", cascade = CascadeType.ALL)
    private List<Like> likes = new ArrayList<>();

    private String title;

    @Lob
    private String content;

    private boolean draft;

    private boolean delete;

    private LocalDateTime deletedAt;

    @Builder
    private Article(String title, String content) {
        this.title = title;
        this.content = content;
        this.draft = false;
        this.delete = false;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void verifyUser(Long userId) {
        user.checkEquals(userId);
    }

    public String getUsername() {
        return this.user.getUsername();
    }

    public long getLikesCount() {
        return this.likes.size();
    }

    public void delete() {
        if (delete) {
            throw new AlreadyDeletedArticle();
        }
        delete = true;
        deletedAt = LocalDateTime.now();
    }

    public void addImages(List<ArticleImage> articleImages) {
        articleImages.forEach(articleImage -> {
            this.articleImages.add(articleImage);
            articleImage.setArticle(this);
        });
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public void addLike(Like like) {
        likes.add(like);
    }

}
