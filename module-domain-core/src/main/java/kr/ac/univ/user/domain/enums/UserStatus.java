package kr.ac.univ.user.domain.enums;

public enum UserStatus {
    ATTENDING("attending"),
    GRADUATED("graduated");

    private String status;

    private UserStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }
}