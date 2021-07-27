package Assignment_Questionnaire.enums;

public enum Topics {
    DED("Design efficient databases for large amounts of data"),
    CPM("Create predictive models (e.g. weather or stock market prediction)"),
    DGS("Define groups of similar objects (e.g. users from a dating site)"),
    VD("Visualise data"),
    SPS("Study patterns on sets (e.g. Amazon shopping carts)"),
    SPSQ("Study patterns on sequences (e.g. exercises on a workout session)"),
    SPG("Study patterns on graphs (e.g. Facebook)"),
    SPT("Study patterns on text (e.g. spam mail)"),
    SPI("Study patterns on images (e.g. face detection)"),
    CDMA("Code data mining algorithms"),
    UDMT("Use off-the-shelf data mining tools");
    private final String topicName;

    Topics(String topicName) {
        this.topicName = topicName;
    }

    public boolean equalsName(String other) {
        return topicName.equals(other);
    }

    public String toString() {
        return this.topicName;
    }
}
