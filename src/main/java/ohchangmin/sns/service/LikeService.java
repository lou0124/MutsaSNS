package ohchangmin.sns.service;

import lombok.RequiredArgsConstructor;
import ohchangmin.sns.domain.Article;
import ohchangmin.sns.domain.Like;
import ohchangmin.sns.domain.User;
import ohchangmin.sns.exception.NotFoundArticle;
import ohchangmin.sns.exception.NotFoundUser;
import ohchangmin.sns.repository.ArticleRepository;
import ohchangmin.sns.repository.LikeRepository;
import ohchangmin.sns.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;

    @Transactional
    public void pushLike(Long userId, Long articleId) {

        Optional<Like> optionalLike = likeRepository.findByUserIdAndArticleId(userId, articleId);
        if(optionalLike.isPresent()) {
            likeRepository.delete(optionalLike.get());
            return;
        }

        Article article = articleRepository.findByIdWithUser(articleId)
                .orElseThrow(NotFoundArticle::new);
        article.verifyOtherUser(userId);

        User user = userRepository.findById(userId)
                .orElseThrow(NotFoundUser::new);

        likeRepository.save(Like.createLike(user, article));
    }
}
