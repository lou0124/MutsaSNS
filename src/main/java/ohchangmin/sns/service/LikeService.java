package ohchangmin.sns.service;

import lombok.RequiredArgsConstructor;
import ohchangmin.sns.domain.Article;
import ohchangmin.sns.domain.User;
import ohchangmin.sns.exception.NotFoundArticle;
import ohchangmin.sns.exception.NotFoundUser;
import ohchangmin.sns.repository.ArticleRepository;
import ohchangmin.sns.repository.LikeRepository;
import ohchangmin.sns.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;

    @Transactional
    public void pushLike(Long userId, Long articleId) {
        Article article = articleRepository.findByIdWithUser(articleId)
                .orElseThrow(NotFoundArticle::new);
        // 다른 유저만 접근 가능 하도록 검사


        User user = userRepository.findById(userId)
                .orElseThrow(NotFoundUser::new);


    }
}
