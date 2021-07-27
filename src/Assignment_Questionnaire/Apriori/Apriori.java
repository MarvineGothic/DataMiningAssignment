package Assignment_Questionnaire.Apriori;

import Assignment_Questionnaire.Library.ASU;
import Assignment_Questionnaire.Student;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Sergiy Isakov
 */
@SuppressWarnings("all")
public class Apriori {
    private static Hashtable<ItemSet, Integer> allFrequentItemSets;
    private static List<ItemSet> aprioriResult;
    private static Object[][] dataSet;
    private static boolean empty;

    static {
        initApriori();
    }

    /**
     * Apriori algorithm
     *
     * @param supportThreshold
     * @return
     */
    public static List<ItemSet> apriori(int supportThreshold, boolean printAll) {
        int k;
        Hashtable<ItemSet, Integer> frequentItemSets = generateFrequentItemSetsLevel1(dataSet, supportThreshold);
        for (k = 1; frequentItemSets.size() > 0; k++) {
            if (printAll)
                System.out.print("\n\nFinding frequent itemsets of length " + (k + 1) + "…");
            frequentItemSets = generateFrequentItemSets(supportThreshold, dataSet, frequentItemSets);
            allFrequentItemSets.putAll(frequentItemSets);
            aprioriResult.addAll(frequentItemSets.entrySet().stream().filter(itemSetIntegerEntry -> itemSetIntegerEntry.getValue() >= supportThreshold)
                    .map(Map.Entry::getKey).collect(Collectors.toList()));
            if (printAll) {
                System.out.println(" found " + frequentItemSets.size());
                frequentItemSets.forEach((key, value) -> System.out.println(Arrays.toString(key.set) + " " + value));
            }
        }
        return aprioriResult;
    }


    /**
     * Creating a transactions array to use in apriori algorithm
     * Depending on what we want to analyse, we pass those attribute classes to attributes
     *
     * @param students   list of student instances
     * @param attributes array of custom defined attributes
     * @return array of chosen student data
     */
    public static Object[][] createTransactions(ArrayList<Student> students, Object... attributes) {
        if (attributes == null || attributes == new Object[0])
            throw new IllegalArgumentException("Attributes cannot be empty");
        dataSet = new Object[students.size()][];
        for (int i = 0; i < students.size(); i++) {
            Student st = students.get(i);
            dataSet[i] = new Object[attributes.length];
            for (int j = 0; j < attributes.length; j++) {
                dataSet[i][j] = st.getAttributeValue(attributes[j]);
            }
        }
        return dataSet;
    }

    /**
     * Prints out result for given ItemSet, confidenceLimit and Class
     *
     * @param resultItemSet
     * @param confidenceLimit
     * @param definingClass
     * @param definedClass
     */
    public static void confidence(ItemSet resultItemSet, double confidenceLimit, Class definingClass, Class definedClass) {
        double supportA;
        double supportAB;
        double confidence = 0;

        Set<ItemSet> subsetsOfItemSet = new HashSet<>(ASU.objectArrayToItemSet(resultItemSet.set));

        for (ItemSet i : allFrequentItemSets.keySet())
            if (ASU.isSubset(resultItemSet.set, i.set) && i.set.length < resultItemSet.set.length)
                subsetsOfItemSet.add(i);

        List<ItemSet> listOfSubsets = new ArrayList<>(subsetsOfItemSet);
        Collections.sort(listOfSubsets);
        //System.out.println("Confidence for " + Arrays.toString(resultItemSet.set));
        for (ItemSet itemSet : listOfSubsets) {
            Object[] difference = ASU.differenceSet(resultItemSet.set, itemSet.set);
            supportA = countSupport(itemSet.set, dataSet);
            supportAB = countSupport(resultItemSet.set, dataSet);
            confidence = supportAB * 100 / supportA;

            if (confidence >= confidenceLimit) {
                if (definingClass != null && itemSet.set.length == 1 && itemSet.set[0].getClass().isAssignableFrom(definingClass)) {
                    empty = false;
                    System.out.printf("%s %-20s [support = %.0f%% confidence = %.0f%%]\n %s \n\n",
                            itemSet, "=>", supportAB * 100 / dataSet.length, confidence,
                            Arrays.toString(difference).replaceAll("]|\\[", "").replaceAll(",", "\n"));
                }
                if (definedClass != null && difference.length == 1 && difference[0].getClass().isAssignableFrom(definedClass)) {
                    empty = false;
                    System.out.printf("%s \n%-80s=> %s [support = %.0f%% confidence = %.0f%%]\n\n",
                            itemSet, "-------------------------------------------------------------------------------",
                            Arrays.toString(difference),
                            supportAB * 100 / dataSet.length, confidence);
                }
            }
        }
    }

