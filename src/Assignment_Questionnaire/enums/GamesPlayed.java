package Assignment_Questionnaire.enums;

public enum GamesPlayed {
    CC("Candy Crush"),
    WORDFEUD("Wordfeud"),
    MINECRAFT("Minecraft"),
    FARMVILLE("FarmVille"),
    FIFA2017("Fifa 2017"),
    STARWARS("Star Wars Battlefield"),
    LS("Life is Strange"),
    BTTLF4("Battlefield 4"),
    JOURNEY("Journey"),
    GHOME("Gone Home"),
    STPARABLE("Stanley Parable"),
    CODBO("Call of Duty: Black Ops"),
    RLEAGUE("Rocket League"),
    BTH("Bloodthorne"),
    RTR("Rise of the Tomb Raider"),
    WITNESS("The Witness"),
    HERSTORY("Her Story"),
    F4("Fallout 4"),
    DAI("Dragon Age: Inquisition"),
    CSGO("Counter Strike_ GO"),
    AB("Angry Birds"),
    LOU("The Last Of Us"),
    MC("The Magic Circle"),
    NOTPLAYED("I have not played any of these games");

    private final String gamesPlayed;

    GamesPlayed(String gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public static GamesPlayed fromString(String text) {
        for (GamesPlayed b : GamesPlayed.values()) {
            if (b.gamesPlayed.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }

    public boolean equalsName(String other) {
        return gamesPlayed.equals(other);
    }

    public String toString() {
        return this.gamesPlayed;
    }
}
