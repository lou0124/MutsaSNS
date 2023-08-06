package ohchangmin.sns.repository;

import ohchangmin.sns.domain.UserFriend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserFriendRepository extends JpaRepository<UserFriend, Long> {

    @Query("select uf from UserFriend uf join fetch uf.from f where uf.to.id = :toId and uf.request = true ")
    List<UserFriend> findRequests(@Param("toId") Long toId);

    @Query("select uf from UserFriend uf join fetch uf.from f1 join fetch uf.to f2 where uf.id = :userFriendId and uf.request = true ")
    Optional<UserFriend> findByIdWithUsers(@Param("userFriendId") Long userFriendId);

    @Query("select uf from UserFriend uf join fetch uf.to t where uf.from.id = :fromId and uf.request = false ")
    List<UserFriend> findByFromIdWithTo(@Param("fromId") Long fromId);
}
