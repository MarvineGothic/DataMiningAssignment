package Assignment_Questionnaire.Library;

import Assignment_Questionnaire.Apriori.ItemSet;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Sergiy Isakov
 * <p>
 * Utility to handle arrays and sets and to convert from one to another
 */
@SuppressWarnings("unchecked")
public class ASU {
    public static int[] intSetToArray(Set<Integer> set) {
        int[] array = new int[set.size()];
        int i = 0;
        for (int aSet : set) {
            array[i++] = aSet;
        }
        return array;
    }

    public static long[] longSetToArray(Set<Long> set) {
        long[] array = new long[set.size()];
        int i = 0;
        for (Long aSet : set) {
            array[i++] = aSet;
        }
        return array;
    }

    public static <T> Set<T> objectArrayToSet(T[] array) {
        return Arrays.stream(array).collect(Collectors.toSet());
    }

    public static <T> T[] objectSetToArray(Set<T> objectSet) {
        T[] result = (T[]) new Object[objectSet.size()];
        int i = 0;
        for (T t : objectSet) {
            result[i++] = t;
        }
        return result;
    }

    public static Object[] differenceSet(Object arr1[], Object arr2[]) {
        Set<Object> set = new HashSet<>();
        if (isSubset(arr1, arr2)) {
            set.addAll(Arrays.asList(arr1));
            for (Object anArr2 : arr2)
                set.remove(anArr2);
        }
        return ASU.objectSetToArray(set);
    }

    public static boolean isSubset(Object arr1[], Object arr2[]) {
        int i;
        int j;
        for (i = 0; i < arr2.length; i++) {
            for (j = 0; j < arr1.length; j++) {
                if (arr1[j].getClass().isArray())
                    return isSubset((Object[]) arr1[j], new Object[]{arr2[i]});
                if (arr2[i] == arr1[j])
                    break;
            }
            if (j == arr1.length)
                return false;
        }
        return true;
    }

    public static Set<ItemSet> objectArrayToItemSet(Object[] arr1) {
        Set<ItemSet> set = new HashSet<>();
        for (Object aMerged : arr1) {
            if (aMerged.getClass().isArray())
                for (int i = 0; i < Array.getLength(aMerged); i++) {
                    set.add(new ItemSet(new Object[]{Array.get(aMerged, i)}));
                }
            else
                set.add(new ItemSet(new Object[]{aMerged}));
        }
        return set;
    }

    public static Set<Object> ArraysToSet(Object[] arr1, Object[] arr2) {
        Object[] merged = new Object[arr1.length + arr2.length];
        System.arraycopy(arr1, 0, merged, 0, arr1.length);
        System.arraycopy(arr2, 0, merged, arr1.length, arr2.length);
        Set<Object> set = new HashSet<>();
        set.addAll(Arrays.asList(merged));
        return set;
    }

    public static Object[] mergeTwoArrays(Object[] arr1, Object[] arr2) {
        return ASU.objectSetToArray(ArraysToSet(arr1, arr2));
    }
}
