package kr.ac.univ.user.domain.enums;

public enum AuthorityType {
    NON_USER("non_user"),
    GENERAL("general"),
    MANAGER("manager"),
    ROOT("root");

    private String type;

    private AuthorityType(String type) {
        this.type = type;
    }

    public String getAuthorityType() {
        return this.type;
    }
}