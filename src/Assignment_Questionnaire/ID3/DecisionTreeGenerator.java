package Assignment_Questionnaire.ID3;

import Assignment_Questionnaire.Student;

import java.util.*;

import static Assignment_Questionnaire.ID3.Algorithm.attributeSelection;
import static Assignment_Questionnaire.ID3.Algorithm.getData_partition;
import static Assignment_Questionnaire.Library.RU.listAttributeCategories;

/**
 * @author Sergiy Isakov
 */
@SuppressWarnings("all")
public class DecisionTreeGenerator {
    private static Collection<Student> data_partition;
    private static Collection<Object> editedAttributeList;

    static {
        data_partition = getData_partition();
    }

    /**
     * Creates a Node for a decision tree.
     * Node should implement java swing TreeNode in order to work properly with DecisionTree GUI.
     * Algorithm is from the book M.Kaufmann "Data Mining - Concepts and techniques" with small changes and does as follows:
     * 1. Create a Node node
     * 2. If tuples in D are all of the same class C, then
     * 3. return node with a leaf labeled with the class C
     * 4. if attribute_list is empty then
     * 5. return node with a leaf with a majority class in D
     * Steps 2 - 5 are implemented in identifyFinalNode() method
     * 6. apply attributeSelection() method to find the "bestSplittingCriterion"
     * 7. Label node with bestSplittingCriterion
     * 8. if bestSplittingCriterion is discrete-valued and multiway splits allowed then
     * 9. Remove bestSplittingCriterion from editedAttributeList
     * 10. for each "outcome_j" of "splittCritOutcomes"
     * 11. Let "Dj" be the set of data tuples in D satisfying outcome_j
     * 12. if "Dj" is empty then
     * 13. do nothing
     * 14. else attach the node returned by "generateDecisionTree(Dj, editedAttributeList, clazz)" to node
     * endfor
     * 15. return node
     *
     * @param data_partitionD data set of student tuples
     * @param attributeList   list of attributes for student items
     * @param clazz           a Class that decides attributes
     * @return a node for a decision tree
     */
    public static Node<Object> generateDecisionTree(Collection<Student> data_partitionD, Collection<Object> attributeList, Object clazz) {
        if (editedAttributeList == null) setEditedAttributeList(attributeList);
        Node<Object> node = new Node(null, null, null);

        if (identifyFinalNode(node, data_partitionD, attributeList, clazz)) return node;

        Object bestSplittingCriterion = selectBestAttribute(attributeSelection(attributeList, clazz, false));
        Object[] splittCritOutcomes = ((Class) bestSplittingCriterion).getEnumConstants();
        node.setLabel(bestSplittingCriterion);

        editedAttributeList.remove(bestSplittingCriterion);

        for (Object outcome_j : splittCritOutcomes) {
            Collection<Student> Dj = new ArrayList<>(listAttributeCategories(data_partitionD, bestSplittingCriterion, outcome_j));
            if (Dj.size() > 0) {
                Node leaf = generateDecisionTree(Dj, editedAttributeList, clazz);
                leaf.addParent(node);
                leaf.setLabel(node + ":" + outcome_j);
                node.addLeaf(leaf);
            }
        }
        return node;
    }

    /**
     * Calculates steps 2 - 5 of "generateDecisionTree" algorithm
     *
     * @param node
     * @param data_partitionD
     * @param attributeList
     * @param clazz
     * @return
     */
    private static boolean identifyFinalNode(Node<Object> node, Collection<Student> data_partitionD, Collection<Object> attributeList, Object clazz) {
        Object[] subclasses = ((Class) clazz).getEnumConstants();
        int[] subclassesCounts = new int[subclasses.length];
        for (int i = 0; i < subclasses.length; i++)
            subclassesCounts[i] = listAttributeCategories(data_partitionD, clazz, subclasses[i]).size();

        int majorityClass = Arrays.stream(subclassesCounts).max().getAsInt();

        for (int j = 0; j < subclasses.length; j++) {
            if (attributeList.isEmpty() && subclassesCounts[j] == majorityClass || areTuplesOfSameClass(subclasses[j], subclasses, subclassesCounts)) {
                node.addLeaf(new Node<>(subclasses[j], node, null));
                return true;
            }
        }
        return false;
    }

    /**
     * Helping method used in identifyFinalNode() method
     * Checks if all tuples are only of the class "clazz"
     *
     * @param clazz
     * @param subclasses
     * @param subclassesCounts
     * @return
     */
    private static boolean areTuplesOfSameClass(Object clazz, Object[] subclasses, int[] subclassesCounts) {
        for (int i = 0; i < subclasses.length; i++)
            if (!subclasses[i].equals(clazz) && subclassesCounts[i] != 0) return false;
        return true;
    }

    /**
     * Helping method for "generateDecisionTree" algorithm
     * Selects best attribute based on attribute info
     *
     * @param attributes
     * @return
     */
    private static Class selectBestAttribute(Map<Double, Class> attributes) {
        double minInfoD = Collections.min(attributes.keySet());
        return attributes.get(minInfoD);
    }

    public static void setEditedAttributeList(Collection<Object> editedAttributeList) {
        DecisionTreeGenerator.editedAttributeList = new ArrayList<>();
        DecisionTreeGenerator.editedAttributeList = editedAttributeList;
    }
}
