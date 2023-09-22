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
}
