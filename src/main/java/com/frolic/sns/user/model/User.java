package com.frolic.sns.user.model;

import com.frolic.sns.global.util.message.CommonMessageUtils;
import com.frolic.sns.global.util.models.BaseTimeAuditing.CreateAndModifiedTimeAuditEntity;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.util.Assert;

import javax.persistence.*;

@Entity(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE frolic_sns.users SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class User extends CreateAndModifiedTimeAuditEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String email;

  @Column(
    length = 15,
    nullable = false
  )
  private String username;

  @Column(
    length = 30,
    nullable = false
  )
  private String realname;

  @Column(
    length = 13,
    nullable = false
  )
  private String phoneNumber;

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
    String realname,
    String phoneNumber,
    String password
  ) {
    Assert.hasText(email, CommonMessageUtils.getIllegalFieldError("email"));
    Assert.hasText(username, CommonMessageUtils.getIllegalFieldError("username"));
    Assert.hasText(password, CommonMessageUtils.getIllegalFieldError("password"));
    Assert.hasText(realname, CommonMessageUtils.getIllegalFieldError("realname"));
    Assert.hasText(phoneNumber, CommonMessageUtils.getIllegalFieldError("phoneNumber"));

    this.email = email;
    this.username = username;
    this.realname = realname;
    this.phoneNumber = phoneNumber;
    this.password = password;
  }

  public void changePassword(String newPassword) {
    this.password = newPassword;
  }
  public void changeUsername(String username) { this.username = username; }
  public void changeEmail(String email) { this.email = email; }
  public void changeRealname(String realname) { this.realname = realname; }

  public void changePhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

  public void updateRefreshToken(String token) {
    this.refreshToken = token;
  }

}
