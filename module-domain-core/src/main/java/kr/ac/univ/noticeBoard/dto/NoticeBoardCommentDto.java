package kr.ac.univ.noticeBoard.dto;

import kr.ac.univ.common.dto.CommonDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class NoticeBoardCommentDto extends CommonDto {
    /* CommonDto: JPA Audit */

    /* 기본 정보 */
    private Long noticeBoardIdx;

    private String content;

    /* newIcon */
    private boolean isNewIcon;

    /* 접근 여부 */
    private boolean isAccess;
}