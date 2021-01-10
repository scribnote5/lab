package kr.ac.univ.loginHistory.dto;

import kr.ac.univ.common.dto.CommonDto;
import kr.ac.univ.loginHistory.domain.enums.AudLoginResultType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class LoginHistoryDto extends CommonDto {
    private Long audIdx;

    @Size(max = 255, message = "The aud ip must be less than 255 characters.")
    private String audIp;

    @Size(max = 255, message = "The aud location must be less than 255 characters.")
    private String audLocation;

    @Size(max = 255, message = "The aud message must be less than 255 characters.")
    private String audMessage;

    @Size(max = 255, message = "The aud sub message must be less than 255 characters.")
    private String audSubMessage;

    @NotNull(message = "The aud login result type must be null.")
    private AudLoginResultType audLoginResultType;
}