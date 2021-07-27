package Assignment_Questionnaire.Library;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author Sergiy Isakov
 * <p>
 * Normalization Utility Class
 */
public class NU {
    /**
     * MinMax normalization method
     *
     * @param vi
     * @param mina
     * @param maxa
     * @param newMina
     * @param newMaxa
     * @return
     */
    public static double minMax(double vi, double mina, double maxa, double newMina, double newMaxa) {
        return newMina + (vi - mina) * (newMaxa - newMina) / (maxa - mina);
    }

    public static double zScoreD(double value, double mean, double stdDev) {
        return (value - mean) / stdDev;
    }

    public static double decimalScaling(int value, int min, int max) {
        int maxAbs = Math.max(Math.abs(min), Math.abs(max));
        int dec = maxAbs == 0 ? 10 : ((int) Math.pow(10, ((int) Math.log10(maxAbs) + 1)));
        return (double) value / dec;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
