package Assignment_Questionnaire.KMean;

/**
 * @author Sergiy Isakov
 */
public class KMeanCluster extends Cluster{

    public KMeanCluster() {
        super();
    }

    public KMeanCluster(ClusterObject clusterCenter) {
        super(clusterCenter);
    }

    @Override
    public String toString() {
        String toPrint = "-----------------------------------CLUSTER START------------------------------------------"
                + System.getProperty("line.separator") + "Center:\n" + mainCluster.toString() +
                "\n------------------------------------------------------------------------------------------\n";

        for (ClusterObject i : this.ClusterMembers) {
            toPrint = toPrint.concat(i.toString() + System.getProperty("line.separator"));
        }
        toPrint = toPrint.concat("-----------------------------------CLUSTER END-------------------------------------------"
                + System.getProperty("line.separator"));

        return toPrint;
    }

    @Override
    public int compareTo(Cluster o) {
        return 0;
    }
}
