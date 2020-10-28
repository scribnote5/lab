package kr.ac.univ.user.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import kr.ac.univ.common.dto.CommonDto;
import kr.ac.univ.common.validation.Contact;
import kr.ac.univ.common.validation.Password;
import kr.ac.univ.user.domain.UserAttachedFile;
import kr.ac.univ.user.domain.enums.AuthorityType;
import kr.ac.univ.user.domain.enums.GenderType;
import kr.ac.univ.user.domain.enums.UserStatus;
import kr.ac.univ.user.domain.enums.UserType;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserDto extends CommonDto  {
    /* CommonDto: JPA Audit */

    /* 기본 정보 */
    @Size(min = 6, max = 16, message = "The ID can be used for more than 6 characters and less than 16 characters.")
    private String username;
    @Password(min = 6, max = 16, message = "The password can be used for more than 6 characters and less than 16 characters.")
    private String password;
    @NotEmpty(message ="The korean name is empty.")
    private String koreanName;
    @NotEmpty(message ="The english name is empty.")
    private String englishName;
    private GenderType gender;
    @Past(message ="The birthDate mus be past.")
    private LocalDate birthDate;
    @Email(message = "The email format is not valid.")
    private String email;
    @Email(message = "The email format is not valid.")
    private String privateEmail;
    @NotEmpty(message ="The messenger id is empty.")
    private String messengerId;
    @Contact(message = "The contact format is not valid.")
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
    private AuthorityType authorityType;

    /* 첨부 파일 */
    private List<UserAttachedFile> attachedFileList = new ArrayList<UserAttachedFile>();
}