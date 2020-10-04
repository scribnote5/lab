package kr.ac.univ.researchField.dto;

import kr.ac.univ.common.dto.CommonDto;
import kr.ac.univ.common.validation.Editor;
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
public class ResearchFieldDto extends CommonDto {
    /* CommonDto: JPA Audit */

    /* 기본 정보 */
    @NotBlank(message = "The title must not be blank.")
    private String title;

    @NotBlank(message = "The sub title must not be blank.")
    private String subTitle;

    @Editor(max = 16777215, message = "The editor's input size of bytes is exceeded.")
    private String content;
}