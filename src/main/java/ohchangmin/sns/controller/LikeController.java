package ohchangmin.sns.controller;

import lombok.RequiredArgsConstructor;
import ohchangmin.sns.service.LikeService;
import ohchangmin.sns.service.UserPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/articles/{articleId}/likes")
    public void requestLike(@AuthenticationPrincipal UserPrincipal userPrincipal,
                     @PathVariable Long articleId) {
        likeService.pushLike(userPrincipal.getId(), articleId);
    }
}
