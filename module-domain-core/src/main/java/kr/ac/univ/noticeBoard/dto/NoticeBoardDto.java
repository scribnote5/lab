package kr.ac.univ.noticeBoard.dto;

import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.common.dto.CommonDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class NoticeBoardDto extends CommonDto {
    /* CommonDto: JPA Audit */

    /* 기본 정보 */
    private String title;
    private String content;
    private ActiveStatus activeStatus;
    private Long viewCount;
}