package Assignment_Questionnaire.Library;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Sergiy Isakov
 * <p>
 * Cleaning Utility Class
 */
public class CU {

    /**
     * Using a regex expression this method processes attribute double values
     * from uncleaned raw data set to find a mean value for missing values.
     * Sorts out only values that fits in given pattern using method sortDigitsOut()
     * removes possible outliers by method removeTwoPercent()
     * and returns a mean value
     *
     * @param data
     * @param regex
     * @param column
     * @return
     */
    public static double meanOfAttribute(String[][] data, String regex, int column) {
        HashMap<String, List<String>> attribute = new HashMap<>();
        List<Double> attributeProcessed = new ArrayList<>();
        String attributeTitle = data[0][column];
        attribute.put(attributeTitle, new ArrayList<>());
        for (int i = 1; i < data.length; i++)
            attribute.get(attributeTitle).add(data[i][1]);
        attributeProcessed.addAll(sortDigitsOut(data, column, regex));
        removeTwoPercent(attributeProcessed);
        return meanD(attributeProcessed);
    }

    /**
     * Mean value for integers
     *
     * @param data
     * @return
     */
    public static int meanI(Collection<Integer> data) {
        return (data.stream().mapToInt(Integer::intValue).sum()) / data.size();
    }

    /**
     * Mean value for doubles
     *
     * @param data
     * @return
     */
    public static double meanD(Collection<Double> data) {
        return (data.stream().mapToDouble(Double::doubleValue).sum()) / data.size();
    }

    /**
     * Median value for doubles
     *
     * @param data
     * @return
     */
    public static double median(List<Double> data) {
        Collections.sort(data);
        return (data.size() % 2 == 1) ? data.get(data.size() / 2) : (data.get(data.size() / 2) + data.get(data.size() / 2 - 1)) / 2;
    }

    /**
     * Modes values for doubles
     *
     * @param numbers
     * @return
     */
    public static List<Double> modes(final List<Double> numbers) {
        final Map<Double, Long> countFrequencies = numbers.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        final long maxFrequency = countFrequencies.values().stream()
                .mapToLong(count -> count)
                .max().orElse(-1);

        return countFrequencies.entrySet().stream()
                .filter(tuple -> tuple.getValue() == maxFrequency)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    /**
     * Mean absolute deviation for doubles
     *
     * @param data
     * @return
     */
    public static double meanAbsDevD(Collection<Double> data) {
        final double[] absSum = {0.0};
        data.forEach(v -> absSum[0] += Math.abs(v - meanD(data)));
        return absSum[0] / data.size();
    }

    /**
     * Standard deviation for integer array
     *
     * @param list
     * @return
     */
    public static double stdev(int[] list) {
        double sum = 0.0;
        double mean;
        double num = 0.0;
        double numi;

        for (int i : list)
            sum += i;
        mean = sum / list.length;
        for (int i : list) {
            numi = Math.pow((double) i - mean, 2);
            num += numi;
        }
        return Math.sqrt(num / list.length);
    }

    /**
     * Standard deviation for Collection of Double
     *
     * @param list
     * @return
     */
    public static double stdevList(Collection<Double> list) {
        final double[] num = {0.0};
        list.forEach(n -> num[0] += Math.pow(n - meanD(list), 2));
        return Math.sqrt(num[0] / list.size());
    }

    /**
     * Removes two percent of minimum and maximum values from a data set
     * (Escape possible outliers)
     *
     * @param doubles
     */
    public static void removeTwoPercent(Collection<Double> doubles) {
        int twoProcent = doubles.size() * 2 / 100;
        for (int i = 0; i < twoProcent; i++) {
            doubles.remove(Collections.min(doubles));
            doubles.remove(Collections.max(doubles));
        }
    }

    /**
     * Can sort out digits from two dimension array
     * using a regex expression as a pattern to find
     * useful values
     *
     * @param data
     * @param regex
     * @return
     */
    public static List<Double> sortDigitsOut(Object[][] data, int column, String regex) {
        List<Double> listProcessed = new ArrayList<>();
        for (int i = 1; i < data.length; i++) {
            String line = ((String) data[i][column]).replaceAll("\\s+\\D+", "")
                    .replaceAll(",", ".");
            if (line.matches(regex))
                listProcessed.add(Double.valueOf(line));
        }
        return listProcessed;
    }


}
