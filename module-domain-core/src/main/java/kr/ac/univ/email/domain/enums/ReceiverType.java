package kr.ac.univ.email.domain.enums;

public enum ReceiverType {
    ADMIN("Admin"),
    DEVELOPER("Developer");

    private final String type;

    ReceiverType(String type) {
        this.type = type;
    }

    public String getUserType() {
        return this.type;
    }
}