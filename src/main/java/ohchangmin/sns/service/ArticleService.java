package ohchangmin.sns.service;

import lombok.RequiredArgsConstructor;
import ohchangmin.sns.domain.Article;
import ohchangmin.sns.domain.ArticleImage;
import ohchangmin.sns.domain.User;
import ohchangmin.sns.dto.ArticleCreateRequest;
import ohchangmin.sns.repository.ArticleRepository;
import ohchangmin.sns.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleService {

    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;

    @Transactional
    public void uploadArticle(Long userId, ArticleCreateRequest request) {
        User user = userRepository.findByIdOrThrow(userId);
        Article article = request.toEntity();
        user.uploadArticle(article);
    }

    @Transactional
    public void addArticleImages(Long userId, Long articleId, List<String> imageUrls) {
        Article article = articleRepository.findByIdOrThrow(articleId);
        article.verifyUser(userId);

        List<ArticleImage> articleImages = imageUrls.stream()
                .map(ArticleImage::new)
                .toList();

        article.addImages(articleImages);
    }
}
