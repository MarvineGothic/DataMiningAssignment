package Assignment_Questionnaire.GUIutils;

import Assignment_Questionnaire.interfaces.View;

import javax.swing.*;
import java.awt.*;
import java.io.File;

import static Assignment_Questionnaire.GUIutils.MenuHelper.showDialog;
import static Assignment_Questionnaire.StudentManager.loadData;
import static Assignment_Questionnaire.StudentManager.loadStudents;


/**
 * @author Sergiy Isakov
 */
public class MenuController {
    private static JFileChooser fileChooser;
    private static String path;

    public static void openFile(View view) {
        fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("."));
        if (fileChooser.showOpenDialog((Component) view) == JFileChooser.APPROVE_OPTION) {
            try {
                path = fileChooser.getSelectedFile().toString();
                loadData(path);
                loadStudents();
            } catch (Exception e) {
                showDialog(null, "File or file path is wrong", "File Error");
            }
        }
    }
}
