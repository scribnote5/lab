package kr.ac.univ.aboutUs.dto;

import kr.ac.univ.common.dto.CommonDto;
import kr.ac.univ.common.validation.Editor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AboutUsDto extends CommonDto {
    @NotBlank(message = "The title must not be blank.")
    @Size(max = 255, message = "The title must be less than 255 characters.")
    private String title;

    @Editor(max = 16777215, message = "The editor's input size must be less than 16777215 bytes(16MB).")
    private String content;
}