package kr.ac.univ.user.dto;

import kr.ac.univ.common.dto.CommonDto;
import kr.ac.univ.common.validation.Editor;
import kr.ac.univ.common.validation.Password;
import kr.ac.univ.user.domain.UserAttachedFile;
import kr.ac.univ.user.domain.enums.AuthorityType;
import kr.ac.univ.user.domain.enums.GenderType;
import kr.ac.univ.user.domain.enums.UserStatus;
import kr.ac.univ.user.domain.enums.UserType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserDto extends CommonDto {
    /* Required Information */
    @Size(min = 4, max = 16, message = "The ID must be more than 4 characters and less than 16 characters.")
    private String username;

    @Password(min = 6, max = 16, message = "The password must be more than 6 characters and less than 16 characters.")
    private String password;

    @NotBlank(message = "The korean name must not be blank.")
    private String koreanName;

    @NotBlank(message = "The english name must not be blank.")
    private String englishName;

    /* Self Introduction */
    @Editor(max = 16777215, message = "The editor's input size must be less than 16777215 bytes(16MB).")
    private String introduction;

    @NotNull(message = "The user type must not be null.")
    private UserType userType;

    @NotNull(message = "The user status must not be null.")
    private UserStatus userStatus;

    /* Personal Information */
    private LocalDate admissionDate;

    private LocalDate graduatedDate;
    @NotNull(message = "The gender name must not be null.")
    private GenderType gender;

    private LocalDate birthDate;

    @Size(max = 255, message = "The workplace must be less than 255 characters.")
    private String workplace;

    /* Contact Information */
    @Size(max = 255, message = "The messenger id must be less than 255 characters.")
    private String messengerId;

    @Size(max = 255, message = "The aud type must be less than 255 characters.")
    private String contact;

    @Size(max = 255, message = "The email must be less than 255 characters.")
    private String email;

    @Size(max = 255, message = "The private email must be less than 255 characters.")
    private String privateEmail;

    @Size(max = 255, message = "The github must be less than 255 characters.")
    private String github;

    @Size(max = 255, message = "The linked in must be less than 255 characters.")
    private String linkedIn;

    @Size(max = 255, message = "The external web page must be less than 255 characters.")
    private String externalWebPage;

    /* Additional Information */
    @NotNull(message = "The authority type must be not null.")
    private AuthorityType authorityType;

    /* Attached File */
    private List<UserAttachedFile> attachedFileList = new ArrayList<>();
}