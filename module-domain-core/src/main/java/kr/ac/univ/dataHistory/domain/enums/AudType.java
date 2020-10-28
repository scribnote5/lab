package kr.ac.univ.dataHistory.domain.enums;

public enum AudType {
    SELECT("Select"),
    INSERT("Insert"),
    UPDATE("Update"),
    DELETE("Delete");

    private String audType;

    private AudType(String audType) {
        this.audType = audType;
    }

    public String getAudType() {
        return this.audType;
    }
}