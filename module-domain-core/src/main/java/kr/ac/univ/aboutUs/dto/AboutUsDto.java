package kr.ac.univ.aboutUs.dto;

import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.common.dto.CommonDto;
import kr.ac.univ.common.validation.Editor;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AboutUsDto extends CommonDto {
    @NotBlank(message = "The title must not be blank.")
    @Size(max = 255, message = "The title must be less than 255 characters.")
    private String title;

    @Editor(max = 16777215, message = "The editor's input size must be less than 16777215 bytes(16MB).")
    private String content;

    private LocalDate labStartDate;

    private int labMaintenanceYears;

    private Long attendingMsUserCount;

    private Long attendingPhdUserCount;

    private Long publicationCount;

    private Long projectCount;

    private String labMaintenanceYearsCountContent;

    private String userCountContent;

    private String publicationCountContent;

    private String projectCountContent;

    @Builder
    public AboutUsDto(LocalDate labStartDate, int labMaintenanceYears, Long attendingMsUserCount, Long attendingPhdUserCount, Long publicationCount, Long projectCount,
                      String labMaintenanceYearsCountContent, String userCountContent, String publicationCountContent, String projectCountContent) {
        this.labStartDate = labStartDate;
        this.labMaintenanceYears = labMaintenanceYears;
        this.attendingMsUserCount = attendingMsUserCount;
        this.attendingPhdUserCount = attendingPhdUserCount;
        this.publicationCount = publicationCount;
        this.projectCount = projectCount;
        this.labMaintenanceYearsCountContent = labMaintenanceYearsCountContent;
        this.userCountContent = userCountContent;
        this.publicationCountContent = publicationCountContent;
        this.projectCountContent = projectCountContent;
    }
}