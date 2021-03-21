package kr.ac.univ.project.dto;

import kr.ac.univ.common.dto.CommonDto;
import kr.ac.univ.common.validation.Editor;
import kr.ac.univ.project.domain.ProjectAttachedFile;
import kr.ac.univ.project.domain.enums.ProjectStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ProjectDto extends CommonDto {
    @NotBlank(message = "The title must not be blank.")
    @Size(max = 255, message = "The title must be less than 255 characters.")
    private String title;

    @Editor(max = 16777215, message = "The editor's input size must be less than 16777215 bytes(16MB).")
    private String content;

    @Positive(message = "The research field idx must not be blank.")
    private Long researchFieldIdx;

    @Size(max = 255, message = "The research establishment must be less than 255 characters.")
    private String researchEstablishment;

    @NotNull(message = "The project status must be not null.")
    private ProjectStatus projectStatus;

    @NotNull(message = "The start date must be not null.")
    private LocalDate startDate;

    @NotNull(message = "The end date must be not null.")
    private LocalDate endDate;

    /* Attached File */
    private List<ProjectAttachedFile> attachedFileList = new ArrayList<ProjectAttachedFile>();
}