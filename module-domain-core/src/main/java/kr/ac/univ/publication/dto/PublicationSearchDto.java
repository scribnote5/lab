package kr.ac.univ.publication.dto;

import kr.ac.univ.publication.dto.enums.PublicationSearchType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PublicationSearchDto {
    private String searchType = "";
    private String keyword = "";
    private PublicationSearchType publicationSearchType = PublicationSearchType.SHOW_ALL;
}
