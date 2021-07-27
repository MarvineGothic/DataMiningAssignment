package Assignment_Questionnaire.Library;

/**
 * @author Sergiy Isakov
 * <p>
 * Distance tool utility class
 */
public class DU {

    public static double MinkowskiDistance(double[] X, double[] Y, int order) {
        int count = 0;
        double distance = 0.0;
        double sum = 0.0;
        if (X.length != Y.length) {
            throw new IllegalArgumentException("the number of elements" +
                    " in X must match the number of elements in Y");
        } else {
            count = X.length;
        }
        for (int i = 0; i < count; i++) {
            sum = sum + Math.pow(Math.abs(X[i] - Y[i]), order);
        }
        distance = Math.pow(sum, (1 / order));
        return distance;
    }

    public static double EuclideanDistance(double[] X, double[] Y) {
        int count = 0;
        double distance = 0.0;
        double sum = 0.0;
        if (X.length != Y.length) {
            throw new IllegalArgumentException("the number of elements" +
                    " in X must match the number of elements in Y");
        } else {
            count = X.length;
        }
        for (int i = 0; i < count; i++) {
            sum = sum + Math.pow(Math.abs(X[i] - Y[i]), 2);
        }
        distance = Math.sqrt(sum);
        return distance;
    }

    public static double ManhattanDistance(double[] X, double[] Y) {
        int count = 0;
        double distance = 0.0;
        double sum = 0.0;
        if (X.length != Y.length) {
            throw new IllegalArgumentException("the number of elements" +
                    " in X must match the number of elements in Y");
        } else {
            count = X.length;
        }
        for (int i = 0; i < count; i++) {
            sum = sum + Math.abs(X[i] - Y[i]);
        }
        distance = sum;
        return distance;
    }

    public static double ChebyshevDistance(double[] X, double[] Y) {
        int count = 0;
        if (X.length != Y.length) {
            throw new IllegalArgumentException("the number of elements" +
                    " in X must match the number of elements in Y");
        } else {
            count = X.length;
        }
        double[] newData = new double[count];
        for (int i = 0; i < count; i++) {
            newData[i] = Math.abs(X[i] - Y[i]);
        }

        double max = Double.NEGATIVE_INFINITY;
        for (double num : newData) {
            if (num > max) {
                max = num;
            }
        }
        return max;
    }
}
