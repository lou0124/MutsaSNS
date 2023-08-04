package ohchangmin.sns.controller;

import lombok.RequiredArgsConstructor;
import ohchangmin.sns.domain.Article;
import ohchangmin.sns.dto.ArticleCreateRequest;
import ohchangmin.sns.dto.ArticleResponse;
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
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;
    private final FileStore fileStore;

    @PostMapping
    public void createArticle(@AuthenticationPrincipal UserPrincipal userPrincipal,
                              @RequestBody ArticleCreateRequest request) {
        articleService.uploadArticle(userPrincipal.getId(), request);
    }

    @PostMapping("/{articleId}/images")
    public void addImage(@AuthenticationPrincipal UserPrincipal userPrincipal,
                         @PathVariable Long articleId,
                         List<MultipartFile> images) {
        List<String> imageUrls = images.stream()
                .map(fileStore::storeFile)
                .collect(Collectors.toList());

        articleService.addArticleImages(userPrincipal.getId(), articleId, imageUrls);
    }

    @GetMapping
    public List<ArticleResponse> findArticles(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return articleService.findArticles(userPrincipal.getId());
    }
}
