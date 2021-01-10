package kr.ac.univ.common.dto;

import kr.ac.univ.common.domain.enums.ActiveStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CommonDto {
    private Long idx;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

    private String createdBy;

    private String lastModifiedBy;

    @NotNull(message = "The active status must not be null.\nIf the message is alerted although you are logged in, please contact the admin.")
    private ActiveStatus activeStatus;

    private Long views = 0L;

    private boolean isAccess;

    private boolean isNewIcon;
}