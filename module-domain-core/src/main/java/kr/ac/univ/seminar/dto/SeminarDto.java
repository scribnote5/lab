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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class SeminarDto extends CommonDto {
    @NotBlank(message = "The title must not be blank.")
    @Size(max = 255, message = "The title must be less than 255 characters.")
    private String title;

    @NotNull(message = "The seminar type must not be null.")
    private SeminarType seminarType;

    private LocalDateTime presentationDate;

    @Size(max = 255, message = "The place must be less than 255 characters.")
    private String place;

    @Size(max = 255, message = "The presenter must be less than 255 characters.")
    private String presenter;

    private Long categoryIdx;

    @Editor(max = 16777215, message = "The editor's input size must be less than 16777215 bytes(16MB).")
    private String content;

    /* Attached File */
    private List<SeminarAttachedFile> attachedFileList = new ArrayList<>();
}