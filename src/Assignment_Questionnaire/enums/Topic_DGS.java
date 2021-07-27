package Assignment_Questionnaire.enums;

public enum Topic_DGS {
    NOT_INTERESTED(0, "Not interested"), MEH(1, "Meh"), SOUNDS_INTERESTING(2, "Sounds interesting"), VERY_INTERESTING(3, "Very interesting");
    private final String name;
    int rate;

    Topic_DGS(int rate, String name) {
        this.rate = rate;
        this.name = name;
    }

    public static Topic_DGS fromString(String text) {
        for (Topic_DGS b : Topic_DGS.values()) {
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
        return "Define groups of similar objects";
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getTopicName() + " - " + name;
    }
}
