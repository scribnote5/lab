package kr.ac.univ.publication.dto.enums;

public enum PublicationSearchType {
    SHOW_ALL("Show All"),

    // International Journal은 KCI, SCOPUS, SCIE로 구분된다.
    INTERNATIONAL_JOURNAL("International Journal"),
    INTERNATIONAL_JOURNAL_SCOPUS("International Journal - SCOPUS"),
    INTERNATIONAL_JOURNAL_SCIE("International Journal - SCIE"),

    // Conference는 Poster, Regular, Demo, Workshop으로 구분된다.
    INTERNATIONAL_CONFERENCE("International Conference"),
    INTERNATIONAL_POSTER("International Conference - Poster"),
    INTERNATIONAL_REGULAR("International Conference - Regular"),
    INTERNATIONAL_DEMO("International Conference - Demo"),
    INTERNATIONAL_WORKSHOP("International Conference - Workshop"),
    INTERNATIONAL_WORKINPROCESSS("International Conference - Work In Process"),

    // Domestic Journal은 KCI로 구분된다.
    DOMESTIC_JOURNAL("Domestic Journal"),
    DOMESTIC_JOURNAL_KCI("International Journal - KCI"),

    // Conference는 Poster, Regular, Demo, Workshop으로 구분된다.
    DOMESTIC_CONFERENCE("Domestic Conference - Conference"),
    DOMESTIC_POSTER("Domestic Conference - Poster"),
    DOMESTIC_REGULAR("Domestic Conference - Regular"),
    DOMESTIC_DEMO("Domestic Conference - Demo"),
    DOMESTIC_WORKSHOP("Domestic Conference - Workshop");

    private String publicationSearchType;

    private PublicationSearchType(String publicationSearchType) {
        this.publicationSearchType = publicationSearchType;
    }

    public String getSearchPublicationType() {
        return this.publicationSearchType;
    }
}