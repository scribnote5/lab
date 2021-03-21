package kr.ac.univ.setting.dto;

import kr.ac.univ.common.dto.CommonDto;
import kr.ac.univ.seminar.domain.SeminarAttachedFile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class SettingDto extends CommonDto {
    @NotBlank(message = "The lab address must not be blank.")
    @Size(max = 255, message = "The lab address must be less than 255 characters.")
    private String labAddress;

    @NotBlank(message = "The email recipient must not be blank.")
    @Size(max = 255, message = "The title must be less than 255 characters.")
    private String emailRecipient;

    @NotBlank(message = "The email address must not be blank.")
    @Size(max = 255, message = "The title must be less than 255 characters.")
    private String emailAddress;

    @NotBlank(message = "The calle must not be blank.")
    @Size(max = 255, message = "The calle must be less than 255 characters.")
    private String callee;

    @Size(max = 255, message = "The phone number must be less than 255 characters.")
    private String phoneNumber;

    @Past(message = "The lab start date must be past.")
    private LocalDate labStartDate;

    @Size(max = 255, message = "The lab maintenance years count content must be less than 255 characters.")
    private String labMaintenanceYearsCountContent;

    @Size(max = 255, message = "The user count content must be less than 255 characters.")
    private String userCountContent;

    @Size(max = 255, message = "The publication count content must be less than 255 characters.")
    private String publicationCountContent;

    @Size(max = 255, message = "The project count content must be less than 255 characters.")
    private String projectCountContent;

    /* Attached File */
    private List<SeminarAttachedFile> attachedFileList = new ArrayList<>();
}