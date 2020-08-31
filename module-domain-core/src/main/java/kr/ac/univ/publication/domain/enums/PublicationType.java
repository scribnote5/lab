package kr.ac.univ.publication.domain.enums;

public enum PublicationType {
    JOURNAL("Journal"),
    CONFERENCE("Conference"),
    // Conference는 Poster, Regular, Demo, Workshop, Work in Process로 구분된다.
    POSTER("Poster"),
    REGULAR("Regular"),
    DEMO("Demo"),
    WORKSHOP("Workshop"),
    WORKINPROCESS("Work in Process")
    ;

    private String publicationType;

    private PublicationType(String publicationType) {
        this.publicationType = publicationType;
    }

    public String getPublicationType() {
        return this.publicationType;
    }
}