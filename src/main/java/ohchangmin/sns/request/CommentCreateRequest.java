package ohchangmin.sns.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentCreateRequest {

    @NotBlank(message = "댓글 내용이 입력 되어야 합니다.")
    private String content;

    public CommentCreateRequest(String content) {
        this.content = content;
    }
}
