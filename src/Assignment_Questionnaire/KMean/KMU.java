package Assignment_Questionnaire.KMean;

import java.util.*;

/**
 * @author Sergiy Isakov
 * <p>
 * KMean utility class
 */
public class KMU {
    /**
     * Randomly choosing unique Objects as cluster centers
     *
     * @param k
     * @param data
     * @param cluster
     * @return
     */
    public static ArrayList<Cluster> randomCenter(int k, ArrayList<ClusterObject> data, Cluster cluster) {
        ArrayList<Cluster> clusters = new ArrayList<>();
        Set<ClusterObject> clusterObjects = new HashSet<>();

        while (clusterObjects.size() < k)
            clusterObjects.add(data.get((int) (Math.random() * data.size())));

        for (ClusterObject clData : clusterObjects)
            clusters.add(new KMeanCluster(clData));

        return clusters;
    }

    /**
     * Two nested for loops reassign each object to the cluster to which the object is the most similar,
     * based of the mean value of the objects in the cluster. By comparing distances from object to all centers
     * we find the closest one and add an object to the cluster.
     *
     * @param data
     * @param clusters
     */
    public static void makeClusters(List<ClusterObject> data, List<Cluster> clusters) {
        for (ClusterObject clusterObject : data) {
            Cluster cluster = clusters.get(0);
            for (int i = 1; i < clusters.size(); i++)
                cluster = manhattanDistance(clusterObject, clusters.get(i).mainCluster) <
                        manhattanDistance(clusterObject, cluster.mainCluster) ? clusters.get(i) : cluster;
            cluster.ClusterMembers.add(clusterObject);
        }
    }

    /**
     * Calculate Average(mean) instance out of a list.
     *
     * @param cm
     * @return
     */
    public static ClusterObject clusterMean(List<ClusterObject> cm) {
        // average(most frequent) class
        Object clazz = ClusterData.getClazz();
        TreeMap<Long, Object> dM = new TreeMap<>();
        for (Object e : ((Class) clazz).getEnumConstants())
            dM.put(cm.stream().filter(a -> a.getClazz().equals(e)).count(), e);


        Object meanClass = dM.lastEntry().getValue();

        ClusterObject.Builder object = ClusterObject.newBuilder();
        object.setClazz(meanClass);
        for (Map.Entry attr : cm.get(0).getAttributes().entrySet()) {
            String name = (String) attr.getKey();
            Double value = (cm.stream().mapToDouble(c -> c.getAttributes().get(name))).average().orElse(-100);
            object.setAttribute(name, value);
        }
        return object.build();
    }

    /**
     * Method to calculate Euclidian distance for two objects with four parameters
     *
     * @param i1 first object
     * @param i2 second object
     * @return euclidian distance
     */
    public static double euclidianDistance(ClusterObject i1, ClusterObject i2) {
        double result = 0;
        for (Map.Entry attr : i1.getAttributes().entrySet()) {
            String key = (String) attr.getKey();
            result += Math.pow(i1.getAttributes().get(key) - i2.getAttributes().get(key), 2);
        }
        return Math.sqrt(result);
    }

    /**
     * Method to calculate Manhattan distance for two objects with four parameters
     *
     * @param i1
     * @param i2
     * @return
     */
    public static double manhattanDistance(ClusterObject i1, ClusterObject i2) {
        double result = 0;
        for (Map.Entry attr : i1.getAttributes().entrySet()) {
            String key = (String) attr.getKey();
            result += Math.abs(i1.getAttributes().get(key) - i2.getAttributes().get(key));
        }
        return result;
    }
}