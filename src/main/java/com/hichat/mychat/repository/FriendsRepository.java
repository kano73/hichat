package com.hichat.mychat.repository;

import com.hichat.mychat.model.entitie.Friends;
import com.hichat.mychat.model.entitie.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendsRepository extends JpaRepository<Friends,Integer> {
    <S extends Friends> S save(S entity);

    List<Friends> findByFriendOrUser(MyUser user, MyUser user2);

    @Modifying
    @Query("DELETE FROM Friends f WHERE (f.user = :user AND f.friend = :friend) OR (f.user = :friend AND f.friend = :user)")
    int deleteByUserAndFriend(@Param("user") MyUser user, @Param("friend") MyUser friend);
}
