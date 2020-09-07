package kr.ac.univ.noticeBoard.dto;

import kr.ac.univ.common.dto.CommonDto;
import kr.ac.univ.common.validation.Editor;
import kr.ac.univ.noticeBoard.domain.NoticeBoardAttachedFile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class NoticeBoardDto extends CommonDto {
    /* CommonDto: JPA Audit */

    /* 기본 정보 */
    @NotBlank(message = "The title must not be blank.")
    private String title;
    @Editor(max = 16777215, message="The editor's input size of bytes is exceeded.")
    private String content;
    private Long viewCount;

    /* newIcon */
    private boolean isNewIcon;

    /* 첨부 파일 */
    private List<NoticeBoardAttachedFile> attachedFileList = new ArrayList<NoticeBoardAttachedFile>();
}