package Assignment_Questionnaire;


import Assignment_Questionnaire.GUIutils.Listener;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

import static Assignment_Questionnaire.GUIutils.MenuHelper.*;

/**
 * @author Sergiy Isakov
 */
public class View extends JFrame implements Assignment_Questionnaire.interfaces.View {

    private Listener listener;

    private JPanel mainPanel = new JPanel(new BorderLayout(2, 2));
    private JPanel panel = new JPanel();
    private JPanel leftPanel = new JPanel();
    private JPanel topPanel = new JPanel();

    private JButton leftButton;

    private GridBagConstraints gbc = new GridBagConstraints();

    public View() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        init();
        initInstances();
        initItems();
        initMenuBar();

        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        View view = new View();
    }

    public void init() {
        setTitle("Data Mining Tool");
        setPreferredSize(new Dimension(500, 300));
        setMinimumSize(new Dimension(500, 300));
        setResizable(false);
        setLayout(new FlowLayout());
        mainPanel.setBorder(new TitledBorder("BorderLayout"));
        add(mainPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void initInstances() {
        listener = new Listener(this);
        leftButton = new JButton("Print data");
    }

    public void initMenuBar() {
        JMenuBar jMenuBar = new JMenuBar();
        setJMenuBar(jMenuBar);
        initFileMenu(listener, jMenuBar);
        initToolsMenu(listener, jMenuBar);
        initHelpMenu(listener, jMenuBar);
    }

    public void initItems() {

        leftPanel.setLayout(new GridLayout(0, 1, 5, 5));

        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.weighty = 1;

        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        panel.setBackground(new Color(255, 255, 255));
        panel.addMouseListener(listener);

        topPanel.setLayout(new FlowLayout(0, 15, 10));
        topPanel.add(leftButton);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(leftPanel, BorderLayout.EAST);

        leftButton.addActionListener(listener);
    }

}
