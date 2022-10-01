package com.jpmarket.domain.verificationToken;

import com.jpmarket.domain.user.Role;
import com.jpmarket.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    @Query("SELECT u FROM VerificationToken u WHERE u.user.email = ?1")
    Optional<VerificationToken> findByEmail(String email);

    @Query("SELECT u FROM VerificationToken u WHERE u.token = ?1")
    Optional<VerificationToken> findByToken(String token);

    @Modifying
    @Query("DELETE FROM VerificationToken u WHERE u.user = ?1")
    void deleteByUser(User user);


}