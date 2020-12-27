package kr.ac.univ.introductionImage.dto;

import kr.ac.univ.common.dto.CommonDto;
import kr.ac.univ.introductionImage.domain.IntroductionImageAttachedFile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class IntroductionImageDto extends CommonDto {
    /* CommonDto: JPA Audit */

    /* 기본 정보 */
    @NotBlank(message = "The title must not be blank.")
    @Size(max = 255, message = "The title can be used for less than 50 characters.")
    private String title;

    private Long mainPagePriority;

    /* 첨부 파일 */
    private List<IntroductionImageAttachedFile> attachedFileList = new ArrayList<IntroductionImageAttachedFile>();
}