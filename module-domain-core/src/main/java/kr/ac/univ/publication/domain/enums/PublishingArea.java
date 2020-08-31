package kr.ac.univ.publication.domain.enums;

public enum PublishingArea {
    INTERNATIONAL("International"),
    DOMESTIC("Domestic");

    private String publishingArea;

    private PublishingArea(String publishingArea) {
        this.publishingArea = publishingArea;
    }

    public String getPublishingArea() {
        return this.publishingArea;
    }
}