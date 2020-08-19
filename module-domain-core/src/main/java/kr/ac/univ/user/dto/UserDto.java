package kr.ac.univ.user.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.common.dto.CommonDto;
import kr.ac.univ.user.domain.User;
import kr.ac.univ.user.domain.User;
import kr.ac.univ.user.domain.UserAttachedFile;
import kr.ac.univ.user.domain.enums.AuthorityType;
import kr.ac.univ.user.domain.enums.GenderType;
import kr.ac.univ.user.domain.enums.UserStatus;
import kr.ac.univ.user.domain.enums.UserType;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserDto extends CommonDto  {
    /* CommonDto: JPA Audit */

    /* 기본 정보 */
    private String username;
    private String password;
    private String koreanName;
    private String englishName;
    private GenderType gender;
    private LocalDate birthDate;
    private String email;
    private String privateEmail;
    private String messangerId;
    private String contact;
    private UserType userType;
    private UserStatus userStatus;
    private String introduction;

    /* 부가 정보 */
    private LocalDate admissionDate;
    private LocalDate graduatedDate;
    private String workplace;
    private String webPage;

    /* 관리자가 수정하는 정보 */
    private ActiveStatus activeStatus;
    private AuthorityType authorityType;

    /* 첨부 파일 */
    private List<UserAttachedFile> attachedFileList = new ArrayList<UserAttachedFile>();
}