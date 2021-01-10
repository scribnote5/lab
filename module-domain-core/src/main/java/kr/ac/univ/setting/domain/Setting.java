package kr.ac.univ.setting.domain;

import kr.ac.univ.common.domain.CommonAudit;
import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.setting.listener.SettingListener;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
@Table
@ToString
@EntityListeners(SettingListener.class)
public class Setting extends CommonAudit {
    private String labAddress;

    private String emailRecipient;

    private String emailAddress;

    private String callee;

    private String phoneNumber;

    private LocalDate labStartDate;

    private String labMaintenanceYearsCountContent;

    private String userCountContent;

    private String publicationCountContent;

    private String projectCountContent;

    @Builder
    public Setting(Long idx, String createdBy, String lastModifiedBy, ActiveStatus activeStatus,
                   String labAddress, String emailRecipient, String emailAddress, String callee, String phoneNumber, LocalDate labStartDate,
                   String labMaintenanceYearsCountContent, String userCountContent, String publicationCountContent, String projectCountContent) {
        setIdx(idx);
        setCreatedBy(createdBy);
        setLastModifiedBy(lastModifiedBy);
        setActiveStatus(activeStatus);
        this.labAddress = labAddress;
        this.emailRecipient = emailRecipient;
        this.emailAddress = emailAddress;
        this.callee = callee;
        this.phoneNumber = phoneNumber;
        this.labStartDate = labStartDate;
        this.labMaintenanceYearsCountContent = labMaintenanceYearsCountContent;
        this.userCountContent = userCountContent;
        this.publicationCountContent = publicationCountContent;
        this.projectCountContent = projectCountContent;
    }

    public void update(Setting setting) {
        setActiveStatus(setting.getActiveStatus());
        this.labAddress = setting.getLabAddress();
        this.emailRecipient = setting.getEmailRecipient();
        this.emailAddress = setting.getEmailAddress();
        this.callee = setting.getCallee();
        this.phoneNumber = setting.getPhoneNumber();
        this.labStartDate = setting.getLabStartDate();
        this.labMaintenanceYearsCountContent = setting.getLabMaintenanceYearsCountContent();
        this.userCountContent = setting.getUserCountContent();
        this.publicationCountContent = setting.getPublicationCountContent();
        this.projectCountContent = setting.getProjectCountContent();
    }
}