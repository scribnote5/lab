package kr.ac.univ.learnMore.domain.enums;

public enum DownloadFileType {
    READ("Read File"),
    VIDEO("Video File");

    private String downloadFileType;

    private DownloadFileType(String downloadFileType) {
        this.downloadFileType = downloadFileType;
    }

    public String getDownloadFileType() {
        return this.downloadFileType;
    }
}
