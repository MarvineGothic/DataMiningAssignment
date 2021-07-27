package Assignment_Questionnaire.DecisionTree;

import Assignment_Questionnaire.ID3.Node;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;

/**
 * From Java library
 * modified by Sergiy Isakov
 */
public class DecisionTree extends JPanel implements TreeSelectionListener {
    private static boolean useSystemLookAndFeel = true;
    private static Node<Object> top;
    private static String label;

    public DecisionTree(Node<Object> top, String label) {
        super(new GridLayout(1, 0));
        DecisionTree.top = top;
        DecisionTree.label = label;
        //Create the nodes.
        top.setLabel(label);
        //Create a tree that allows one selection at a time.
        JTree tree = new JTree(top);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        //Listen for when the selection changes.
        tree.addTreeSelectionListener(this);

        //Create the scroll pane and add the tree to it.
        JScrollPane treeView = new JScrollPane(tree);

        //Add the scroll panes to a split pane.
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setTopComponent(treeView);

        Dimension minimumSize = new Dimension(500, 500);
        treeView.setMinimumSize(minimumSize);
        splitPane.setPreferredSize(new Dimension(500, 600));

        //Add the split pane to this panel.
        add(splitPane);
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI() {
        if (useSystemLookAndFeel) {
            try {
                UIManager.setLookAndFeel(
                        UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                System.err.println("Couldn't use system look and feel.");
            }
        }

        //Create and set up the window.
        JFrame frame = new JFrame("DecisionTree");
        //frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        //Add content to the window.
        frame.add(new DecisionTree(top, label));

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public void run() {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    /**
     * Required by TreeSelectionListener interface.
     */
    public void valueChanged(TreeSelectionEvent e) {
    }
}