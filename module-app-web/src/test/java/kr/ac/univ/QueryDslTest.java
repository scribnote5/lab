package kr.ac.univ;

import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.noticeBoard.domain.NoticeBoard;
import kr.ac.univ.noticeBoard.repository.NoticeBoardRepository;
import kr.ac.univ.noticeBoard.repository.NoticeBoardRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@SpringBootTest
@EnableAutoConfiguration
@ExtendWith(SpringExtension.class)
public class QueryDslTest {
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
    @DisplayName("QueryDsl로 구현한 findByTitle 테스트")
    public void Test() {
        List<NoticeBoard> list = noticeBoardRepositoryImpl.findByTitle("게시글10");

        for (NoticeBoard noticeboard : list) {
            assertEquals(noticeboard.getTitle(), "게시글10");
        }
    }

    @Test
    @DisplayName("QueryDsl로 구현한 updateViewsByIdx 테스트")
    public void Test2() {
        // update된 column 개수 반환
        Long cnt = noticeBoardRepositoryImpl.updateViewsByIdx(1L);
        assertEquals(cnt, 1);
    }

    @Test
    @DisplayName("JPA findById를 사용한 views 테스트")
    public void Test3() {
        Optional<NoticeBoard> optionalNoticeBoard = noticeBoardRepository.findById(1L);
        NoticeBoard noticeBoard = null;

        if (optionalNoticeBoard.isPresent()) {
            noticeBoard = optionalNoticeBoard.get();
        }

        // idx 1인 column을 조회한다.
        assertEquals(noticeBoard.getIdx(), 1L);
        assertEquals(noticeBoard.getViews(), 1L);
    }
}