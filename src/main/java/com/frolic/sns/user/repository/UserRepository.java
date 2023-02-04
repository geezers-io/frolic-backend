package com.frolic.sns.user.repository;

import com.frolic.sns.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail(String email);

  Optional<User> findByUsername(String username);

  boolean existsByEmail(String email);
  boolean existsByUsername(String username);

  @Query("select u.refreshToken from users u where u.email = :email")
  String getUserRefreshToken(@Param("email") String email);

  @Query("select u.email from users u where u.phoneNumber = :phoneNumber")
  Optional<String> getEmailByPhoneNumber(@Param("phoneNumber") String phoneNumber);

  @Query("select u.phoneNumber from users u where u.email = ?1")
  Optional<String> getPhoneNumberByEmail(@Param("email") String email);

  @Query("select u.email from users u where u.email = ?1 and u.phoneNumber = ?2")
  Optional<String> getUserInfoPwExist(@Param("email") String email, @Param("phoneNumber") String phoneNumber);

  @Modifying
  @Query("update users u set u.password = ?1 where u.email = ?2 and u.phoneNumber = ?3")
  void changeTempPassword(@Param("password") String password,
                          @Param("email") String email,
                          @Param("phoneNumber") String phoneNumber);
}
