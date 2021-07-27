package Assignment_Questionnaire.KMean;

import java.util.ArrayList;

import static Assignment_Questionnaire.KMean.KMU.*;

/**
 * @author Sergiy Isakov
 */
public class KMeans {
    private static ArrayList<Cluster> clusters;


    /**
     * Start by creating a List of cluster instances to make using a randomCenter method from KMU utility class.
     * In a while loop:
     * assign a boolean noChange which shows that we need to stop the loop if centers can't be changed any more
     * Then update the cluster means in the next for loop, using a temporary list of clusters.
     * if new calculated center differs from the previous, value of noChange become false.
     * Temporary list is filed up any way, in the case if at least one center must be updated, then we need to repeat
     * while loop again.
     * In the end we check if changes were made  - by noChange. If there are changes, we assign list of clusters
     * by a new list with new centers. In the other case job is done and we break the while loop.
     * Before returning the result we sort the list. Basically for the sake of testing.
     *
     * @param k    number of defined clusters
     * @param data data set of instances that has to be clustered
     * @return ArrayList of Cluster instances
     */
    public static ArrayList<Cluster> KMeansPartition(int k, ArrayList<ClusterObject> data, boolean print) {
        System.out.println("\n\n\nClustering KMeans:\n");
        System.out.println("Topic_CDMA - Code data mining algorithms");
        System.out.println("Topic_CPM - Create predictive models");
        System.out.println("Topic_DED - Design efficient databases for large amounts of data");
        System.out.println("Topic_DGS - Define groups of similar objects");
        System.out.println("Topic_SPG - Study patterns on graphs");
        System.out.println("Topic_SPI - Study patterns on images");
        clusters = randomCenter(k, data, new KMeanCluster());

        while (true) {
            boolean noChange = true;

            makeClusters(data, clusters);

            ArrayList<Cluster> tempCenters = new ArrayList<>();
            for (Cluster cluster : clusters) {
                ClusterObject newCenter = clusterMean(cluster.getClusterMembers());
                if (!newCenter.equals(cluster.getMainCluster()))
                    noChange = false;
                tempCenters.add(new KMeanCluster(newCenter));
            }

            if (!noChange)
                clusters = new ArrayList<>(tempCenters);
            else
                break;
        }
        if (print) printClusters();
        return clusters;
    }

    public static void printClusters() {
        if (clusters == null)
            throw new IllegalArgumentException("You need to create clusters first.\nRun method KMeansPartition");
        clusters.forEach(System.out::println);
    }

}
