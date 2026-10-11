
package screens;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class HomePanel extends JPanel {

    private final Color navy = new Color(18, 27, 52);
    private final Color blue = new Color(76, 110, 245);
    private final Color purple = new Color(139, 92, 246);
    private final Color muted = new Color(190, 200, 225);

    public HomePanel(
            ActionListener loginAction,
            ActionListener signupAction
    ) {
        setLayout(new GridBagLayout());
        setBackground(navy);

        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(new EmptyBorder(25, 35, 25, 35));

        // Top brand
        JLabel brand = new JLabel("✦  QUIZ MASTER");
        brand.setFont(new Font("Arial", Font.BOLD, 18));
        brand.setForeground(Color.WHITE);
        brand.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel badge = new JLabel("YOUR KNOWLEDGE. YOUR CHALLENGE.");
        badge.setFont(new Font("Arial", Font.BOLD, 11));
        badge.setForeground(new Color(165, 180, 252));
        badge.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Main title
        JLabel title = new JLabel(
                "<html><div style='text-align:center;'>"
                + "Challenge Your Mind.<br>"
                + "<font color='#8B5CF6'>Discover Your Potential.</font>"
                + "</div></html>"
        );
        title.setFont(new Font("Arial", Font.BOLD, 32));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel description = new JLabel(
                "<html><div style='text-align:center;'>"
                + "Practice your skills, test your knowledge, and<br>"
                + "see how much you can achieve."
                + "</div></html>"
        );
        description.setFont(new Font("Arial", Font.PLAIN, 15));
        description.setForeground(muted);
        description.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Topic chips
        JPanel topics = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 5));
        topics.setOpaque(false);

        topics.add(createChip("JAVA"));
        topics.add(createChip("SQL"));
        topics.add(createChip("PYTHON"));
        topics.add(createChip("DATA STRUCTURES"));

        // Buttons
        JButton loginButton = new JButton("Login  →");
        styleButton(loginButton, true);
        loginButton.addActionListener(loginAction);

        JButton signupButton = new JButton("Create Account");
        styleButton(signupButton, false);
        signupButton.addActionListener(signupAction);

        // Feature cards
        JPanel features = new JPanel(new GridLayout(1, 3, 12, 0));
        features.setOpaque(false);
        features.setMaximumSize(new Dimension(570, 95));

        features.add(createFeature("◷", "Timed Quizzes"));
        features.add(createFeature("✧", "Random Questions"));
        features.add(createFeature("✓", "Review Answers"));

        content.add(brand);
        content.add(Box.createVerticalStrut(24));
        content.add(badge);
        content.add(Box.createVerticalStrut(14));
        content.add(title);
        content.add(Box.createVerticalStrut(16));
        content.add(description);
        content.add(Box.createVerticalStrut(20));
        content.add(topics);
        content.add(Box.createVerticalStrut(22));
        content.add(loginButton);
        content.add(Box.createVerticalStrut(10));
        content.add(signupButton);
        content.add(Box.createVerticalStrut(25));
        content.add(features);

        add(content);
    }

    private JLabel createChip(String text) {
        JLabel chip = new JLabel("  " + text + "  ");
        chip.setFont(new Font("Arial", Font.BOLD, 11));
        chip.setForeground(new Color(220, 225, 255));
        chip.setOpaque(true);
        chip.setBackground(new Color(39, 50, 84));
        chip.setBorder(new EmptyBorder(8, 5, 8, 5));
        return chip;
    }

    private JPanel createFeature(String symbol, String text) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(29, 40, 69));
        panel.setBorder(new EmptyBorder(12, 5, 12, 5));

        JLabel icon = new JLabel(symbol);
        icon.setFont(new Font("Arial", Font.BOLD, 22));
        icon.setForeground(new Color(165, 180, 252));
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 11));
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(icon);
        panel.add(Box.createVerticalStrut(5));
        panel.add(label);

        return panel;
    }

    private void styleButton(JButton button, boolean primary) {
        button.setFont(new Font("Arial", Font.BOLD, 15));
        button.setPreferredSize(new Dimension(270, 44));
        button.setMinimumSize(new Dimension(270, 44));
        button.setMaximumSize(new Dimension(270, 44));
        button.setFocusPainted(false);
        button.setCursor(
                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
        );
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        if (primary) {
            button.setBackground(blue);
            button.setForeground(Color.WHITE);
            button.setBorder(
                    BorderFactory.createEmptyBorder(10, 20, 10, 20)
            );
        } else {
            button.setBackground(new Color(39, 50, 84));
            button.setForeground(Color.WHITE);
            button.setBorder(
                    BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(
                                    new Color(72, 85, 125)
                            ),
                            BorderFactory.createEmptyBorder(
                                    9, 19, 9, 19
                            )
                    )
            );
        }
    }
}
