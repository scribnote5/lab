package kr.ac.univ.alumniAssociation.dto;

import kr.ac.univ.common.dto.CommonDto;
import kr.ac.univ.common.validation.Editor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AlumniAssociationDto extends CommonDto {
    /* CommonDto: JPA Audit */
    @NotBlank(message = "The title must not be blank.")
    private String title;

    @Editor(max = 16777215, message = "The editor's input size of bytes is exceeded.")
    private String content;
}