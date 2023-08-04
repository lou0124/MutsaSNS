package ohchangmin.sns.service;

import lombok.RequiredArgsConstructor;
import ohchangmin.sns.domain.Article;
import ohchangmin.sns.domain.ArticleImage;
import ohchangmin.sns.domain.User;
import ohchangmin.sns.dto.ArticleCreateRequest;
import ohchangmin.sns.dto.ArticleResponse;
import ohchangmin.sns.exception.NotFoundArticle;
import ohchangmin.sns.repository.ArticleImageRepository;
import ohchangmin.sns.repository.ArticleRepository;
import ohchangmin.sns.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleService {

    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final ArticleImageRepository articleImageRepository;

    @Transactional
    public void uploadArticle(Long userId, ArticleCreateRequest request) {
        User user = userRepository.findByIdOrThrow(userId);
        Article article = request.toEntity();
        user.uploadArticle(article);
    }

    @Transactional
    public void addArticleImages(Long userId, Long articleId, List<String> imageUrls) {
        Article article = articleRepository.findByIdWithUser(articleId)
                .orElseThrow(NotFoundArticle::new);
        article.verifyUser(userId);

        List<ArticleImage> articleImages = imageUrls.stream()
                .map(ArticleImage::new)
                .toList();

        setThumbnail(articleImages);
        article.addImages(articleImages);
    }


    /**
     * 구현 방식
     * 유저아이디를 통해 List<Article>를 조회해 온다. (ArticleImage함께 가져오지 않는다.)
     *
     *
     *
     */
    public List<ArticleResponse> findArticles(Long userId) {
        List<Article> articles = articleRepository.findArticles(userId);
        List<ArticleImage> articleImages = articleImageRepository.findInArticle(articles);

        Map<Long, ArticleResponse> responseMap = createArticleResponseMap(articles);
        setThumbnails(responseMap, articleImages);
        return new ArrayList<>(responseMap.values());
    }

    private void setThumbnail(List<ArticleImage> articleImages) {
        if (!articleImages.isEmpty()) {
            ArticleImage articleImage = articleImages.get(0);
            articleImage.setThumbnail(true);
        }
    }

    private Map<Long, ArticleResponse> createArticleResponseMap(List<Article> articles) {
        return articles.stream()
                .collect(Collectors.toMap(Article::getId, ArticleResponse::new));
    }

    private void setThumbnails(Map<Long, ArticleResponse> responseMap, List<ArticleImage> articleImages) {
        articleImages.forEach(articleImage -> {
            ArticleResponse response = responseMap.get(articleImage.getArticleId());
            if (response != null) {
                response.setThumbnail(articleImage.getImageUrl());
            }
        });
    }
}
