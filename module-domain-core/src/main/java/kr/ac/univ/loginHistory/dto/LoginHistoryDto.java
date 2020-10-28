package kr.ac.univ.loginHistory.dto;

import kr.ac.univ.common.dto.CommonDto;
import kr.ac.univ.loginHistory.domain.enums.AudLoginResultType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class LoginHistoryDto extends CommonDto {
    /* CommonDto: JPA Audit */

    /* 기본 정보 */
    private Long audIdx;

    private String audIp;

    private String audLocation;

    private String audMessage;

    private String audSubMessage;

    private AudLoginResultType audLoginResultType;
}