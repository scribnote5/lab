package kr.ac.univ.publication.dto;

import kr.ac.univ.common.dto.SearchDto;
import kr.ac.univ.publication.dto.enums.PublicationSearchType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PublicationSearchDto extends SearchDto {
    /* 검색 정보 */
    private PublicationSearchType publicationSearchType = PublicationSearchType.SHOW_ALL;
}
