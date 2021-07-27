package Assignment_Questionnaire.GUIutils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * @author Sergiy Isakov
 */
public class MenuHelper {

    public static JMenuItem addMenuItem(JMenu parent, String text, ActionListener actionListener) {
        JMenuItem menuItem = new JMenuItem(text);
        menuItem.addActionListener(actionListener);
        parent.add(menuItem);
        return menuItem;
    }

    public static void initFileMenu(ActionListener listener, JMenuBar menuBar) {
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        addMenuItem(fileMenu, "Open File", listener);
        fileMenu.addSeparator();
        addMenuItem(fileMenu, "Exit", listener);
    }
    public static void initToolsMenu(ActionListener listener, JMenuBar menuBar){
        JMenu toolMenu = new JMenu("Tools");
        menuBar.add(toolMenu);
        addMenuItem(toolMenu, "Apriori", listener);
        addMenuItem(toolMenu, "KMean", listener);
        addMenuItem(toolMenu, "ID3", listener);
    }

    public static void initHelpMenu(ActionListener listener, JMenuBar menuBar) {
        JMenu helpMenu = new JMenu("Help");
        menuBar.add(helpMenu);
        addMenuItem(helpMenu, "About", listener);
    }

    public static void showDialog(Component view, String message, String title) {
        JOptionPane.showMessageDialog(view, message,
                title, JOptionPane.INFORMATION_MESSAGE);
    }
}
