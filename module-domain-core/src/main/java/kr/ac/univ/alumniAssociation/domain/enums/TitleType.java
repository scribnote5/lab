package kr.ac.univ.alumniAssociation.domain.enums;

public enum TitleType {
    INTRODUCTION("Introduction"),
    ACTIVITY_RESULT("Activity Result");

    private String type;

    private TitleType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
