package ohchangmin.sns.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ohchangmin.sns.dto.CommentCreateRequest;
import ohchangmin.sns.dto.CommentUpdateRequest;
import ohchangmin.sns.service.CommentService;
import ohchangmin.sns.service.UserPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/articles/{articleId}/comments")
    public void addComment(@AuthenticationPrincipal UserPrincipal userPrincipal,
                           @PathVariable Long articleId,
                           @RequestBody @Valid CommentCreateRequest request) {
        commentService.writeComment(userPrincipal.getId(), articleId, request.getContent());
    }

    @PostMapping("/comments/{commentId}")
    public void modifyComment(@AuthenticationPrincipal UserPrincipal userPrincipal,
                              @PathVariable Long commentId,
                              @RequestBody @Valid CommentUpdateRequest request) {
        commentService.modifyComment(userPrincipal.getId(), commentId, request.getContent());
    }
}
