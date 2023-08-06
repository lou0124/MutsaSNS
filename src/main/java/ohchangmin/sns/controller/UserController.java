package ohchangmin.sns.controller;

import lombok.RequiredArgsConstructor;
import ohchangmin.sns.response.FriendRequestElement;
import ohchangmin.sns.response.ListResponse;
import ohchangmin.sns.response.UserResponse;
import ohchangmin.sns.file.FileStore;
import ohchangmin.sns.service.UserPrincipal;
import ohchangmin.sns.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final FileStore fileStore;
    private final UserService userService;

    @PostMapping("/change-profile")
    public void changeImage(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestPart MultipartFile image) {
        String imagePath = fileStore.storeFile(image);
        userService.changeProfile(userPrincipal.getId(), imagePath);
    }

    @GetMapping("/users/{userId}")
    public UserResponse findUser(@PathVariable Long userId) {
        return userService.findUser(userId);
    }

    @PostMapping("/users/{userId}/follow")
    public void follow(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable Long userId) {
        userService.follow(userPrincipal.getId(), userId);
    }

    @PostMapping("/users/{userId}/unfollow")
    public void unfollow(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable Long userId) {
        userService.unfollow(userPrincipal.getId(), userId);
    }

    @PostMapping("/users/{userId}/friend-requests")
    public void requestFriends(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable Long userId) {
        userService.requestFriends(userPrincipal.getId(), userId);
    }

    @GetMapping("/friend-requests")
    public ListResponse<List<FriendRequestElement>> findRequestFriends(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        List<FriendRequestElement> friendRequestElements = userService.findRequestFriends(userPrincipal.getId());
        return new ListResponse<>(friendRequestElements.size(), friendRequestElements);
    }
}
