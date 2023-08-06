package ohchangmin.sns;

import lombok.RequiredArgsConstructor;
import ohchangmin.sns.domain.Article;
import ohchangmin.sns.domain.Comment;
import ohchangmin.sns.domain.User;
import ohchangmin.sns.domain.UserFollow;
import ohchangmin.sns.repository.CommentRepository;
import ohchangmin.sns.repository.UserFollowRepository;
import ohchangmin.sns.repository.UserRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Profile("local")
@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final UserFollowRepository userFollowRepository;
    private final PasswordEncoder passwordEncoder;

    @EventListener(ApplicationReadyEvent.class)
    public void initData() {
        User userA = User.builder()
                .username("userA")
                .password(passwordEncoder.encode("1234"))
                .build();
        User userB = User.builder()
                .username("userB")
                .password(passwordEncoder.encode("1234"))
                .build();
        User userC = User.builder()
                .username("userC")
                .password(passwordEncoder.encode("1234"))
                .build();
        Article article1 = Article.builder().title("article1").content("content").build();
        Article article2 = Article.builder().title("article2").content("content").build();
        Article article3 = Article.builder().title("article3").content("content").build();
        userB.uploadArticle(article1);
        userB.uploadArticle(article2);
        userC.uploadArticle(article3);
        userRepository.save(userA);
        userRepository.save(userB);
        userRepository.save(userC);

        Comment comment1 = Comment.builder().user(userA).article(article1).content("A->B").build();
        Comment comment2 = Comment.builder().user(userB).article(article3).content("B->C").build();
        commentRepository.save(comment1);
        commentRepository.save(comment2);

        UserFollow userFollow1 = UserFollow.createFollow(userA, userB);
        UserFollow userFollow2 = UserFollow.createFollow(userA, userC);
        userFollowRepository.save(userFollow1);
        userFollowRepository.save(userFollow2);
    }
}
