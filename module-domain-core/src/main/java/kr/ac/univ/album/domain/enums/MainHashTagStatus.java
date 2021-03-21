package kr.ac.univ.album.domain.enums;

public enum MainHashTagStatus {
    PRINT("Print"),
    SKIP_SPACE("Skip space"),
    KEEP_SPACE("Keep space"),
    NON_PRINT("None print");

    private String status;

    private MainHashTagStatus(String type) {
        this.status = status;
    }

    public String getMainHashTagPrintStatus() {
        return this.status;
    }
}