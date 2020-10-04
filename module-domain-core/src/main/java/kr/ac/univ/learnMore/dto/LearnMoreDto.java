package kr.ac.univ.learnMore.dto;

import kr.ac.univ.common.dto.CommonDto;
import kr.ac.univ.common.validation.Editor;
import kr.ac.univ.learnMore.domain.LearnMore;
import kr.ac.univ.learnMore.domain.LearnMoreAttachedFile;
import kr.ac.univ.learnMore.domain.enums.DownloadFileType;
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
public class LearnMoreDto extends CommonDto {
    /* CommonDto: JPA Audit */

    /* 기본 정보 */
    @NotBlank(message = "The title must not be blank.")
    private String title;
//    @NotBlank(message = "The download file type must not be blank.")
    private DownloadFileType downloadFileType;

    /* 첨부 파일 */
    private List<LearnMoreAttachedFile> attachedFileList = new ArrayList<LearnMoreAttachedFile>();
}