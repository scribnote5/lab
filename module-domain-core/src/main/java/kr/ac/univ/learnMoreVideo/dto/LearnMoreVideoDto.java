package kr.ac.univ.learnMoreVideo.dto;

import kr.ac.univ.common.dto.CommonDto;
import kr.ac.univ.learnMoreVideo.domain.LearnMoreVideoAttachedFile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class LearnMoreVideoDto extends CommonDto {
    /* CommonDto: JPA Audit */

    /* 기본 정보 */
    @NotBlank(message = "The title must not be blank.")
    @Size(max = 255, message = "The title can be used for less than 50 characters.")
    private String title;

    /* 첨부 파일 */
    private List<LearnMoreVideoAttachedFile> attachedFileList = new ArrayList<LearnMoreVideoAttachedFile>();
}