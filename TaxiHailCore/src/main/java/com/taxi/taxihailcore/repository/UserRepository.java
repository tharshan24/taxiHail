package com.taxi.taxihailcore.repository;

import com.taxi.taxihailcore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Modifying
    @Query("UPDATE User u SET u.status = 0 WHERE u.userId = :userId")
    void softDelete(@Param("userId") UUID userId);

    Optional<User> findByUserName(String username);
    Optional<User> findByUserNameAndStatus(String username, int status);

    Optional<User> findByUserId(UUID userId);
    Optional<User> findByUserIdAndStatus(UUID userId, int status);

}
