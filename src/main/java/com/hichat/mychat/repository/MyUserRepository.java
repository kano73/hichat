package com.hichat.mychat.repository;

import java.util.List;
import java.util.Optional;

import com.hichat.mychat.model.entitie.MyUser;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MyUserRepository extends CrudRepository<MyUser, Integer> {
    Boolean existsByEmail(String email);

    Boolean existsByUsername(String username);

    <S extends MyUser> S save(S entity);

    Optional<MyUser> findByUsernameIgnoreCase(String username);

    List<MyUser> findByPublicNameStartingWithIgnoreCaseAndAgeBetween(String publicName, Integer ageAfter, Integer ageBefore);

    Optional<MyUser> findByEmail(String email);

    @Modifying
    @Query("UPDATE MyUser u SET u.usersPhotoUrl = :usersPhotoUrl WHERE u.id = :id")
    void uploadUsersPhotoUrlById(@Param("id") Integer id, @Param("usersPhotoUrl") String usersPhotoUrl);

    @Transactional
    @Modifying
    @Query("UPDATE MyUser u SET u.isOnline = :status WHERE u.id = :id")
    void updateOnlineStatusById(Integer id, boolean status);
}