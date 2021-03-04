package kr.ac.univ.maintenance.domain.enums;

public enum ReceiverType {
    NONE("None"),
    ADMIN("Admin"),
    DEVELOPER("Developer"),
    ALL("All");

    private final String type;

    ReceiverType(String type) {
        this.type = type;
    }

    public String getUserType() {
        return this.type;
    }
}