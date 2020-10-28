package kr.ac.univ.loginHistory.domain.enums;

public enum AudLoginResultType {
    SUCCESS("Success"),
    FAIL("Fail");


    private String type;

    private AudLoginResultType(String type) {
        this.type = type;
    }

    public String getAudLoginResultType() {
        return this.type;
    }
}