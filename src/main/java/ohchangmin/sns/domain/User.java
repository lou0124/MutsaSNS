package ohchangmin.sns.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ohchangmin.sns.exception.MisMatchedUser;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Article> articles = new ArrayList<>();

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String profileImage;

    private String email;

    private String phone;

    @Builder
    private User(String username, String password, String email, String phone) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }

    public void changProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public void uploadArticle(Article article) {
        articles.add(article);
        article.setUser(this);
    }

    public void checkEquals(Long userId) {
        if (!id.equals(userId)) {
            throw new MisMatchedUser();
        }
    }
}
