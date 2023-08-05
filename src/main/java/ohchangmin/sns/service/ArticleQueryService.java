package ohchangmin.sns.service;

import lombok.RequiredArgsConstructor;
import ohchangmin.sns.domain.*;
import ohchangmin.sns.response.ArticleElementResponse;
import ohchangmin.sns.response.ArticleResponse;
import ohchangmin.sns.exception.NotFoundArticle;
import ohchangmin.sns.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleQueryService {

    private final ArticleRepository articleRepository;
    private final ArticleImageRepository articleImageRepository;
    private final UserFollowRepository userFollowRepository;

    public List<ArticleElementResponse> findArticles(Long userId) {
        List<Article> articles = articleRepository.findByUserId(userId);
        List<ArticleImage> articleImages = articleImageRepository.findInArticle(articles);
        return createResponse(articles, articleImages);
    }

    public ArticleResponse findArticle(Long articleId) {
        Article article = articleRepository.findByIdWithUser(articleId)
                .orElseThrow(NotFoundArticle::new);
        return new ArticleResponse(article);
    }

    public List<ArticleElementResponse> findFollowArticles(Long userId) {
        List<UserFollow> userFollows = userFollowRepository.findByFollowingIdWithFollower(userId);
        List<User> followers = getFollowers(userFollows);
        List<Article> articles = articleRepository.findByUserIn(followers);
        List<ArticleImage> articleImages = articleImageRepository.findInArticle(articles);
        return createResponse(articles, articleImages);
    }

    private List<ArticleElementResponse> createResponse(List<Article> articles, List<ArticleImage> articleImages) {
        List<ArticleElementResponse> responses = createArticleElementResponse(articles);
        Map<Long, String> imageUrlMap = createImageUrlMap(articleImages);
        setRepresentativeImages(responses, imageUrlMap);
        return responses;
    }

    private List<ArticleElementResponse> createArticleElementResponse(List<Article> articles) {
        return articles.stream()
                .map(ArticleElementResponse::new)
                .toList();
    }

    private Map<Long, String> createImageUrlMap(List<ArticleImage> articleImages) {
        return articleImages.stream()
                .collect(Collectors.toMap(ArticleImage::getArticleId, ArticleImage::getImageUrl));
    }

    private void setRepresentativeImages(List<ArticleElementResponse> articles, Map<Long, String> imageUrlMap) {
        articles.forEach(article -> {
            article.setThumbnail(imageUrlMap.get(article.getId()));
        });
    }

    private List<User> getFollowers(List<UserFollow> userFollows) {
        return userFollows.stream()
                .map(UserFollow::getFollower)
                .toList();
    }
}
