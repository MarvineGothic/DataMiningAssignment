package Assignment_Questionnaire.enums;

public enum Topic_DED {
    NOT_INTERESTED(0, "Not interested"), MEH(1, "Meh"), SOUNDS_INTERESTING(2, "Sounds interesting"), VERY_INTERESTING(3, "Very interesting");
    private final String name;
    int rate;

    Topic_DED(int rate, String name) {
        this.rate = rate;
        this.name = name;
    }

    public static Topic_DED fromString(String text) {
        for (Topic_DED b : Topic_DED.values()) {
            if (b.name.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }

    public int getRate() {
        return rate;
    }

    public String getTopicName() {
        return "Design efficient databases for large amounts of data";
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getTopicName() + " - " + name;
    }
}
