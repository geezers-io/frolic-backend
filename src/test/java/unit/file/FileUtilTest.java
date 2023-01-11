package unit.file;

import com.modular.restfulserver.global.common.file.application.CustomFile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class FileUtilTest {

  private final String fileDownloadUrl =
    "http://localhost:8080/api/download/KakaoTalk_20210130_201328352_8d5a64ed-8db9-4077-90c6-a3424eae00601669379150304.jpg";

  @Test
  @DisplayName("파일 다운로드 주소에서 파일이름을 정상적으로 가져온다.")
  public void successfulGetFilename() {
    String filename = CustomFile.parseFilenameByDownloadUrls(fileDownloadUrl);
    assertThat(filename).isEqualTo("KakaoTalk_20210130_201328352_8d5a64ed-8db9-4077-90c6-a3424eae00601669379150304.jpg");
  }

}
