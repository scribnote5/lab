package kr.ac.univ.album.dto;

import kr.ac.univ.album.domain.AlbumAttachedFile;
import kr.ac.univ.common.dto.CommonDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
    /* CommonDto: JPA Audit */

    /* 기본 정보 */
    @NotBlank(message = "The title must not be blank.")
    @Size(max = 255, message = "The title can be used for less than 50 characters.")
    private String title;

    private LocalDate photoTakenDate;

    private Long mainPagePriority;

    @Size(max = 255, message = "The hashTags can be used for less than 255 characters.")
    private String hashTags;

    private String mainHashTag;

    private boolean isMainHashTagPrint;

    /* 첨부 파일 */
    private List<AlbumAttachedFile> attachedFileList = new ArrayList<AlbumAttachedFile>();
}