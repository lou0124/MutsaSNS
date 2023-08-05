package ohchangmin.sns.service;

import ohchangmin.sns.domain.Article;
import ohchangmin.sns.domain.Comment;
import ohchangmin.sns.domain.User;
import ohchangmin.sns.exception.MisMatchedUser;
import ohchangmin.sns.exception.UnauthorizedAccess;
import ohchangmin.sns.repository.ArticleRepository;
import ohchangmin.sns.repository.CommentRepository;
import ohchangmin.sns.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class CommentServiceTest {

    @Autowired UserRepository userRepository;

    @Autowired ArticleRepository articleRepository;

    @Autowired CommentService commentService;

    @Autowired CommentRepository commentRepository;

    @DisplayName("로그인한 유저는 피드에 댓글을 달 수 있다.")
    @Test
    void writeComment() {
        //given
        User user1 = User.builder().username("user1").password("1234").build();
        userRepository.save(user1);

        User user2 = User.builder().username("user2").password("1234").build();
        userRepository.save(user2);

        Article article = Article.builder().title("제목 입니다.").content("내용 입니다.").build();
        article.setUser(user1);
        articleRepository.save(article);

        //when
        commentService.writeComment(user2.getId(), article.getId(), "댓글 내용입니다.");

        //then
        List<Comment> all = commentRepository.findAll();
        assertThat(all.size()).isEqualTo(1);
        assertThat(all.get(0))
                .extracting("user", "article", "content")
                .contains(user2, article, "댓글 내용입니다.");
    }

    @DisplayName("댓글의 작성자는 자신의 댓글을 수정할 수 있다.")
    @Test
    void modifyComment() {
        //given
        User user1 = User.builder().username("user1").password("1234").build();
        userRepository.save(user1);

        User user2 = User.builder().username("user2").password("1234").build();
        userRepository.save(user2);

        Article article = Article.builder().title("제목 입니다.").content("내용 입니다.").build();
        article.setUser(user1);
        articleRepository.save(article);

        Comment comment = Comment.createComment(user2, article, "댓글입니다.");
        commentRepository.save(comment);

        //when
        commentService.modifyComment(user2.getId(), article.getId(), "수정할래요.");

        //then
        Comment findComment = commentRepository.findById(comment.getId()).get();
        assertThat(findComment.getContent()).isEqualTo("수정할래요.");
    }

    @DisplayName("다른사용자가 댓글을 수정할 시 예외가 발생한다.")
    @Test
    void otherUserModifyComment() {
        //given
        User user1 = User.builder().username("user1").password("1234").build();
        userRepository.save(user1);

        User user2 = User.builder().username("user2").password("1234").build();
        userRepository.save(user2);

        Article article = Article.builder().title("제목 입니다.").content("내용 입니다.").build();
        article.setUser(user1);
        articleRepository.save(article);

        Comment comment = Comment.createComment(user2, article, "댓글입니다.");
        commentRepository.save(comment);

        //when //then
        assertThatThrownBy(() -> commentService.modifyComment(user1.getId(), article.getId(), "수정할래요."))
                .isInstanceOf(UnauthorizedAccess.class);
    }

}