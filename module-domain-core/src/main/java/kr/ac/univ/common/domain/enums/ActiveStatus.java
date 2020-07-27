package kr.ac.univ.common.domain.enums;

public enum ActiveStatus {
    ACTIVE("active"),
    INACTIVE("inactive");

    private final String status;

    ActiveStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }
}