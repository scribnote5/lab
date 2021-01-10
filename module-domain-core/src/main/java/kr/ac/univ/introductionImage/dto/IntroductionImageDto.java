package kr.ac.univ.introductionImage.dto;

import kr.ac.univ.common.dto.CommonDto;
import kr.ac.univ.introductionImage.domain.IntroductionImageAttachedFile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
    @NotBlank(message = "The title must not be blank.")
    @Size(max = 255, message = "The title must be less than 255 characters.")
    private String title;

    @Max(value = 30, message = "The main page priority must be less than 30.")
    @Min(value = -1, message = "The main page priority must be more than -1.")
    private Long mainPagePriority;

    /* Attached File */
    private List<IntroductionImageAttachedFile> attachedFileList = new ArrayList<IntroductionImageAttachedFile>();
}