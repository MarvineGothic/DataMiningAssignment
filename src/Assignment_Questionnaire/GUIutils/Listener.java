package Assignment_Questionnaire.GUIutils;

import Assignment_Questionnaire.interfaces.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static Assignment_Questionnaire.GUIutils.MenuController.openFile;
import static Assignment_Questionnaire.GUIutils.PopUp.*;
import static Assignment_Questionnaire.StudentManager.printStudentData;

/**
 * @author Sergiy Isakov
 */
public class Listener implements ActionListener, MouseListener {
    private static View view;
    private String buttonName = "";

    public Listener(View view) {
        Listener.view = view;
    }

    public static View getView() {
        return view;
    }

    // ActionListener
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String action = actionEvent.getActionCommand();
        switch (action) {
            // menu tab
            case "Open File":
                openFile(view);
                break;
            case "Apriori":
                aprioriPOP();
                break;
            case "KMean":
                clusteringPOP();
                break;
            case "ID3":
                id3algorithmPOP();
                break;
            case "Decision Tree":
                System.out.println("Decision Tree");
                break;
            case "Exit":
                System.exit(0);
                break;
            case "About":
                MenuHelper.showDialog(null, "Data Mining Tool\nby Sergiy Isakov", "About");
                break;
            // buttons on window
            case "Print data":
                try {
                    printStudentData();
                } catch (Exception e) {
                    openFile(view);
                    printStudentData();
                }
                break;

        }

    }

    // MouseListener
    @Override
    public void mouseClicked(MouseEvent e) {
        int mod = e.getModifiers();
        String eName = e.getSource().getClass().getSimpleName();
        switch (mod) {
            case 16:

                break;
            case 4:
                switch (eName) {
                    case "JPanel":
                        //view.getPanelPopUp().show(e.getComponent(), e.getX(), e.getY());
                        break;
                    case "JButton":
                        //view.getRecipePopUp().show(e.getComponent(), e.getX(), e.getY());
                        buttonName = ((JButton) e.getSource()).getText();
                        break;
                }
                break;

        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
