package ohchangmin.sns.repository;

import ohchangmin.sns.domain.UserFriend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserFriendRepository extends JpaRepository<UserFriend, Long> {

    @Query("select uf from UserFriend uf join fetch uf.from f where uf.to.id = :toId")
    List<UserFriend> findAllToId(@Param("toId") Long toId);
}
