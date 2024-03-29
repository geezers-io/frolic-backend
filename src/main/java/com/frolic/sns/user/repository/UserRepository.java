package com.frolic.sns.user.repository;

import com.frolic.sns.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail(String email);

  Optional<User> findByUsername(String username);

  boolean existsByEmail(String email);
  boolean existsByUsername(String username);

  @Query("select u.email from users u where u.phoneNumber = :phoneNumber")
  Optional<String> getEmailByPhoneNumber(@Param("phoneNumber") String phoneNumber);

  @Query("select u.email from users u where u.email = ?1 and u.phoneNumber = ?2")
  Optional<String> getUserInfoPwExist(@Param("email") String email, @Param("phoneNumber") String phoneNumber);

}
