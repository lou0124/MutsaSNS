package ohchangmin.sns.repository;

import ohchangmin.sns.domain.Article;
import ohchangmin.sns.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByUserIdAndArticleId(Long userId, Long articleId);
}
