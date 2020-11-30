package kr.ac.univ.publication.dto.enums;

public enum PublicationSearchType {
    SHOW_ALL("Show All"),
    INTERNATIONAL_JOURNAL("International_Journal"),
    // Conference는 Poster, Regular, Demo, Workshop, Work in Process로 구분된다.
    INTERNATIONAL_CONFERENCE("International_Conference"),
    INTERNATIONAL_POSTER("International_Poster"),
    INTERNATIONAL_REGULAR("International_Regular"),
    INTERNATIONAL_DEMO("International_Demo"),
    INTERNATIONAL_WORKSHOP("International_Workshop"),
    INTERNATIONAL_WORKINPROCESS("International_Work in Process"),

    DOMESTIC_JOURNAL("Domestic_Journal"),
    // Conference는 Poster, Regular, Demo, Workshop, Work in Process로 구분된다.
    DOMESTIC_CONFERENCE("Domestic_Conference"),
    DOMESTIC_POSTER("Domestic_Poster"),
    DOMESTIC_REGULAR("Domestic_Regular"),
    DOMESTIC_DEMO("Domestic_Demo"),
    DOMESTIC_WORKSHOP("Domestic_Workshop"),
    DOMESTIC_WORKINPROCESS("Domestic_Work in Process"),
    ;

    private String publicationSearchType;

    private PublicationSearchType(String publicationSearchType) {
        this.publicationSearchType = publicationSearchType;
    }

    public String getSearchPublicationType() {
        return this.publicationSearchType;
    }
}