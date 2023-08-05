package ohchangmin.sns.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ohchangmin.sns.domain.Article;

@Getter
@NoArgsConstructor
public class ArticleCreateRequest {

    private String title;

    private String content;

    @Builder
    private ArticleCreateRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Article toEntity() {
        return Article.builder()
                .title(title)
                .content(content)
                .build();
    }
}
