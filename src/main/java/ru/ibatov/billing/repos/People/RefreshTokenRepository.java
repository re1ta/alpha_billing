package ru.ibatov.billing.repos.People;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.ibatov.billing.entity.People.RefreshToken;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    @Transactional
    @Modifying
    @Query("update RefreshToken a set a.token = ?1, a.expireDate = ?2 WHERE a.user_id = ?3")
    void updateToken(String token, Instant time, Long user_id);

    @Query(value = "SELECT a FROM RefreshToken a WHERE a.user_id = :ID")
    Optional<RefreshToken> findByUserId(@Param("ID") Long user_id);
}