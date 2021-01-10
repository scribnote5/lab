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
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class EmailDto extends CommonDto {
    @Email(message = "The email format is not valid.")
    private String emailAddress;

    @NotNull(message = "The receiver type must not be null.")
    private ReceiverType receiverType;

    @Editor(max = 255, message = "The note's input size of bytes is less than 255 characters.")
    private String note;
}