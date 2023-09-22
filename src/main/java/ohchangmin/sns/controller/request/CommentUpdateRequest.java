package ohchangmin.sns.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentUpdateRequest {

    @NotBlank(message = "댓글 내용이 입력 되어야 합니다.")
    private String content;
}
