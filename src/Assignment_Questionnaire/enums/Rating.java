package Assignment_Questionnaire.enums;

public enum Rating {
    NOT_INTERESTED(0), MEH(1), SOUNDS_INTERESTING(2), VERY_INTERESTING(3);
    int rate;

    Rating(int rate) {
        this.rate = rate;
    }

    public int getRate() {
        return rate;
    }
}
