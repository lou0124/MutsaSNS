package ohchangmin.sns.controller;

import lombok.RequiredArgsConstructor;
import ohchangmin.sns.controller.response.ListResponse;
import ohchangmin.sns.service.FriendService;
import ohchangmin.sns.service.UserPrincipal;
import ohchangmin.sns.service.response.FriendRequestElement;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;

    @PostMapping("/users/{userId}/friend-requests")
    public void requestFriend(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable Long userId) {
        friendService.requestFriends(userPrincipal.getId(), userId);
    }

    @GetMapping("/friend-requests")
    public ListResponse<List<FriendRequestElement>> findRequestFriend(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        List<FriendRequestElement> friendRequestElements = friendService.findRequestFriends(userPrincipal.getId());
        return new ListResponse<>(friendRequestElements.size(), friendRequestElements);
    }

    @PostMapping("/friend-requests/{requestId}/accept")
    public void acceptFriend(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable Long requestId) {
        friendService.acceptFriend(userPrincipal.getId(), requestId);
    }

    @PostMapping("/friend-requests/{requestId}/reject")
    public void rejectFriend(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable Long requestId) {
        friendService.rejectFriend(userPrincipal.getId(), requestId);
    }
}
