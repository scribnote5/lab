package kr.ac.univ.email.dto;

import kr.ac.univ.common.dto.CommonDto;
import kr.ac.univ.common.validation.Editor;
import kr.ac.univ.email.domain.enums.ReceiverType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class EmailDto extends CommonDto {
    /* CommonDto: JPA Audit */

    /* 기본 정보 */
    @Email(message = "The email format is not valid.")
    private String emailAddress;

    private ReceiverType receiverType;

    @Editor(max = 255, message = "The note's input size of bytes is exceeded.")
    private String note;
}