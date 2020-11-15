package kr.ac.univ;

import kr.ac.univ.noticeBoard.domain.NoticeBoard;
import kr.ac.univ.noticeBoard.dto.NoticeBoardDto;
import kr.ac.univ.noticeBoard.dto.mapper.NoticeBoardMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@SpringBootTest
@EnableAutoConfiguration
@ExtendWith(SpringExtension.class)
@Slf4j
public class MapStructTest {

    @Test
    @DisplayName("Mapstrcut 테스트")
    public void Test() {
        NoticeBoardDto noticeBoardDto = new NoticeBoardDto();
        noticeBoardDto.setTitle("제목입니다");
        noticeBoardDto.setContent("글입니다");

        NoticeBoard noticeBoard = NoticeBoardMapper.INSTANCE.toEntity(noticeBoardDto); // DTO -> Entity

        Assert.assertEquals(noticeBoardDto.getTitle(), noticeBoard.getTitle());
        Assert.assertEquals(noticeBoardDto.getContent(), noticeBoard.getContent());
    }
}