package kr.ac.univ.researchField.domain.enums;

public enum ResearchFieldStatus {
    CURRENT("Current"),
    PREVIOUS("Previous");

    private String status;

    private ResearchFieldStatus(String type) {
        this.status = status;
    }

    public String getProjectStatus() {
        return this.status;
    }
}