package ohchangmin.sns.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ohchangmin.sns.dto.CommentCreateRequest;
import ohchangmin.sns.service.CommentService;
import ohchangmin.sns.service.UserPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/articles/{articleId}/comments")
    public void addComment(@AuthenticationPrincipal UserPrincipal userPrincipal,
                           @PathVariable Long articleId,
                           @RequestBody @Valid CommentCreateRequest request) {
        commentService.writeComment(userPrincipal.getId(), articleId, request.getContent());
    }
}
