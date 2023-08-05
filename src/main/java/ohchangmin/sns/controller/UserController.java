package ohchangmin.sns.controller;

import lombok.RequiredArgsConstructor;
import ohchangmin.sns.dto.UserResponse;
import ohchangmin.sns.file.FileStore;
import ohchangmin.sns.service.UserPrincipal;
import ohchangmin.sns.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
}
