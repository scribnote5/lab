package kr.ac.univ.maintenance.domain.enums;

public enum MaintenanceStatus {
    REQUIREMENT("Requirement"),
    PROGRESSING("Progressing"),
    COMPLETED("Completed");

    private String status;

    private MaintenanceStatus(String type) {
        this.status = status;
    }

    public String getProjectStatus() {
        return this.status;
    }
}
