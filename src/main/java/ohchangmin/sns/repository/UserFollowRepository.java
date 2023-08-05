package ohchangmin.sns.repository;

import ohchangmin.sns.domain.UserFollow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserFollowRepository extends JpaRepository<UserFollow, Long> {

    Optional<UserFollow> findByFollowingIdAndFollowerId(Long followingId, Long followerId);
}
