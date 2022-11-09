package com.modular.restfulserver.user.repository;

import com.modular.restfulserver.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail(String email);

  Optional<User> findByUsername(String username);

  boolean existsByEmail(String email);
  boolean existsByUsername(String username);

  @Query("select u.refreshToken from users u where u.email = :email")
  String getUserRefreshToken(String email);
}
