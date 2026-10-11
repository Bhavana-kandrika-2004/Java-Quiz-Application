
package screens;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class ResultPanel extends JPanel {

    private final Color navy = new Color(18, 27, 52);
    private final Color cardColor = new Color(29, 40, 69);
    private final Color blue = new Color(76, 110, 245);
    private final Color muted = new Color(190, 200, 225);
    private final Color white = Color.WHITE;

    public ResultPanel(
            int score,
            int totalQuestions,
            ActionListener homeAction,
            ActionListener reviewAction
    ) {
        setLayout(new GridBagLayout());
        setBackground(navy);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(cardColor);
        card.setBorder(new EmptyBorder(35, 35, 35, 35));
        card.setPreferredSize(new Dimension(520, 490));

        double percentage = totalQuestions > 0
                ? (double) score / totalQuestions * 100
                : 0;

        // Title
        JLabel title = new JLabel("Quiz Completed!");
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setForeground(white);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Here is your performance summary");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitle.setForeground(muted);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Score
        JLabel scoreLabel = new JLabel(
                score + " / " + totalQuestions
        );
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 54));
        scoreLabel.setForeground(blue);
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel scoreCaption = new JLabel("YOUR SCORE");
        scoreCaption.setFont(new Font("Arial", Font.BOLD, 12));
        scoreCaption.setForeground(muted);
        scoreCaption.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Percentage
        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setValue((int) Math.round(percentage));
        progressBar.setStringPainted(true);
        progressBar.setString(String.format("%.0f%%", percentage));
        progressBar.setForeground(blue);
        progressBar.setBackground(navy);
        progressBar.setBorderPainted(false);
        progressBar.setPreferredSize(new Dimension(350, 24));
        progressBar.setMaximumSize(new Dimension(350, 24));
        progressBar.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Performance message
        String message;
        if (percentage >= 80) {
            message = "Excellent! Outstanding work!";
        } else if (percentage >= 60) {
            message = "Good job! Keep it up!";
        } else if (percentage >= 40) {
            message = "Nice effort! Keep practicing!";
        } else {
            message = "Every attempt helps you improve!";
        }

        JLabel messageLabel = new JLabel(message);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 17));
        messageLabel.setForeground(white);
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel encouragement = new JLabel(
                "Review your answers to learn from mistakes."
        );
        encouragement.setFont(new Font("Arial", Font.PLAIN, 12));
        encouragement.setForeground(muted);
        encouragement.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Buttons
        JButton reviewButton = new JButton("Review Answers");
        styleButton(reviewButton, true);
        reviewButton.addActionListener(reviewAction);

        JButton homeButton = new JButton("Back to Home");
        styleButton(homeButton, false);
        homeButton.addActionListener(homeAction);

        // Add components
        card.add(title);
        card.add(Box.createVerticalStrut(8));
        card.add(subtitle);
        card.add(Box.createVerticalStrut(28));
        card.add(scoreLabel);
        card.add(Box.createVerticalStrut(2));
        card.add(scoreCaption);
        card.add(Box.createVerticalStrut(22));
        card.add(progressBar);
        card.add(Box.createVerticalStrut(22));
        card.add(messageLabel);
        card.add(Box.createVerticalStrut(8));
        card.add(encouragement);
        card.add(Box.createVerticalStrut(28));
        card.add(reviewButton);
        card.add(Box.createVerticalStrut(12));
        card.add(homeButton);

        add(card);
    }

    private void styleButton(JButton button, boolean primary) {
        button.setFont(new Font("Arial", Font.BOLD, 15));
        button.setForeground(white);
        button.setBackground(primary ? blue : cardColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        primary ? blue : muted, 1
                ),
                new EmptyBorder(10, 20, 10, 20)
        ));
        button.setCursor(
                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
        );
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(350, 46));
        button.setPreferredSize(new Dimension(350, 46));
    }
}
