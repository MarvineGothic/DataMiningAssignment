package Assignment_Questionnaire.ID3;

import Assignment_Questionnaire.Student;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import static Assignment_Questionnaire.Library.RU.countAttributeCategories;
import static Assignment_Questionnaire.Library.RU.listAttributeCategories;

/**
 * @author Sergiy Isakov
 */
@SuppressWarnings("unchecked")
public class Algorithm {
    private static Collection<Student> data_partition;

    public Algorithm(Collection<Student> dataPartition) {
        data_partition = dataPartition;
    }

    /**
     * * Takes as a parameter list of attributes of analyzing objects
     * and for each category calculates info(D) and them sum them together
     * within each attribute.
     *
     * @param attributeList list of attributes for object
     * @return a map with class name and value of info(D)
     */
    public static Map<Double, Class> attributeSelection(Collection<Object> attributeList, Object clazz, boolean print) {

        Map<Double, Class> result = new TreeMap<>();
        double InfoD = InfoD(clazz);

        if (attributeList.contains(clazz))
            attributeList.remove(clazz);

        for (Object attribute : attributeList) {
            double infoCurrAttribute = 0;
            Class currentEnumClass = (Class) attribute;                         // getting ENUM Class
            Object[] categories = currentEnumClass.getEnumConstants();          // getting enumValues from ENUM Class

            for (Object category : categories)
                infoCurrAttribute += calculateInfoCurrentAttribute(category, currentEnumClass, clazz);
            result.put(infoCurrAttribute, currentEnumClass);
        }

        if (print) {
            System.out.printf("\n\nID3\nInfoD of a class label %s: %-20.2f\n======================================================\n\n",
                    ((Class) clazz).getSimpleName(), InfoD);
            printSelectedAttributes(result);
        }
        return result;
    }

    /**
     * General formula for Entropy of D for one case
     *
     * @param caseOccurrence frequency of a case
     * @param allOccurrences all occurrences
     * @return entropy
     */
    private static double info(double caseOccurrence, double allOccurrences) {
        if (caseOccurrence == 0) return 0;
        double div = caseOccurrence / allOccurrences;
        return -(div) * (Math.log(div) / Math.log(2));
    }

    /**
     * Expected information needed to classify a tuple in D
     * A Class Label can contain more than two options for a decision
     * So it was made more general using Java Object and Class to calculate a general InfoD
     * for a given Class.
     *
     * @param clazz
     * @return
     */
    private static double InfoD(Object clazz) {
        Object[] subclasses = ((Class) clazz).getEnumConstants();
        double InfoD = 0;
        for (Object subclass : subclasses) {
            int size = listAttributeCategories(data_partition, clazz, subclass).size();
            InfoD += info(size, data_partition.size());
        }
        return InfoD;
    }

    /**
     * Helping method for attributeSelection();
     * Calculates an Info for a current attribute
     *
     * @param category         A specific value within an attribute class
     * @param currentEnumClass Current class of attribute
     * @param clazz            Class Label
     * @return double value of attribute Info
     */
    private static double calculateInfoCurrentAttribute(Object category, Class currentEnumClass, Object clazz) {
        double infoCurrAttribute;
        double sumInfoD = 0;
        long catCountTotal = 0;
        Object[] subclasses = ((Class) clazz).getEnumConstants();
        long[] catNumbers = new long[subclasses.length];

        for (int i = 0; i < subclasses.length; i++) {
            long categoriesNumber = countAttributeCategories(data_partition, currentEnumClass, category, clazz, subclasses[i]);
            catCountTotal += categoriesNumber;
            catNumbers[i] = categoriesNumber;
        }
        for (long catNumber : catNumbers) {
            sumInfoD += info(catNumber, catCountTotal);
        }
        infoCurrAttribute = ((double) catCountTotal / data_partition.size()) * sumInfoD;
        return infoCurrAttribute;
    }

    /**
     * Print result of attributeSelection() method
     *
     * @param result
     */
    public static void printSelectedAttributes(Map<Double, Class> result) {
        for (Map.Entry entry : result.entrySet()) {
            System.out.printf("Class: %-20s Info %-20s %.2f\n",
                    ((Class) entry.getValue()).getSimpleName(), ((Class) entry.getValue()).getSimpleName(), (double) entry.getKey());
        }
    }

    public static Collection<Student> getData_partition() {
        return data_partition;
    }
}

