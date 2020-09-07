package kr.ac.univ.publication.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import kr.ac.univ.common.dto.CommonDto;
import kr.ac.univ.publication.domain.PublicationAttachedFile;
import kr.ac.univ.publication.domain.enums.PublicationType;
import kr.ac.univ.publication.domain.enums.PublishingArea;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PublicationDto extends CommonDto {
    /* CommonDto: JPA Audit */

    /* 기본 정보 */
    @NotBlank(message = "The title must not be blank.")
    private String title;
    private String authors;
    private PublicationType publicationType;
    private PublishingArea publishingArea;
    private String publishedIn;
    private String impactFactor;
    private LocalDate publishedDate;
    private String pages;
    private String volume;
    private String number;
    private String doi;
    private String uri;
    private String isbnIssn;
    private String remark;

    /* 첨부 파일 */
    private List<PublicationAttachedFile> attachedFileList = new ArrayList<PublicationAttachedFile>();
}