package com.hichat.mychat.repository;

import com.hichat.mychat.model.entitie.MyUser;
import com.hichat.mychat.model.entitie.PasswordRecovery;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordRecoveryRepository extends CrudRepository<PasswordRecovery, Integer> {
    @Override
    <S extends PasswordRecovery> S save(S entity);

    boolean existsByLink(String link);

    Optional<PasswordRecovery> findByUser(MyUser user);

    Optional<PasswordRecovery> findByLink(String link);

    void deleteByLink(String link);
}
