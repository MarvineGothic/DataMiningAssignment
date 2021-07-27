package Assignment_Questionnaire.GUIutils;

import Assignment_Questionnaire.DecisionTree.DecisionTree;
import Assignment_Questionnaire.ID3.Algorithm;
import Assignment_Questionnaire.ID3.DecisionTreeGenerator;
import Assignment_Questionnaire.ID3.Node;
import Assignment_Questionnaire.Student;
import Assignment_Questionnaire.StudentManager;
import Assignment_Questionnaire.enums.*;
import Assignment_Questionnaire.enums.Number;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

import static Assignment_Questionnaire.Apriori.Apriori.*;
import static Assignment_Questionnaire.GUIutils.MenuController.openFile;
import static Assignment_Questionnaire.ID3.Algorithm.attributeSelection;
import static Assignment_Questionnaire.ID3.DecisionTreeGenerator.setEditedAttributeList;
import static Assignment_Questionnaire.KMean.ClusterData.createClusterData;
import static Assignment_Questionnaire.KMean.KMeans.KMeansPartition;
import static java.awt.BorderLayout.CENTER;

/**
 * @author Sergiy Isakov
 * <p>
 * Utility class(static) for making popup windows
 */
@SuppressWarnings("all")
class PopUp {
    private static ArrayList<Student> students;
    private static JPanel gui;
    private static JPanel fields;
    private static JPanel guiCenter;


