package kr.ac.univ.user.domain;

import kr.ac.univ.common.domain.CommonAudit;
import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.user.domain.enums.AuthorityType;
import kr.ac.univ.user.domain.enums.GenderType;
import kr.ac.univ.user.domain.enums.UserStatus;
import kr.ac.univ.user.domain.enums.UserType;
import kr.ac.univ.util.EmptyUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
@Table
@ToString
public class User extends CommonAudit {
    /* 기본 정보 */
    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String koreanName;

    @Column
    private String englishName;

    @Column
    @Enumerated(EnumType.STRING)
    private GenderType gender;

    @Column
    private LocalDate birthDate;

    @Column
    private String email;

    @Column
    private String privateEmail;

    @Column
    private String messangerId;

    @Column
    private String contact;

    @Column
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Column
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @Column
    private String introduction;

    /* 부가 정보 */
    @Column
    private LocalDate admissionDate;

    @Column
    private LocalDate graduatedDate;

    @Column
    private String webPage;

    @Column
    private String workplace;

    /* 관리자가 수정하는 정보 */
    @Column
    @Enumerated(EnumType.STRING)
    private ActiveStatus activeStatus;

    @Column
    @Enumerated(EnumType.STRING)
    private AuthorityType authorityType;

    @Builder
    public User(Long idx, String createdBy, String lastModifiedBy, String username, String password, String koreanName, String englishName, GenderType gender, LocalDate birthDate, String email, String privateEmail, String messangerId, String contact, UserType userType, UserStatus userStatus, String introduction, LocalDate admissionDate, LocalDate graduatedDate, String webPage, String workplace, ActiveStatus activeStatus, AuthorityType authorityType) {
        setIdx(idx);
        setCreatedBy(createdBy);
        setLastModifiedBy(lastModifiedBy);
        this.username = username;
        this.password = password;
        this.koreanName = koreanName;
        this.englishName = englishName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.privateEmail = privateEmail;
        this.email = email;
        this.messangerId = messangerId;
        this.contact = contact;
        this.userType = userType;
        this.userStatus = userStatus;
        this.introduction = introduction;
        this.webPage = webPage;
        this.workplace = workplace;
        this.admissionDate = admissionDate;
        this.graduatedDate = graduatedDate;
        this.activeStatus = activeStatus;
        this.authorityType = authorityType;
    }

    public void update(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // 비밀번호가 입력되지 않은 경우, 비밀번호를 변경하지 않음
        if (!"".equals(user.getPassword())) {
            this.password = passwordEncoder.encode(user.getPassword());
        }

        this.username = user.getUsername();
        this.koreanName = user.getKoreanName();
        this.englishName = user.getEnglishName();
        this.gender = user.getGender();
        this.birthDate = user.getBirthDate();
        this.privateEmail = user.getPrivateEmail();
        this.email = user.getEmail();
        this.messangerId = user.getMessangerId();
        this.contact = user.getContact();
        this.userType = user.getUserType();
        this.userStatus = user.getUserStatus();
        this.introduction = user.getIntroduction();
        this.webPage = user.getWebPage();
        this.workplace = user.getWorkplace();
        this.admissionDate = user.getAdmissionDate();
        this.graduatedDate = user.getGraduatedDate();
        this.activeStatus = user.getActiveStatus();
        this.authorityType = user.getAuthorityType();
    }
}