package kr.ac.univ.maintenance.dto;

import kr.ac.univ.common.dto.CommonDto;
import kr.ac.univ.common.validation.Editor;
import kr.ac.univ.maintenance.domain.MaintenanceAttachedFile;
import kr.ac.univ.maintenance.domain.enums.MaintenanceStatus;
import kr.ac.univ.maintenance.domain.enums.ReceiverType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MaintenanceDto extends CommonDto {
    @NotBlank(message = "The title must not be blank.")
    @Size(max = 255, message = "The title must be less than 255 characters.")
    private String title;

    @Editor(max = 16777215, message = "The editor's input size must be less than 16777215 bytes(16MB).")
    private String content;

    @NotNull(message = "The maintenance status must be not null.")
    private MaintenanceStatus maintenanceStatus;

    /* email receiver type */
    private ReceiverType receiverType;

    /* Attached File */
    private List<MaintenanceAttachedFile> attachedFileList = new ArrayList<MaintenanceAttachedFile>();
}