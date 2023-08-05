package ohchangmin.sns.repository;

import ohchangmin.sns.domain.Article;
import ohchangmin.sns.domain.ArticleImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleImageRepository extends JpaRepository<ArticleImage, Long> {

    @Query("select ai from ArticleImage ai join fetch ai.article where ai.article in :articles and ai.id = " +
        "(select min(ai2.id) from ArticleImage ai2 where ai2.article = ai.article)")
    List<ArticleImage> findInArticle(@Param("articles") List<Article> articles);

    @Query("select ai from ArticleImage ai where ai.article.id = :articleId")
    List<ArticleImage> findByArticleId(@Param("articleId") Long articleId);

    @Query("select ai from ArticleImage ai join fetch ai.article a join fetch a.user u where ai.id = :articleImageId")
    Optional<ArticleImage> findByIdWithUser(@Param("articleImageId") Long articleImageId);
}
