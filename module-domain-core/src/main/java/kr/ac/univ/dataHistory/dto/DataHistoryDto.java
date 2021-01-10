package kr.ac.univ.dataHistory.dto;

import kr.ac.univ.common.dto.CommonDto;
import kr.ac.univ.dataHistory.domain.enums.AudType;
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
public class DataHistoryDto extends CommonDto {
    private Long audIdx;

    @Size(max = 255, message = "The aud class must be less than 255 characters.")
    private String audClass;

    @NotNull(message = "The aud type must not be null/")
    private AudType audType;

    @Size(max = 255, message = "The aud message must be less than 255 characters.")
    private String audMessage;

    @Size(max = 255, message = "The aud sub message must be less than 255 characters.")
    private String audSubMessage;
}