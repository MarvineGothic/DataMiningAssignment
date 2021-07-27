package Assignment_Questionnaire;

import Assignment_Questionnaire.DecisionTree.DecisionTree;
import Assignment_Questionnaire.ID3.Algorithm;
import Assignment_Questionnaire.ID3.DecisionTreeGenerator;
import Assignment_Questionnaire.ID3.Node;
import Assignment_Questionnaire.enums.*;
import Assignment_Questionnaire.enums.Number;

import java.util.ArrayList;
import java.util.Arrays;

import static Assignment_Questionnaire.Apriori.Apriori.*;
import static Assignment_Questionnaire.ID3.DecisionTreeGenerator.setEditedAttributeList;
import static Assignment_Questionnaire.KMean.ClusterData.createClusterData;
import static Assignment_Questionnaire.KMean.KMeans.KMeansPartition;
import static Assignment_Questionnaire.StudentManager.*;

/**
 * @author Sergiy Isakov
 */
@SuppressWarnings("all")
public class Main {
    public static void main(String[] args) {
        String path;
        ArrayList<Student> students = new ArrayList<>();
        path = System.getProperty("user.dir") + "/src/Assignment_Questionnaire/data/Data Mining - Spring 2018.csv";
        try {
            loadData(path);
            printStudentData();
            students = getStudents();
        } catch (Exception e) {
            System.out.println("Wrong file type of path");
            System.exit(0);
        }

        // Apriori
        int supportThreshold = 7;
        double confidenceLimit = 100;
        Class definingClass = null;
        Class definedClass = Degree.class;
        createTransactions(students, Degree.class, ReasonTakeCourse.class,
                Topic_DED.class, Topic_SPS.class, Topic_UDMT.class, Topic_CDMA.class, Topic_CPM.class, Topic_DGS.class, Topic_SPG.class,
                Topic_SPI.class, Topic_SPSQ.class, Topic_SPT.class, Topic_VD.class);
        apriori(supportThreshold, false);
        associationRules(confidenceLimit, definingClass, definedClass);


        // Clustering KMean
        KMeansPartition(3, createClusterData(students, Degree.class, GamesPlayed.class, FavoriteGame.class, Topic_DED.class, Topic_CDMA.class,
                Topic_CPM.class, Topic_DGS.class, Topic_SPG.class, Topic_SPI.class), true);

        // ID3
        Algorithm id3 = new Algorithm(students);
        ArrayList<Object> attributeList = Student.getAttributeList();
        id3.attributeSelection(attributeList, Degree.class, true);
        Node<Object> node = DecisionTreeGenerator.generateDecisionTree(students, attributeList, Degree.class);
        new DecisionTree(node, "Students").run();

        // next try: remove undesirable attributes
        attributeList = Student.getAttributeList();
        Object[] asd = {Age.class, Gender.class, ShoeSize.class, Height.class, Therb.class, Number.class, FavoriteFilm.class,
                TVShow.class, FavoriteGame.class, Row.class, Seat.class};
        attributeList.removeAll(Arrays.asList(asd));
        setEditedAttributeList(attributeList);
        id3.attributeSelection(attributeList, Degree.class, true);
        Node<Object> nodeX = DecisionTreeGenerator.generateDecisionTree(students, attributeList, Degree.class);
        new DecisionTree(nodeX, "Students").run();

    }
}