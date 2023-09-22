package ohchangmin.sns.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ohchangmin.sns.controller.request.ArticleCreateRequest;
import ohchangmin.sns.file.FileStore;
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
    private final FileStore fileStore;

    @PostMapping("/articles")
    public void createArticle(@AuthenticationPrincipal UserPrincipal userPrincipal,
                              @RequestBody @Valid ArticleCreateRequest request) {
        articleService.uploadArticle(userPrincipal.getId(), request.getTitle(), request.getContent());
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
}
