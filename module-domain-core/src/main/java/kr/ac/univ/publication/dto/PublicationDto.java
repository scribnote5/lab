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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PublicationDto extends CommonDto {
    @NotBlank(message = "The title must not be blank.")
    @Size(max = 255, message = "The title must be less than 255 characters.")
    private String title;

    @NotBlank(message = "The authors must not be blank.")
    @Size(max = 255, message = "The authors must be less than 255 characters.")
    private String authors;

    @NotNull(message = "The publication type must not be null.")
    private PublicationType publicationType;

    @NotNull(message = "The publishing area must not be null.")
    private PublishingArea publishingArea;

    @Size(max = 255, message = "The published in must be less than 255 characters.")
    private String publishedIn;

    @Size(max = 255, message = "The impact factor must be less than 255 characters.")
    private String impactFactor;

    private LocalDate publishedDate;

    @Size(max = 255, message = "The pages must be less than 255 characters.")
    private String pages;

    @Size(max = 255, message = "The volume must be less than 255 characters.")
    private String volume;

    @Size(max = 255, message = "The number must be less than 255 characters.")
    private String number;

    @Size(max = 255, message = "The doi must be less than 255 characters.")
    private String doi;

    @Size(max = 255, message = "The uri must be less than 255 characters.")
    private String uri;

    @Size(max = 255, message = "The isbn and issn must be less than 255 characters.")
    private String isbnIssn;

    @Size(max = 255, message = "The remark must be less than 255 characters.")
    private String remark;

    /* Attached File */
    private List<PublicationAttachedFile> attachedFileList = new ArrayList<PublicationAttachedFile>();
}