package kr.ac.univ.maintenance.dto;

import kr.ac.univ.common.dto.CommonDto;
import kr.ac.univ.common.validation.Editor;
import kr.ac.univ.maintenance.domain.enums.ReceiverType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MaintenanceCommentDto extends CommonDto {
    private Long maintenanceIdx;

    @Editor(max = 16777215, message = "The editor's input size must be less than 16777215 bytes(16MB).")
    private String content;

    /* email receiver type */
    private ReceiverType receiverType;

    /* newIcon */
    private boolean isNewIcon;

    /* 접근 여부 */
    private boolean isAccess;
}