package kr.ac.univ.dataHistory.dto;

import kr.ac.univ.common.dto.CommonDto;
import kr.ac.univ.dataHistory.domain.enums.AudType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class DataHistoryDto extends CommonDto {
    /* CommonDto: JPA Audit */

    /* 기본 정보 */
    private Long audIdx;

    private String audClass;

    private AudType audType;

    private String audMessage;

    private String audSubMessage;
}