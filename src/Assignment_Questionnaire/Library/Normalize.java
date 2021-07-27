package Assignment_Questionnaire.Library;

import Assignment_Questionnaire.Student;
import Assignment_Questionnaire.StudentManager;

import static Assignment_Questionnaire.Library.NU.minMax;

/**
 * @author Sergiy Isakov
 */
public class Normalize {
    /**
     * Normalizing attribute values.
     * For array attributes as "FavoriteGame" or "GamesPlayed" it's a length of array.
     * "Topics" has enum values from 0 to 3.
     *
     * @param name    name of attribute Class
     * @param student student Item
     * @return
     */
    public static double attribute(String name, Student student) {
        double value = student.getAnyAttribute(name);
        double minValue = 0;
        double maxValue = 0;
        if (name.equals("GamesPlayed")) {
            maxValue = StudentManager.maxGames();
        }
        if (name.contains("Topic_")) {
            maxValue = 3;
        }
        if (name.equals("FavoriteGame")) {
            maxValue = StudentManager.maxFavGames();
        }
        return minMax(value, minValue, maxValue, 0, 1);
    }
}
