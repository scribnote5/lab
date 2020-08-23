package kr.ac.univ;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.noticeBoard.domain.NoticeBoard;
import kr.ac.univ.noticeBoard.repository.NoticeBoardRepository;
import kr.ac.univ.noticeBoard.repository.NoticeBoardRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Slf4j
@SpringBootTest
@EnableAutoConfiguration
@ExtendWith(SpringExtension.class)
public class JpaAuditTest {
    @Autowired
    NoticeBoardRepository noticeBoardRepository;

    @Autowired
    NoticeBoardRepositoryImpl noticeBoardRepositoryImpl;

    @BeforeEach
    public void init() {
        IntStream.rangeClosed(1, 200)
                .forEach(index -> noticeBoardRepository.save(NoticeBoard.builder()
                        .title("게시글" + index)
                        .content("컨텐츠")
                        .activeStatus(ActiveStatus.ACTIVE)
                        .build()));
    }

    @Test
    @DisplayName("JPA Auditing 테스트")
    public void Test() {
        List<NoticeBoard> list = noticeBoardRepository.findAll();

        LocalDateTime pastDateTime = LocalDateTime.of(2020,4,26,0,0,0,0);

        // 새로 생성한 게시글이 과거 시간 이후에 생성되어 있는지 확인한다.
        for (NoticeBoard noticeboard : list) {
            Assert.assertEquals(noticeboard.getCreatedDate().isAfter(pastDateTime), true);
        }
    }
}