package kr.ac.univ.researchField.dto;

import kr.ac.univ.common.dto.CommonDto;
import kr.ac.univ.common.validation.Editor;
import kr.ac.univ.researchField.domain.ResearchFieldAttachedFile;
import kr.ac.univ.researchField.domain.enums.ResearchFieldStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ResearchFieldDto extends CommonDto {
    @NotBlank(message = "The title must not be blank.")
    @Size(max = 255, message = "The title must be less than 255 characters.")
    private String title;

    @Positive(message = "The category idx must not be blank.")
    private Long categoryIdx;

    @NotNull(message = "The research field status must be not null.")
    private ResearchFieldStatus researchFieldStatus;

    @Editor(max = 16777215, message = "The editor's input size must be less than 16777215 bytes(16MB).")
    private String content;

    /* Attached File */
    private List<ResearchFieldAttachedFile> attachedFileList = new ArrayList<ResearchFieldAttachedFile>();
}