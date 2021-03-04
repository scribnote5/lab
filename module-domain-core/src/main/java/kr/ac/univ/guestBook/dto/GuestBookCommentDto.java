package kr.ac.univ.guestBook.dto;

import kr.ac.univ.common.dto.CommonDto;
import kr.ac.univ.common.validation.Editor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class GuestBookCommentDto extends CommonDto {
    private Long guestBookIdx;

    @Editor(max = 16777215, message = "The editor's input size must be less than 16777215 bytes(16MB).")
    private String content;

    /* newIcon */
    private boolean isNewIcon;

    /* 접근 여부 */
    private boolean isAccess;
}