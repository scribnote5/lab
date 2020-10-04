package kr.ac.univ.seminar.domain.enums;

public enum SeminarType {
    DELIVERY_SEMINAR("Delivery seminar"),
    LECTURE_SEMINAR("Lecture seminar"),
    PUBLICATION_SEMINAR("Publication seminar"),
    THESIS_SEMINAR("Thesis seminar");

    private String type;

    private SeminarType(String type) {
        this.type = type;
    }

    public String getSeminarType() {
        return this.type;
    }
}