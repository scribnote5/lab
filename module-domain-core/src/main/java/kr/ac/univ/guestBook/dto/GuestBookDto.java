package kr.ac.univ.guestBook.dto;

import kr.ac.univ.common.dto.CommonDto;
import kr.ac.univ.common.validation.Editor;
import kr.ac.univ.guestBook.domain.GuestBookAttachedFile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class GuestBookDto extends CommonDto {
    @NotBlank(message = "The title must not be blank.")
    @Size(max = 255, message = "The title must be less than 255 characters.")
    private String title;

    @Editor(max = 16777215, message = "The editor's input size must be less than 16777215 bytes(16MB).")
    private String content;

    @Max(value = 11, message = "The main page priority must be less than 11.")
    @Min(value = 1, message = "The main page priority must be more than 1.")
    private Long mainPagePriority;

    /* Attached File */
    private List<GuestBookAttachedFile> attachedFileList = new ArrayList<GuestBookAttachedFile>();
}