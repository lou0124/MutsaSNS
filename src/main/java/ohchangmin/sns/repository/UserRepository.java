package ohchangmin.sns.repository;

import ohchangmin.sns.domain.User;
import ohchangmin.sns.exception.NotFoundUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);

    default User findByIdOrThrow(Long id) {
        return findById(id).orElseThrow(NotFoundUser::new);
    }
}
