package kr.ac.univ.publication.domain.enums;

public enum PublicationType {
    JOURNAL("Journal"),
    CONFERENCE("Conference"),

    // Journal은 KCI, SCOPUS, SCI로 구분된다.
    JOURNAL_KCI("Journal - KCI"),
    JOURNAL_SCOPUS("Journal - SCOPUS"),
    JOURNAL_SCI("Journal - SCI"),

    // Conference는 Poster, Regular, Demo, Workshop으로 구분된다.
    CONFERENCE_POSTER("Conference - Poster"),
    CONFERENCE_REGULAR("Conference - Regular"),
    CONFERENCE_DEMO("Conference - Demo"),
    CONFERENCE_WORKSHOP("Conference - Workshop");

    private String publicationType;

    private PublicationType(String publicationType) {
        this.publicationType = publicationType;
    }

    public String getPublicationType() {
        return this.publicationType;
    }
}