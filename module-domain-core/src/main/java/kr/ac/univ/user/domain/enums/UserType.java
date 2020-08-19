package kr.ac.univ.user.domain.enums;

public enum UserType {
    FACULTY("Faculty"),
    FULL_TIME_PhD("full_time_Ph.d."),
    PART_TIME_PhD("part_time_Ph.d."),
    FULL_TIME_MS("full_time_M.S."),
    PART_TIME_MS("part_time_M.S."),
    BS("B.S.");

    private String type;

    private UserType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}