package kr.ac.univ.common.dto;

import kr.ac.univ.common.domain.enums.ActiveStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
    private ActiveStatus activeStatus;
    private boolean isAccess;
}