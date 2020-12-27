package kr.ac.univ.seminar.dto;

import kr.ac.univ.common.dto.CommonDto;
import kr.ac.univ.common.validation.Editor;
import kr.ac.univ.seminar.domain.SeminarAttachedFile;
import kr.ac.univ.seminar.domain.enums.SeminarType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class SeminarDto extends CommonDto {
    /* CommonDto: JPA Audit */

    /* 기본 정보 */
    @NotBlank(message = "The title must not be blank.")
    @Size(max = 255, message = "The title can be used for less than 50 characters.")
    private String title;

    private SeminarType seminarType;

    private LocalDateTime presentationDate;

    private String place;

    private String presenter;

    private Long categoryIdx;

    @Editor(max = 16777215, message = "The editor's input size of bytes is exceeded.")
    private String content;

    private Long views;

    /* 첨부 파일 */
    private List<SeminarAttachedFile> attachedFileList = new ArrayList<SeminarAttachedFile>();
}