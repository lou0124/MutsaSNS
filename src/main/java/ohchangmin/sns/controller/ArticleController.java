package ohchangmin.sns.controller;

import lombok.RequiredArgsConstructor;
import ohchangmin.sns.dto.ArticleCreateRequest;
import ohchangmin.sns.dto.ArticleElementResponse;
import ohchangmin.sns.dto.ArticleResponse;
import ohchangmin.sns.file.FileStore;
import ohchangmin.sns.service.ArticleQueryService;
import ohchangmin.sns.service.ArticleService;
import ohchangmin.sns.service.UserPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
    private final ArticleQueryService articleQueryService;
    private final FileStore fileStore;

    @PostMapping("/articles")
    public void createArticle(@AuthenticationPrincipal UserPrincipal userPrincipal,
                              @RequestBody ArticleCreateRequest request) {
        articleService.uploadArticle(userPrincipal.getId(), request);
    }

    @PostMapping("/articles/{articleId}/article-images")
    public void addImage(@AuthenticationPrincipal UserPrincipal userPrincipal,
                         @PathVariable Long articleId,
                         List<MultipartFile> images) {
        List<String> imageUrls = images.stream()
                .map(fileStore::storeFile)
                .collect(Collectors.toList());

        articleService.addArticleImages(userPrincipal.getId(), articleId, imageUrls);
    }

    @GetMapping("/users/{userId}/articles")
    public Result<List<ArticleElementResponse>> findArticles(@PathVariable Long userId) {
        List<ArticleElementResponse> articles = articleQueryService.findArticles(userId);
        return new Result<>(articles.size(), articles);
    }

    @GetMapping("/articles/{articleId}")
    public ArticleResponse findArticle(@PathVariable Long articleId) {
        return articleQueryService.findArticle(articleId);
    }

    @DeleteMapping("/article-images/{articleImageId}")
    public void deleteImage(@AuthenticationPrincipal UserPrincipal userPrincipal,
                            @PathVariable Long articleImageId) {
        String imageUrl = articleService.deleteArticleImages(userPrincipal.getId(), articleImageId);
        fileStore.deleteFile(imageUrl);
    }

    @DeleteMapping("/articles/{articleId}")
    public void deleteArticle(@AuthenticationPrincipal UserPrincipal userPrincipal,
                              @PathVariable Long articleId) {
        articleService.deleteArticle(userPrincipal.getId(), articleId);
    }

    @GetMapping("/articles/follow")
    public Result<List<ArticleElementResponse>> findFollowArticles(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        List<ArticleElementResponse> articles = articleQueryService.findFollowArticles(userPrincipal.getId());
        return new Result<>(articles.size(), articles);
    }

}
