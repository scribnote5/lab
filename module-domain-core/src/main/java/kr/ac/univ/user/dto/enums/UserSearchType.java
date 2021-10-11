package kr.ac.univ.user.dto.enums;

public enum UserSearchType {
    A_SHOW_ALL("Show All"),
    B_FACULTY("Faculty"),
    C_PHD("Ph.d."),
    D_MS("M.S."),
    E_BS("B.S."),
    F_ALUMNI("Alumni");

    private String userSearchType;

    private UserSearchType(String userSearchType) {
        this.userSearchType = userSearchType;
    }

    public String getUserSearchType() {
        return this.userSearchType;
    }
}