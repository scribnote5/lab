package kr.ac.univ.common.dto;

import kr.ac.univ.common.domain.enums.ActiveStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CommonDto {
    private Long idx;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    @NotBlank(message = "The createdBy must not be blank.\nIf the message is alerted although you are logged in, please contact the admin.")
    private String createdBy;
    private String lastModifiedBy;
    private ActiveStatus activeStatus;
    private boolean isAccess;
    private boolean isNewIcon;
}