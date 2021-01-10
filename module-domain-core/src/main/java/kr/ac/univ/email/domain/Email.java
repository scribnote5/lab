package kr.ac.univ.email.domain;

import kr.ac.univ.common.domain.CommonAudit;
import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.email.domain.enums.ReceiverType;
import kr.ac.univ.email.listener.EmailListener;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

@Getter
@NoArgsConstructor
@Entity
@Table
@ToString
@EntityListeners(EmailListener.class)
public class Email extends CommonAudit {
    private String emailAddress;

    private ReceiverType receiverType;

    private String note;

    @Builder
    public Email(Long idx, String createdBy, String lastModifiedBy, ActiveStatus activeStatus, String emailAddress, ReceiverType receiverType, String note) {
        setIdx(idx);
        setCreatedBy(createdBy);
        setLastModifiedBy(lastModifiedBy);
        setActiveStatus(activeStatus);
        this.emailAddress = emailAddress;
        this.receiverType = receiverType;
        this.note = note;
    }

    public void update(Email email) {
        setActiveStatus(email.getActiveStatus());
        this.emailAddress = email.getEmailAddress();
        this.receiverType = email.getReceiverType();
        this.note = email.getNote();
    }
}