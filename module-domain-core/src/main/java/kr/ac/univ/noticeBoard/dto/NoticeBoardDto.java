package kr.ac.univ.noticeBoard.dto;

import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.common.dto.CommonDto;
import kr.ac.univ.noticeBoard.domain.NoticeBoardAttachedFile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class NoticeBoardDto extends CommonDto {
    /* CommonDto: JPA Audit */

    /* 기본 정보 */
    private String title;
    private String content;
    private Long viewCount;

    /* newIcon */
    private boolean isNewIcon;

    /* 첨부 파일 */
    private List<NoticeBoardAttachedFile> attachedFileList = new ArrayList<NoticeBoardAttachedFile>();
}