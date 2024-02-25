package views;

import javax.swing.*;

import controllers.Chat;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

public class SignUpView extends JFrame {
    public JTextField usernameField;
    public JTextField emailField;
    public JPasswordField passwordField;
    public JPasswordField confirmPasswordField;

    public SignUpView() {
        super("Sign Up");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 350);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBackground(Color.WHITE);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(getLabelFont());
        usernameField = new JTextField();
        formPanel.add(usernameLabel);
        formPanel.add(usernameField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(getLabelFont());
        emailField = new JTextField();
        formPanel.add(emailLabel);
        formPanel.add(emailField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(getLabelFont());
        passwordField = new JPasswordField();
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);

        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setFont(getLabelFont());
        confirmPasswordField = new JPasswordField();
        formPanel.add(confirmPasswordLabel);
        formPanel.add(confirmPasswordField);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setFont(getButtonFont());
        signUpButton.setBackground(Color.ORANGE);
        signUpButton.setForeground(Color.WHITE);
        signUpButton.addActionListener(new Chat(this));

        JButton loginNowButton = new JButton("Login Now");
        loginNowButton.setFont(getButtonFont());
        loginNowButton.setBackground(new Color(135, 206, 235));
        loginNowButton.setForeground(Color.WHITE);
        loginNowButton.addActionListener(new Chat(this));

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(signUpButton);
        buttonPanel.add(loginNowButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        setVisible(true);
    }

    private Font getLabelFont() {
        Font font = new Font("Arial", Font.BOLD, 14);
        Map<TextAttribute, Object> attributes = new HashMap<>();
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        return font.deriveFont(attributes);
    }

    private Font getButtonFont() {
        return new Font("Arial", Font.BOLD, 16);
    }


}
