package ohchangmin.sns.service;

import jakarta.persistence.EntityManager;
import ohchangmin.sns.domain.Article;
import ohchangmin.sns.domain.Like;
import ohchangmin.sns.domain.User;
import ohchangmin.sns.exception.UnauthorizedAccess;
import ohchangmin.sns.repository.ArticleRepository;
import ohchangmin.sns.repository.LikeRepository;
import ohchangmin.sns.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class LikeServiceTest {

    @Autowired UserRepository userRepository;
    @Autowired ArticleRepository articleRepository;
    @Autowired LikeService likeService;
    @Autowired LikeRepository likeRepository;
    @Autowired EntityManager em;

    @DisplayName("로그인한 사용자는 본인이 아닌 피드에 좋아요를 누를 수 있다.")
    @Test
    void pushLike() {
        //given
        User user1 = User.builder().username("user1").password("1234").build();
        userRepository.save(user1);

        User user2 = User.builder().username("user2").password("1234").build();
        userRepository.save(user2);

        Article article = Article.builder().title("제목 입니다.").content("내용 입니다.").build();
        article.setUser(user1);
        articleRepository.save(article);

        //when
        likeService.pushLike(user2.getId(), article.getId());

        //then
        List<Like> all = likeRepository.findAll();
        assertThat(all.size()).isEqualTo(1);
        assertThat(all.get(0).getUser()).isEqualTo(user2);
    }

    @DisplayName("로그인한 사용자는 본인의 피드에 좋아요를 누를 시 예외가 발생한다.")
    @Test
    void otherUserPushLike() {
        //given
        User user1 = User.builder().username("user1").password("1234").build();
        userRepository.save(user1);

        Article article = Article.builder().title("제목 입니다.").content("내용 입니다.").build();
        article.setUser(user1);
        articleRepository.save(article);

        //when //then
        assertThatThrownBy(() -> likeService.pushLike(user1.getId(), article.getId()))
                .isInstanceOf(UnauthorizedAccess.class);
    }

    @DisplayName("동일한 피드에 좋아요를 2번 누를 시 좋아요는 취소가 된다.")
    @Test
    void cancelLike() {
        //given
        User user1 = User.builder().username("user1").password("1234").build();
        userRepository.save(user1);

        User user2 = User.builder().username("user2").password("1234").build();
        userRepository.save(user2);

        Article article = Article.builder().title("제목 입니다.").content("내용 입니다.").build();
        article.setUser(user1);
        articleRepository.save(article);

        likeService.pushLike(user2.getId(), article.getId());
        em.clear();

        //when
        likeService.pushLike(user2.getId(), article.getId());

        //then
        List<Like> all = likeRepository.findAll();
        assertThat(all.isEmpty()).isTrue();
    }
}