package ohchangmin.sns.service;

import ohchangmin.sns.domain.Article;
import ohchangmin.sns.domain.User;
import ohchangmin.sns.request.ArticleCreateRequest;
import ohchangmin.sns.exception.NotFoundArticle;
import ohchangmin.sns.exception.UnauthorizedAccess;
import ohchangmin.sns.repository.ArticleImageRepository;
import ohchangmin.sns.repository.ArticleRepository;
import ohchangmin.sns.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class ArticleServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ArticleService articleService;

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    ArticleImageRepository articleImageRepository;

    @DisplayName("로그인한 유저는 피드를 등록할 수 있다.")
    @Test
    void uploadArticle() {
        //given
        User user = User.builder().username("user").password("1234").build();
        userRepository.save(user);

        ArticleCreateRequest request = ArticleCreateRequest.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        //when
        articleService.uploadArticle(user.getId(), request);

        //then
        List<Article> all = articleRepository.findAll();
        assertThat(all.size()).isEqualTo(1);
        assertThat(all.get(0))
                .extracting("title", "content")
                .contains("제목입니다.", "내용입니다.");

    }

    @DisplayName("로그인 한 유저는 피드에 이미지를 여러개 추가할 수 있다.")
    @Test
    void addArticleImages() {
        //given
        User user = User.builder().username("user").password("1234").build();
        userRepository.save(user);

        Article article = Article.builder().title("제목 입니다.").content("내용 입니다.").build();
        article.setUser(user);
        articleRepository.save(article);

        //when
        articleService.addArticleImages(user.getId(), article.getId(), List.of("이미지경로1", "이미지경로2"));

        //then
        Article findArticle = articleRepository.findById(article.getId()).get();
        assertThat(findArticle.getArticleImages().size()).isEqualTo(2);
    }

    @DisplayName("피드의 주인이 아닌경우 이미지 추가시 예외가 발생한다.")
    @Test
    void misMatchedUserAddArticleImages() {
        //given
        User user = User.builder().username("user").password("1234").build();
        userRepository.save(user);

        Article article = Article.builder().title("제목 입니다.").content("내용 입니다.").build();
        article.setUser(user);
        articleRepository.save(article);

        //when //then
        assertThatThrownBy(() -> articleService.addArticleImages(user.getId() + 1, article.getId(), List.of("이미지경로1", "이미지경로2")))
                .isInstanceOf(UnauthorizedAccess.class);
    }

    @DisplayName("피드의 주인은 피드를 삭제할 수 있다.")
    @Test
    void deleteArticle() {
        //given
        User user = User.builder().username("user").password("1234").build();
        userRepository.save(user);

        Article article = Article.builder().title("제목 입니다.").content("내용 입니다.").build();
        article.setUser(user);
        articleRepository.save(article);

        //when
        articleService.deleteArticle(user.getId(), article.getId());

        //then
        Optional<Article> optionalArticle = articleRepository.findById(article.getId());
        assertThat(optionalArticle).isEmpty();
    }

    @DisplayName("이미 삭제된 피드를 삭제할 시 예외가 발생한다.")
    @Test
    void alreadyDeletedArticle() {
        //given
        User user = User.builder().username("user").password("1234").build();
        userRepository.save(user);

        Article article = Article.builder().title("제목 입니다.").content("내용 입니다.").build();
        article.setUser(user);

        articleRepository.save(article);
        articleRepository.delete(article);

        //when //then
        assertThatThrownBy(() -> articleService.deleteArticle(user.getId(), article.getId()))
                .isInstanceOf(NotFoundArticle.class);
    }
}