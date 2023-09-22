package ohchangmin.sns.controller;

import lombok.RequiredArgsConstructor;
import ohchangmin.sns.controller.response.ListResponse;
import ohchangmin.sns.service.ArticleQueryService;
import ohchangmin.sns.service.UserPrincipal;
import ohchangmin.sns.service.response.ArticleElement;
import ohchangmin.sns.service.response.ArticleResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ArticleQueryController {

    private final ArticleQueryService articleQueryService;

    @GetMapping("/users/{userId}/articles")
    public ListResponse<List<ArticleElement>> findArticles(@PathVariable Long userId) {
        List<ArticleElement> articles = articleQueryService.findArticles(userId);
        return new ListResponse<>(articles.size(), articles);
    }

    @GetMapping("/articles/{articleId}")
    public ArticleResponse findArticle(@PathVariable Long articleId) {
        return articleQueryService.findArticle(articleId);
    }

    @GetMapping("/articles/follows")
    public ListResponse<List<ArticleElement>> findFollowArticles(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        List<ArticleElement> articles = articleQueryService.findFollowersArticles(userPrincipal.getId());
        return new ListResponse<>(articles.size(), articles);
    }

    @GetMapping("/articles/friends")
    public ListResponse<List<ArticleElement>> findFriendsArticles(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        List<ArticleElement> articles = articleQueryService.findFriendsArticles(userPrincipal.getId());
        return new ListResponse<>(articles.size(), articles);
    }
}
