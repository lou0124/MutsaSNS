package ohchangmin.sns.repository;

import ohchangmin.sns.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c join fetch c.user u where c.id =:commentId")
    Optional<Comment> findByIdWithUser(@Param("commentId") Long commentId);

    @Modifying
    @Query("update Comment c set c.deleted = true where c.article.id = :articleId")
    void updateDeletedByArticleId(@Param("articleId") Long articleId);
}
