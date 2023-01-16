package unit.util;

import com.frolic.sns.user.model.User;
import com.frolic.sns.util.TestUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class TestUserTest {

  @Test
  @DisplayName("테스트용 패스워드를 정상적으로 불러온다.")
  void checkPassword() {
    assertThat(TestUser.testPassword).isInstanceOf(String.class);
  }

  @Test
  @DisplayName("유저 정보 객체를 정상적으로 불러온다.")
  void checkTestUser() {
    User user = TestUser.EUNGI.getUser();
    assertThat(user.getEmail()).isInstanceOf(String.class);
    assertThat(user.getPassword()).isInstanceOf(String.class);
    assertThat(user.getUsername()).isInstanceOf(String.class);
    assertThat(user.getRealname()).isInstanceOf(String.class);
  }

}
