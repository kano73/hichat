package com.hichat.mychat.repository;

import com.hichat.mychat.model.entitie.EmailValidation;
import com.hichat.mychat.model.entitie.MyUser;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailValidationRepository extends JpaRepository<EmailValidation, Integer> {

    <S extends EmailValidation> @NotNull S save(@NotNull S entity);

    boolean existsByLink(String link);

    Optional<EmailValidation> findByUser(MyUser user);

    Optional<EmailValidation> findByLink(String link);

    void deleteByLink(String link);
}
