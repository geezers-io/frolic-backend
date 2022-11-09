package com.modular.restfulserver.user.model;

import com.modular.restfulserver.global.utils.models.BaseTimeAuditing.CreateAndModifiedTimeAuditEntity;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.util.Assert;

import javax.persistence.*;

@Entity(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE users SET deleted = true WHERE id ?")
@Where(clause = "deleted = false")
public class User extends CreateAndModifiedTimeAuditEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(
    length = 15,
    nullable = false,
    unique = true
  )
  private String username;

  @Column(nullable = false)
  private String password;

  @Column
  private String refreshToken;

  @Builder(
    setterPrefix = "add"
  )
  public User(
    String email,
    String username,
    String password
  ) {
    Assert.hasText(email, "email must not be null");
    Assert.hasText(username, "username must not be null");
    Assert.hasText(password, "password must not be null");

    this.email = email;
    this.username = username;
    this.password = password;
  }

  public void changePassword(String newPassword) {
    this.password = newPassword;
  }
  public void changeUsername(String username) { this.username = username; }
  public void changeEmail(String email) { this.email = email; }

  public void updateRefreshToken(String token) {
    this.refreshToken = token;
  }
}
