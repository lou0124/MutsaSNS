package ohchangmin.sns.service;

import lombok.RequiredArgsConstructor;
import ohchangmin.sns.domain.Article;
import ohchangmin.sns.domain.Comment;
import ohchangmin.sns.domain.User;
import ohchangmin.sns.exception.NotFoundArticle;
import ohchangmin.sns.exception.NotFoundUser;
import ohchangmin.sns.repository.ArticleRepository;
import ohchangmin.sns.repository.CommentRepository;
import ohchangmin.sns.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public void writeComment(Long userId, Long articleId, String content) {
        Article article = articleRepository.findByIdWithUser(articleId)
                .orElseThrow(NotFoundArticle::new);
        User user = userRepository.findById(userId)
                .orElseThrow(NotFoundUser::new);

        Comment comment = Comment.writeComment(user, article, content);
        commentRepository.save(comment);
    }
}
