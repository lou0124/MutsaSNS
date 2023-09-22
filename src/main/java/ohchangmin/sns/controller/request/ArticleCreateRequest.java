package ohchangmin.sns.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ArticleCreateRequest {

    @NotBlank(message = "피드 제목이 입력 되어야 합니다.")
    private String title;

    @NotBlank(message = "피드 내용이 입력 되어야 합니다.")
    private String content;
}