    /**
     * Prints out all association Rules from apriori result using method confidence()
     *
     * @param confidenceLimit
     * @param definingClass
     * @param definedClass
     */
    public static void associationRules(double confidenceLimit, Class definingClass, Class definedClass) {
        empty = true;
        System.out.println("\n\nApriori. Association rules:\n================================\n");

        for (ItemSet itemSet : aprioriResult)
            confidence(itemSet, confidenceLimit, definingClass, definedClass);

        if (empty)
            System.out.println("No results.\nProbably you've chose too high values for support threshold or confidence.\n" +
                    "Try again with lower threshold or confidence\n" +
                    "============================================================================");
    }

    /**
     * Generates unique ItemSets for next levels based on previous ItemSets
     * and then sorts out those which doesn't satisfy supportThreshold condition
     *
     * @param supportThreshold   a limit below which support value is not relevant
     * @param transactions       a set of all transactions
     * @param lowerLevelItemSets the set of previous ItemSets
     * @return new map of ItemSets and support values
     */
    private static Hashtable<ItemSet, Integer> generateFrequentItemSets(int supportThreshold,
                                                                        Object[][] transactions, Hashtable<ItemSet, Integer> lowerLevelItemSets) {
        Hashtable<ItemSet, Integer> result = new Hashtable<>();

        List<ItemSet> lowerLevelItemList = new ArrayList<>((lowerLevelItemSets.keySet()));
        int itemSetLength = lowerLevelItemList.get(0).set.length + 1;
        int countSupport;

        for (int i = 0; i < lowerLevelItemList.size(); i++)
            for (int j = i + 1; j < lowerLevelItemList.size(); j++) {
                ItemSet aSet = joinSets(lowerLevelItemList.get(i), lowerLevelItemList.get(j));
                if (aSet.set.length == itemSetLength && (countSupport = countSupport(aSet.set, transactions)) >= supportThreshold)
                    result.put(aSet, countSupport);

            }
        return result;
    }

    /**
     * Simple join of two ItemSet without duplicates
     *
     * @param first  ItemSet
     * @param second ItemSet
     * @return set of two ItemSet
     */
    private static ItemSet joinSets(ItemSet first, ItemSet second) {
        return new ItemSet(ASU.mergeTwoArrays(first.set, second.set));
    }

    /**
     * Generator of ItemSets for level 1
     * i.e. set of all single elements from transactions that fulfill requirement of supportThreshold
     *
     * @param transactions     a set of sets in which we look up for itemSet elements
     * @param supportThreshold a limit below which support value is not relevant
     * @return set of frequent elements for level 1
     */
    private static Hashtable<ItemSet, Integer> generateFrequentItemSetsLevel1(Object[][] transactions, int supportThreshold) {
        Hashtable<ItemSet, Integer> result = new Hashtable<>();
        int count;

        Set<ItemSet> setOfTransactElements = new HashSet<>();
        for (Object[] transaction : transactions) setOfTransactElements.addAll(ASU.objectArrayToItemSet(transaction));

        for (ItemSet element : setOfTransactElements)
            if ((count = countSupport(element.set, transactions)) >= supportThreshold)
                result.put(element, count);
        return result;
    }

    /**
     * Counts support (frequency) of itemSet in transactions
     *
     * @param itemSet      a set which frequency needs to be count
     * @param transactions a set of sets in which we look up for itemSet occurrence
     * @return frequency of itemSet in transactions
     */
    private static int countSupport(Object[] itemSet, Object[][] transactions) {
        // Assumes that items in ItemSets and transactions are both unique
        int count = 0;
        for (Object[] sets : transactions)
            if (ASU.isSubset(sets, itemSet))
                count++;
        return count;
    }

    public static void initApriori() {
        allFrequentItemSets = new Hashtable<>();
        aprioriResult = new ArrayList<>();
    }
}
