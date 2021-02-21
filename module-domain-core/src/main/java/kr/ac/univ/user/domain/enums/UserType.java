package kr.ac.univ.user.domain.enums;

public enum UserType {
    // 앞에 있는 알파뱃은 사용자 페이지에서 정렬 순서를 의미한다.
    A_FACULTY("faculty"),
    B_FULL_TIME_PHD("full time Ph.d."),
    C_FULL_TIME_MS("full time M.S."),
    D_PART_TIME_PHD("part time Ph.d."),
    E_PART_TIME_MS("part time M.S."),
    F_BS("B.S.");

    private String type;

    private UserType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}