package kr.ac.univ.album.dto;

import kr.ac.univ.album.domain.AlbumAttachedFile;
import kr.ac.univ.common.dto.CommonDto;
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
public class AlbumDto extends CommonDto {
    @NotBlank(message = "The title must not be blank.")
    @Size(max = 255, message = "The title must be less than 255 characters.")
    private String title;

    private LocalDate photoTakenDate;

    @Max(value = 30, message = "The main page priority must be less than 30.")
    @Min(value = -1, message = "The main page priority must be more than -1.")
    private Long mainPagePriority;

    @Size(max = 255, message = "The hash tags must be less than 255 characters.")
    private String hashTags;

    @Size(max = 255, message = "The main hash tag must be less than 255 characters.")
    private String mainHashTag;

    private boolean isMainHashTagPrint;

    /* Attached File */
    private List<AlbumAttachedFile> attachedFileList = new ArrayList<AlbumAttachedFile>();
}