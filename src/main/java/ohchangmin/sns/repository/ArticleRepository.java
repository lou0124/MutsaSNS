package ohchangmin.sns.repository;

import ohchangmin.sns.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query("select a from Article a join fetch a.user u where a.id = :articleId")
    Optional<Article> findByIdWithUser(@Param("articleId") Long articleId);

    @Query("select a from Article a join fetch a.user u where a.user.id =:userId")
    List<Article> findByUserId(@Param("userId") Long userId);
}