    static void aprioriPOP() {
        gui = new JPanel(new BorderLayout(2, 2));
        fields = new JPanel(new GridLayout(0, 1, 5, 5));
        guiCenter = new JPanel(new BorderLayout(2, 2));
        guiCenter.setBorder(new TitledBorder("Apriori"));

        // adding GUI elements:

        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));
        panel.setBorder(new TitledBorder(""));

        JTextField thresholdField = new JTextField(10);
        JTextField confidenceField = new JTextField(10);

        panel.add(new JLabel("Support treshold"));
        panel.add(thresholdField);
        panel.add(new JLabel("Confidence Limit"));
        panel.add(confidenceField);
        thresholdField.setText("7");
        confidenceField.setText("100");

        ButtonGroup defClassesGroup = new ButtonGroup();
        JRadioButton definingClassRadio = new JRadioButton("Defining Class");
        JRadioButton definedClassRadio = new JRadioButton("Defined Class");
        definedClassRadio.setSelected(true);
        defClassesGroup.add(definingClassRadio);
        defClassesGroup.add(definedClassRadio);

        panel.add(definingClassRadio);
        panel.add(definedClassRadio);

        JComboBox classesCombo = new JComboBox<>(reducedAttributeList(FavoriteFilm.class, FavoriteGame.class, TVShow.class,Age.class,Gender.class,
                ShoeSize.class, Height.class, PhoneOS.class, Therb.class, Number.class, Row.class, Seat.class));
        panel.add(classesCombo);
        classesCombo.setSelectedIndex(4);

        JCheckBox printAll = new JCheckBox("Print all results");
        panel.add(printAll);

        guiCenter.add(panel, CENTER);
        guiCenter.add(fields, BorderLayout.EAST);
        gui.add(guiCenter, CENTER);
        // end of adding GUI elements

        // handle dialog buttons OK and CANCEL
        int dialog = JOptionPane.showConfirmDialog(null, gui, "Apriori", JOptionPane.OK_CANCEL_OPTION);
        if (dialog == JOptionPane.YES_OPTION) {
            Class clazz = null;
            try {
                clazz = Class.forName("Assignment_Questionnaire.enums." + (String) classesCombo.getSelectedItem());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            initStudents();
            // run apriori
            int supportThreshold = Integer.parseInt(thresholdField.getText());
            double confidenceLimit = Double.parseDouble(confidenceField.getText());
            Class definingClass = null;
            Class definedClass = Degree.class;
            boolean print = false;
            if (definingClassRadio.isSelected()) {
                definingClass = clazz;
                definedClass = null;
            } else if (definedClassRadio.isSelected()) {
                definingClass = null;
                definedClass = clazz;
            }
            if (printAll.isSelected()) print = true;

            initApriori();
            createTransactions(students, Degree.class, ReasonTakeCourse.class,
                    Topic_DED.class, Topic_SPS.class, Topic_UDMT.class, Topic_CDMA.class, Topic_CPM.class, Topic_DGS.class, Topic_SPG.class,
                    Topic_SPI.class, Topic_SPSQ.class, Topic_SPT.class, Topic_VD.class);
            apriori(supportThreshold, print);
            associationRules(confidenceLimit, definingClass, definedClass);
        }
    }

    static void clusteringPOP() {
        gui = new JPanel(new BorderLayout(2, 2));
        fields = new JPanel(new GridLayout(0, 1, 5, 5));
        guiCenter = new JPanel(new BorderLayout(2, 2));
        guiCenter.setBorder(new TitledBorder("KMean"));

        // adding GUI elements:
        /*String[] attr = new String[Student.getAttributeList().size()];
        for (int i = 0; i < attr.length; i++) {
            attr[i] = ((Class) Student.getAttributeList().get(i)).getSimpleName();
        }*/

        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));
        panel.setBorder(new TitledBorder(""));

        JTextField clustersNumField = new JTextField(10);
        JComboBox classesCombo = new JComboBox<>(reducedAttributeList(FavoriteFilm.class, FavoriteGame.class, TVShow.class));

        panel.add(new JLabel("Chose number of clusters"));
        panel.add(clustersNumField);
        clustersNumField.setText("3");
        panel.add(new JLabel("Chose cluster class"));
        panel.add(classesCombo);
        classesCombo.setSelectedIndex(4);

        guiCenter.add(panel, CENTER);
        guiCenter.add(fields, BorderLayout.EAST);
        gui.add(guiCenter, CENTER);
        // end of adding GUI elements

        // handle dialog buttons OK and CANCEL
        int dialog = JOptionPane.showConfirmDialog(null, gui, "KMean", JOptionPane.OK_CANCEL_OPTION);

        if (dialog == JOptionPane.YES_OPTION) {
            Class clazz = null;
            try {
                clazz = Class.forName("Assignment_Questionnaire.enums." + (String) classesCombo.getSelectedItem());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            initStudents();
            // run clusteringPOP
            KMeansPartition(Integer.parseInt(clustersNumField.getText()), createClusterData(students, clazz, GamesPlayed.class, Topic_DED.class, Topic_CDMA.class,
                    Topic_CPM.class, Topic_DGS.class, Topic_SPG.class, Topic_SPI.class, FavoriteGame.class), true);
        }
    }

    static void id3algorithmPOP() {
        gui = new JPanel(new BorderLayout(2, 2));
        fields = new JPanel(new GridLayout(0, 1, 5, 5));
        guiCenter = new JPanel(new BorderLayout(2, 2));
        guiCenter.setBorder(new TitledBorder("ID3"));

        // adding GUI elements:

        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));
        panel.setBorder(new TitledBorder(""));

        ButtonGroup defClassesGroup = new ButtonGroup();
        JRadioButton all_attributes = new JRadioButton("All attributes");
        JRadioButton only_relevant = new JRadioButton("Only relevant");
        defClassesGroup.add(all_attributes);
        defClassesGroup.add(only_relevant);
        all_attributes.setSelected(true);

        panel.add(all_attributes);
        panel.add(only_relevant);

        JComboBox classesCombo = new JComboBox<>(reducedAttributeList(FavoriteFilm.class, FavoriteGame.class, TVShow.class));
        JCheckBox tree = new JCheckBox("Decision Tree");
        tree.setSelected(true);
        panel.add(classesCombo);
        classesCombo.setSelectedIndex(4);
        panel.add(tree);


        guiCenter.add(panel, CENTER);
        guiCenter.add(fields, BorderLayout.EAST);
        gui.add(guiCenter, CENTER);
        // end of adding GUI elements

        // handle dialog buttons OK and CANCEL
        int dialog = JOptionPane.showConfirmDialog(null, gui, "ID3", JOptionPane.OK_CANCEL_OPTION);
        if (dialog == JOptionPane.YES_OPTION) {
            initStudents();
            ArrayList<Object> attributeList = Student.getAttributeList();
            // run decision tree generation
            new Algorithm(students);

            try {
                Class clazz = Class.forName("Assignment_Questionnaire.enums." + (String) classesCombo.getSelectedItem());

                if (all_attributes.isSelected()) {
                    attributeSelection(Student.getAttributeList(), clazz, true);
                    if (tree.isSelected()) {
                        Node<Object> node = DecisionTreeGenerator.generateDecisionTree(students, Student.getAttributeList(), clazz);
                        new DecisionTree(node, "Students").run();
                    }
                } else if (only_relevant.isSelected()) {
                    // next try: remove undesirable attributes
                    Object[] asd = {Age.class, Gender.class, ShoeSize.class, Height.class, Therb.class, Number.class, FavoriteFilm.class,
                            TVShow.class, FavoriteGame.class, Row.class, Seat.class};
                    attributeList.removeAll(Arrays.asList(asd));
                    setEditedAttributeList(attributeList);
                    attributeSelection(attributeList, Degree.class, true);
                    if (tree.isSelected()) {
                        Node<Object> nodeX = DecisionTreeGenerator.generateDecisionTree(students, attributeList, Degree.class);
                        new DecisionTree(nodeX, "Students").run();
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private static void initStudents() {
        try {
            students = StudentManager.getStudents();
        } catch (Exception e) {
            openFile(Listener.getView());
            students = StudentManager.getStudents();
        }
    }
    private static String[] reducedAttributeList(Object... toRemove){
        ArrayList<Object> attributes = Student.getAttributeList();
        //Object[] toRemove = {FavoriteFilm.class, FavoriteGame.class, TVShow.class};
        attributes.removeAll(Arrays.asList(toRemove));

        String[] attr = new String[attributes.size()];
        for (int i = 0; i < attr.length; i++) {
            attr[i] = ((Class) attributes.get(i)).getSimpleName();
        }
        return attr;
    }
}
