package kr.ac.univ.univ;

import kr.ac.univ.util.EmailUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Slf4j
@SpringBootTest
@EnableAutoConfiguration
@ExtendWith(SpringExtension.class)
public class EmailTest {
    @Test
    @DisplayName("Email 테스트")
    public void Test() {
        String sender = "eslab533";
        String password = "eslabeslab533";
        String[] receivers = {"scribnote5@gmail.com"};
        String subject = "제목";
        String content = "내용";

        EmailUtil.gmailSend(sender, password, receivers, subject, content);
    }

}