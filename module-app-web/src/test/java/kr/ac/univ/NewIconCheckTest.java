package kr.ac.univ;

import kr.ac.univ.noticeBoard.repository.NoticeBoardRepository;
import kr.ac.univ.noticeBoard.repository.NoticeBoardRepositoryImpl;
import kr.ac.univ.util.NewIconCheck;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

@Slf4j
@SpringBootTest
@EnableAutoConfiguration
@ExtendWith(SpringExtension.class)
public class NewIconCheckTest {
    @Autowired
    NoticeBoardRepository noticeBoardRepository;

    @Autowired
    NoticeBoardRepositoryImpl noticeBoardRepositoryImpl;

    @Test
    @DisplayName("Time 테스트")
    public void Test() {
        long days = 2;

        // 비교하고 싶은 날짜와 시간을 입력한다.
        LocalDateTime pastDateTime = LocalDateTime.of(2020, 7, 30, 0, 0, 0, 0);

        // 현재 날짜와 비교하고 싶은 날짜의 차이가 2인 경우 Test가 통과된다.
        Assert.assertEquals(NewIconCheck.isNew(pastDateTime),true);
    }
}