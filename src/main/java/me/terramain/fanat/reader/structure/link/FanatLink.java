package me.terramain.fanat.reader.structure.link;

public class FanatLink {
    public String linkText;
    public FanatLinkType linkType;
    public FanatLink parent;

    public FanatLink(String linkText, FanatLinkType linkType, FanatLink parent) {
        this.linkText = linkText;
        this.linkType = linkType;
        this.parent = parent;
    }
    public FanatLink(String linkText, FanatLinkType linkType) {
        this.linkText = linkText;
        this.linkType = linkType;
        this.parent = null;
    }

    @Override
    public String toString() {
        return "FanatLink{" +
                "linkText='" + linkText + '\'' +
                ", linkType=" + linkType +
                ", parent=" + parent +
                '}';
    }
}
