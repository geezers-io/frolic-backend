package com.frolic.sns.auth.application.finder;

public interface UserInfoTransmittable {
  void sendPrincipalInfo(String principalInfo, String dest);
}
