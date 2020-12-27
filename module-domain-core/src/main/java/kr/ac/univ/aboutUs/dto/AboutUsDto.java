package kr.ac.univ.aboutUs.dto;

import kr.ac.univ.common.dto.CommonDto;
import kr.ac.univ.common.validation.Editor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AboutUsDto extends CommonDto {
    /* CommonDto: JPA Audit */

    /* 기본 정보 */
    @NotBlank(message = "The title must not be blank.")
    @Size(max = 255, message = "The title can be used for less than 50 characters.")
    private String title;

    @Editor(max = 16777215, message = "The editor's input size of bytes is exceeded.")
    private String content;
}