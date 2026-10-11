
package screens;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel {

    private final Color navy = new Color(18, 27, 52);
    private final Color blue = new Color(76, 110, 245);
    private final Color purple = new Color(139, 92, 246);
    private final Color cardColor = new Color(29, 40, 69);
    private final Color fieldColor = new Color(39, 50, 84);
    private final Color muted = new Color(190, 200, 225);

    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JCheckBox showPassword;

    public LoginPanel(
            ActionListener loginAction,
            ActionListener signupAction
    ) {
        setLayout(new GridBagLayout());
        setBackground(navy);

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(cardColor);
        card.setBorder(new EmptyBorder(30, 38, 30, 38));
        card.setPreferredSize(new Dimension(430, 510));

        // Brand
        JLabel brand = new JLabel("✦  QUIZ MASTER");
        brand.setFont(new Font("Arial", Font.BOLD, 15));
        brand.setForeground(new Color(165, 180, 252));
        brand.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title = new JLabel("Welcome Back!");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Login to continue your quiz journey");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 13));
        subtitle.setForeground(muted);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Username
        JLabel usernameLabel = createLabel("Username");
        usernameField = new JTextField();
        styleField(usernameField);

        // Password
        JLabel passwordLabel = createLabel("Password");
        passwordField = new JPasswordField();
        styleField(passwordField);

        // Show password
        showPassword = new JCheckBox("Show password");
        showPassword.setFont(new Font("Arial", Font.PLAIN, 12));
        showPassword.setForeground(muted);
        showPassword.setOpaque(false);
        showPassword.setFocusPainted(false);
        showPassword.setAlignmentX(Component.LEFT_ALIGNMENT);

        char defaultEcho = passwordField.getEchoChar();

        showPassword.addActionListener(e -> {
            if (showPassword.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar(defaultEcho);
            }
        });

        // Login button
        JButton loginButton = new JButton("Login  →");
        styleButton(loginButton, true);
        loginButton.addActionListener(e -> {
            if (usernameField.getText().trim().isEmpty()
                    || passwordField.getPassword().length == 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "Please enter your username and password.",
                        "Missing Information",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            loginAction.actionPerformed(
             new java.awt.event.ActionEvent(
                this,
                java.awt.event.ActionEvent.ACTION_PERFORMED,
                usernameField.getText().trim()
                  + "|"
                  + new String(passwordField.getPassword())
    )
);
        });

        // Sign up navigation
        JPanel signupRow = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 5, 0)
        );
        signupRow.setOpaque(false);
        signupRow.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel signupText = new JLabel("Don't have an account?");
        signupText.setFont(new Font("Arial", Font.PLAIN, 12));
        signupText.setForeground(muted);

        JButton signupButton = new JButton("Sign Up");
        signupButton.setFont(new Font("Arial", Font.BOLD, 12));
        signupButton.setForeground(new Color(165, 180, 252));
        signupButton.setBorderPainted(false);
        signupButton.setContentAreaFilled(false);
        signupButton.setFocusPainted(false);
        signupButton.setCursor(
                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
        );
        signupButton.addActionListener(signupAction);

        signupRow.add(signupText);
        signupRow.add(signupButton);

        // Assemble screen
        card.add(brand);
        card.add(Box.createVerticalStrut(20));
        card.add(title);
        card.add(Box.createVerticalStrut(8));
        card.add(subtitle);
        card.add(Box.createVerticalStrut(28));

        card.add(usernameLabel);
        card.add(Box.createVerticalStrut(7));
        card.add(usernameField);
        card.add(Box.createVerticalStrut(18));

        card.add(passwordLabel);
        card.add(Box.createVerticalStrut(7));
        card.add(passwordField);
        card.add(Box.createVerticalStrut(8));
        card.add(showPassword);
        card.add(Box.createVerticalStrut(22));

        card.add(loginButton);
        card.add(Box.createVerticalStrut(18));
        card.add(signupRow);

        add(card);

        // Press Enter to log in
        passwordField.addActionListener(e -> loginButton.doClick());
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 13));
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private void styleField(JTextField field) {
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setBackground(fieldColor);
        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(72, 85, 125)
                        ),
                        new EmptyBorder(10, 12, 10, 12)
                )
        );
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 43));
        field.setPreferredSize(new Dimension(350, 43));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    private void styleButton(JButton button, boolean primary) {
        button.setFont(new Font("Arial", Font.BOLD, 15));
        button.setForeground(Color.WHITE);
        button.setBackground(primary ? blue : purple);
        button.setFocusPainted(false);
        button.setBorder(
                BorderFactory.createEmptyBorder(12, 20, 12, 20)
        );
        button.setCursor(
                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
        );
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));
        button.setPreferredSize(new Dimension(350, 46));
    }
}
