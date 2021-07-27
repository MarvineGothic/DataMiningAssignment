package Assignment_Questionnaire.enums;

public enum Number {
    S("7"),
    N("9"),
    A("Asparagus"),
    T("13");
    private final String number;

    Number(String number) {
        this.number = number;
    }

    public static Number fromString(String text) {
        for (Number b : Number.values()) {
            if (b.number.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }

    public String toString() {
        return this.number;
    }
}
