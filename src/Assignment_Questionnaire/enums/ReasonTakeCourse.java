package Assignment_Questionnaire.enums;

public enum ReasonTakeCourse {
    Interested("I am interested in the subject"),
    Job("It may help me to find a job"),
    Mandatory("This was a mandatory course for me");
    private final String reason;

    ReasonTakeCourse(String reason) {
        this.reason = reason;
    }

    public static ReasonTakeCourse fromString(String text) {
        for (ReasonTakeCourse b : ReasonTakeCourse.values()) {
            if (b.reason.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }

    public boolean equalsName(String other) {
        return reason.equals(other);
    }

    public String getName() {
        return this.reason;
    }

    public String toString() {
        return String.format("Why did you take this course - %s", this.reason);
        //return this.reason;
    }
}
