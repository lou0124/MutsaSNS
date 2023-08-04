package ohchangmin.sns.repository;

import ohchangmin.sns.domain.Article;
import ohchangmin.sns.exception.NotFoundArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    default Article findByIdOrThrow(Long id) {
        return findById(id).orElseThrow(NotFoundArticle::new);
    }
}
