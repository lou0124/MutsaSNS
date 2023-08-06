package ohchangmin.sns.controller;

import lombok.RequiredArgsConstructor;
import ohchangmin.sns.service.FollowService;
import ohchangmin.sns.service.UserPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/users/{userId}/follow")
    public void follow(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable Long userId) {
        followService.follow(userPrincipal.getId(), userId);
    }

    @PostMapping("/users/{userId}/unfollow")
    public void unfollow(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable Long userId) {
        followService.unfollow(userPrincipal.getId(), userId);
    }
}
