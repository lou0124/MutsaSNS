package ohchangmin.sns.repository;

import ohchangmin.sns.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c join fetch c.user u where c.article.id =:articleId")
    List<Comment> findByArticleIdWithUser(@Param("articleId") Long articleId);
}
