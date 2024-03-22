package GUI;

import system.Course;
import system.EvaluationSystem;
import system.RankingStudentsTree;
import system.QualifiedStudentsTree;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ManagerGUI extends JFrame {
    private EvaluationSystem evaluationSystem;
    private LoginGUI loginGUI;
    private AssessmentsGUI assessmentsGUI;
    private QuestionnairesGUI questionnairesGUI;
    private ExamsGUI examsGUI;
    private CoursesGUI coursesGUI;
    private QualifiedStudentsTree qualifiedStudentsTree;
    private RankingStudentsTree rankingStudentsTree;

    private JPanel mainPanel;
    private JLabel timeLabel;
    private JLabel welcomeLabel;
    private JComboBox<Course> coursesComboBox;

    public ManagerGUI(EvaluationSystem evaluationSystem, LoginGUI loginGUI) {
        this.evaluationSystem = evaluationSystem;
        this.loginGUI = loginGUI;
        assessmentsGUI = new AssessmentsGUI(evaluationSystem);
        questionnairesGUI = new QuestionnairesGUI(evaluationSystem);
        examsGUI = new ExamsGUI(evaluationSystem);
        coursesGUI = new CoursesGUI(evaluationSystem);
        qualifiedStudentsTree = new QualifiedStudentsTree();
        rankingStudentsTree = new RankingStudentsTree();

        setTitle("ManagerGUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        welcomeLabel = new JLabel("!Bienvenido " + evaluationSystem.getLogged().getName() + "!");
        mainPanel.add(welcomeLabel, BorderLayout.NORTH);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        JButton logOutButton = new JButton("Log Out");
        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logOut();
            }
        });
        controlPanel.add(logOutButton);

        timeLabel = new JLabel();
        controlPanel.add(timeLabel);

        mainPanel.add(controlPanel, BorderLayout.SOUTH);

        coursesComboBox = new JComboBox<>();
        coursesComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCoursesPane();
            }
        });
        mainPanel.add(coursesComboBox, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);

        initializeComboBox();
        timer();
    }

    private void initializeComboBox() {
        coursesComboBox.removeAllItems();
        for (Course course : evaluationSystem.getLogged().getCourses()) {
            coursesComboBox.addItem(course);
        }
    }

    private void logOut() {
        loginGUI.setVisible(true);
        dispose();
    }

    private void showCoursesPane() {
        Course selectedCourse = (Course) coursesComboBox.getSelectedItem();
        if (selectedCourse != null) {
            // Implement the logic to show courses pane
            // You can use a separate JPanel and add it to the mainPanel
            // Example:
            // mainPanel.removeAll();
            // mainPanel.add(new CoursesPanel(selectedCourse)); // CoursesPanel is a Swing JPanel
            // mainPanel.revalidate();
            // mainPanel.repaint();
        }
    }

    private void timer() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                timeLabel.setText(sdf.format(new Date()));
            }
        }, 0, 1000);
    }