package kr.ac.univ.publication.dto.enums;

public enum PublicationSearchType {
    SHOW_ALL("Show All"),
    INTERNATIONAL_JOURNAL("International Journal"),
    // Conference는 Poster, Regular, Demo, Workshop, Work in Process로 구분된다.
    INTERNATIONAL_CONFERENCE("International Conference"),
    INTERNATIONAL_POSTER("International Poster"),
    INTERNATIONAL_REGULAR("International Regular"),
    INTERNATIONAL_DEMO("International Demo"),
    INTERNATIONAL_WORKSHOP("International Workshop"),
    INTERNATIONAL_WORKINPROCESS("International Work in Process"),

    DOMESTIC_JOURNAL("Domestic Journal"),
    // Conference는 Poster, Regular, Demo, Workshop, Work in Process로 구분된다.
    DOMESTIC_CONFERENCE("Domestic Conference"),
    DOMESTIC_POSTER("Domestic Poster"),
    DOMESTIC_REGULAR("Domestic Regular"),
    DOMESTIC_DEMO("Domestic Demo"),
    DOMESTIC_WORKSHOP("Domestic Workshop"),
    DOMESTIC_WORKINPROCESS("Domestic Work in Process"),
    ;

    private String publicationSearchType;

    private PublicationSearchType(String publicationSearchType) {
        this.publicationSearchType = publicationSearchType;
    }

    public String getSearchPublicationType() {
        return this.publicationSearchType;
    }
}