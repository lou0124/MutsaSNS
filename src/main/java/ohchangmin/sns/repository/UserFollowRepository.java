package ohchangmin.sns.repository;

import ohchangmin.sns.domain.UserFollow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserFollowRepository extends JpaRepository<UserFollow, Long> {

    Optional<UserFollow> findByFollowingIdAndFollowerId(Long followingId, Long followerId);

    @Query("select uf from UserFollow uf join fetch uf.follower where uf.following.id = :followingId")
    List<UserFollow> findByFollowingIdWithFollower(@Param("followingId") Long followingId);
}
