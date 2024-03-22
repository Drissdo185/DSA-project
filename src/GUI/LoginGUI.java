package GUI;
import system.EvaluationSystem;
import system.Teacher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JFrame {
    EvaluationSystem evaluationSystem;
    ManagerGUI managerGUI;
    RegisterGUI registerGUI;
    CreditsGUI creditsGUI;

    private JPanel mainPanel;
    private JTextField institutionalCodeTextField;
    private JPasswordField passwordField;

    public LoginGUI(EvaluationSystem evaluationSystem) {
        this.evaluationSystem = evaluationSystem;
        managerGUI = new ManagerGUI(evaluationSystem, this);
        registerGUI = new RegisterGUI(evaluationSystem, this);
        creditsGUI = new CreditsGUI();

        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 2));

        JLabel institutionalCodeLabel = new JLabel("Institutional Code:");
        institutionalCodeTextField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logIn();
            }
        });

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerTeacher();
            }
        });

        mainPanel.add(institutionalCodeLabel);
        mainPanel.add(institutionalCodeTextField);
        mainPanel.add(passwordLabel);
        mainPanel.add(passwordField);
        mainPanel.add(loginButton);
        mainPanel.add(registerButton);

        add(mainPanel);
        setVisible(true);
    }

    private void logIn() {
        String code = institutionalCodeTextField.getText();
        String password = new String(passwordField.getPassword());

        if (exist(code, password) != null) {
            managerGUI.setVisible(true);
            dispose();
            managerGUI.timer();
            managerGUI.initialize();
        } else {
            JOptionPane.showMessageDialog(this, "Incorrect institutional code and/or password. Please try again.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            institutionalCodeTextField.setText("");
            passwordField.setText("");
        }
    }

    private void registerTeacher() {
        registerGUI.setVisible(true);
        dispose();
    }

    private Teacher exist(String codeToSearch, String passwordToSearch) {
        Teacher logged = null;
        if (evaluationSystem.searchTeacher(codeToSearch) != null && evaluationSystem.searchTeacher(codeToSearch).getPassword().equals(passwordToSearch)) {
            logged = evaluationSystem.searchTeacher(codeToSearch);
            evaluationSystem.setLogged(logged);
        }
        return logged;
    }
}
