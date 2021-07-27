package Assignment_Questionnaire.KMean;

import Assignment_Questionnaire.Library.Normalize;
import Assignment_Questionnaire.Student;

import java.util.ArrayList;


/**
 * @author Sergiy Isakov
 */
@SuppressWarnings("all")
public class ClusterData {
    private static ArrayList<ClusterObject> clusterObjects;
    private static Object clazz;

    /**
     * Creates a data set from chosen attributes to process in clustering algorithm
     *
     * @param list
     * @param attributes
     * @return
     */
    public static ArrayList<ClusterObject> createClusterData(ArrayList<Student> list, Object clazz, Object... attributes) {
        clusterObjects = new ArrayList<>();
        ClusterData.clazz = clazz;
        for (Student s : list) {
            ClusterObject.Builder clusterObject = ClusterObject.newBuilder();
            clusterObject.setClazz(s.getAttributeValue(clazz));
            for (Object attribute : attributes) {
                String name = ((Class) attribute).getSimpleName();
                clusterObject.setAttribute(name, Normalize.attribute(name, s));
            }
            clusterObjects.add(clusterObject.build());
        }
        return clusterObjects;
    }

    /**
     * Prints out cluster data
     *
     * @param list
     */
    public static void printClusterData(ArrayList<Student> list) {
        if (clusterObjects == null) createClusterData(list, clazz);
        clusterObjects.forEach(System.out::println);
    }

    public static Object getClazz() {
        return clazz;
    }
}