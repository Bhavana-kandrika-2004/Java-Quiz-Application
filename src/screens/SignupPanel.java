
package screens;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class SignupPanel extends JPanel {

    private final Color navy = new Color(18, 27, 52);
    private final Color blue = new Color(76, 110, 245);
    private final Color cardColor = new Color(29, 40, 69);
    private final Color fieldColor = new Color(39, 50, 84);
    private final Color muted = new Color(190, 200, 225);

    public SignupPanel(ActionListener signupAction,
                       ActionListener loginAction) {

        setLayout(new GridBagLayout());
        setBackground(navy);

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(cardColor);
        card.setBorder(new EmptyBorder(25, 38, 25, 38));
        card.setPreferredSize(new Dimension(440, 540));

        JLabel brand = new JLabel("✦  QUIZ MASTER");
        brand.setFont(new Font("Arial", Font.BOLD, 15));
        brand.setForeground(new Color(165, 180, 252));
        brand.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title = new JLabel("Create Account");
        title.setFont(new Font("Arial", Font.BOLD, 27));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Start your learning journey today");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 13));
        subtitle.setForeground(muted);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField fullNameField = new JTextField();
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JPasswordField confirmPasswordField = new JPasswordField();

        styleField(fullNameField);
        styleField(usernameField);
        styleField(passwordField);
        styleField(confirmPasswordField);

        JButton signupButton = new JButton("Create Account  →");
        styleButton(signupButton);

        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 12));
        loginButton.setForeground(new Color(165, 180, 252));
        loginButton.setBorderPainted(false);
        loginButton.setContentAreaFilled(false);
        loginButton.setFocusPainted(false);
        loginButton.setCursor(
                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
        );
        loginButton.addActionListener(loginAction);

        JPanel loginRow = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 5, 0)
        );
        loginRow.setOpaque(false);

        JLabel loginText = new JLabel("Already have an account?");
        loginText.setForeground(muted);
        loginText.setFont(new Font("Arial", Font.PLAIN, 12));

        loginRow.add(loginText);
        loginRow.add(loginButton);

        card.add(brand);
        card.add(Box.createVerticalStrut(12));
        card.add(title);
        card.add(Box.createVerticalStrut(6));
        card.add(subtitle);
        card.add(Box.createVerticalStrut(20));

        addField(card, "Full Name", fullNameField);
        addField(card, "Username", usernameField);
        addField(card, "Password", passwordField);
        addField(card, "Confirm Password", confirmPasswordField);

        card.add(Box.createVerticalStrut(12));
        card.add(signupButton);
        card.add(Box.createVerticalStrut(12));
        loginRow.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(loginRow);

        signupButton.addActionListener(e -> {
            String fullName = fullNameField.getText().trim();
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            String confirmPassword =
                    new String(confirmPasswordField.getPassword());

            if (fullName.isEmpty() || username.isEmpty()
                    || password.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Please fill in all fields.",
                        "Missing Information",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(
                        this,
                        "Passwords do not match.",
                        "Check Password",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            if (password.length() < 6) {
                JOptionPane.showMessageDialog(
                        this,
                        "Password must contain at least 6 characters.",
                        "Weak Password",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            // Pass the details to the existing registration logic.
            signupAction.actionPerformed(
                    new java.awt.event.ActionEvent(
                            this,
                            java.awt.event.ActionEvent.ACTION_PERFORMED,
                            fullName + "|" + username + "|" + password
                    )
            );
        });

        add(card);
    }

    private void addField(JPanel panel, String labelText,
                          JTextField field) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 12));
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(label);
        panel.add(Box.createVerticalStrut(5));
        panel.add(field);
        panel.add(Box.createVerticalStrut(12));
    }

    private void styleField(JTextField field) {
        field.setFont(new Font("Arial", Font.PLAIN, 13));
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setBackground(fieldColor);
        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(72, 85, 125)
                        ),
                        new EmptyBorder(8, 10, 8, 10)
                )
        );
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        field.setPreferredSize(new Dimension(350, 38));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(blue);
        button.setFocusPainted(false);
        button.setBorder(
                BorderFactory.createEmptyBorder(11, 18, 11, 18)
        );
        button.setCursor(
                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
        );
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        button.setPreferredSize(new Dimension(350, 44));
    }
}
