package kr.ac.univ.project.domain.enums;

public enum ProjectStatus {
    CURRENT("Current"),
    PREVIOUS("Previous");

    private String status;

    private ProjectStatus(String type) {
        this.status = status;
    }

    public String getProjectStatus() {
        return this.status;
    }
}