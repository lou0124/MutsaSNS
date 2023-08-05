package ohchangmin.sns.service;

import ohchangmin.sns.domain.Article;
import ohchangmin.sns.domain.Comment;
import ohchangmin.sns.domain.User;
import ohchangmin.sns.repository.ArticleRepository;
import ohchangmin.sns.repository.CommentRepository;
import ohchangmin.sns.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

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
}