
package screens;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class InstructionsPanel extends JPanel {

    private final Color navy = new Color(18, 27, 52);
    private final Color blue = new Color(76, 110, 245);
    private final Color cardColor = new Color(29, 40, 69);
    private final Color muted = new Color(190, 200, 225);

    public InstructionsPanel(ActionListener startAction) {

        setLayout(new GridBagLayout());
        setBackground(navy);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(cardColor);
        content.setBorder(new EmptyBorder(28, 35, 28, 35));
        content.setPreferredSize(new Dimension(590, 510));

        JLabel brand = new JLabel("✦  QUIZ MASTER");
        brand.setFont(new Font("Arial", Font.BOLD, 14));
        brand.setForeground(new Color(165, 180, 252));
        brand.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title = new JLabel("Quiz Instructions");
        title.setFont(new Font("Arial", Font.BOLD, 29));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel(
                "Read these instructions before you begin."
        );
        subtitle.setFont(new Font("Arial", Font.PLAIN, 13));
        subtitle.setForeground(muted);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        content.add(brand);
        content.add(Box.createVerticalStrut(12));
        content.add(title);
        content.add(Box.createVerticalStrut(7));
        content.add(subtitle);
        content.add(Box.createVerticalStrut(22));

        // Quiz summary cards
        JPanel summary = new JPanel(new GridLayout(1, 3, 10, 0));
        summary.setOpaque(false);
        summary.setMaximumSize(new Dimension(520, 75));

        summary.add(createSummaryCard("05", "Questions"));
        summary.add(createSummaryCard("60 sec", "Time limit"));
        summary.add(createSummaryCard("1–4", "Answer options"));

        content.add(summary);
        content.add(Box.createVerticalStrut(22));

        JLabel rulesTitle = new JLabel("Before you start");
        rulesTitle.setFont(new Font("Arial", Font.BOLD, 17));
        rulesTitle.setForeground(Color.WHITE);
        rulesTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(rulesTitle);
        content.add(Box.createVerticalStrut(12));

        addRule(content, "01", "Read each question carefully.");
        addRule(content, "02", "Choose one answer from the four options.");
        addRule(content, "03", "Answer each question before moving forward.");
        addRule(content, "04", "Your score is calculated from correct answers.");
        addRule(content, "05", "The quiz submits automatically when time ends.");

        content.add(Box.createVerticalStrut(20));

        JLabel note = new JLabel(
                "<html><div style='text-align:center;'>"
                + "Make every second count. Good luck!"
                + "</div></html>"
        );
        note.setFont(new Font("Arial", Font.ITALIC, 13));
        note.setForeground(new Color(165, 180, 252));
        note.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton startButton = new JButton("Start Quiz  →");
        UIStyle.stylePrimaryButton(startButton);
        startButton.addActionListener(startAction);
        startButton.setFont(new Font("Arial", Font.BOLD, 15));
        startButton.setForeground(Color.WHITE);
        startButton.setBackground(blue);
        startButton.setFocusPainted(false);
        startButton.setBorder(
                BorderFactory.createEmptyBorder(12, 25, 12, 25)
        );
        startButton.setCursor(
                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
        );
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setMaximumSize(new Dimension(260, 46));
        startButton.setPreferredSize(new Dimension(260, 46));
        startButton.addActionListener(startAction);

        content.add(note);
        content.add(Box.createVerticalStrut(15));
        content.add(startButton);

        add(content);
    }

    private JPanel createSummaryCard(String value, String label) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(39, 50, 84));
        panel.setBorder(new EmptyBorder(10, 5, 10, 5));

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 19));
        valueLabel.setForeground(new Color(165, 180, 252));
        valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel textLabel = new JLabel(label);
        textLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        textLabel.setForeground(muted);
        textLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(valueLabel);
        panel.add(Box.createVerticalStrut(4));
        panel.add(textLabel);

        return panel;
    }

    private void addRule(JPanel parent, String number, String text) {
        JPanel row = new JPanel(new BorderLayout(12, 0));
        row.setOpaque(false);
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        row.setMaximumSize(new Dimension(520, 30));

        JLabel numberLabel = new JLabel(number);
        numberLabel.setFont(new Font("Arial", Font.BOLD, 12));
        numberLabel.setForeground(new Color(165, 180, 252));
        numberLabel.setPreferredSize(new Dimension(28, 25));

        JLabel textLabel = new JLabel(text);
        textLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        textLabel.setForeground(muted);

        row.add(numberLabel, BorderLayout.WEST);
        row.add(textLabel, BorderLayout.CENTER);

        parent.add(row);
        parent.add(Box.createVerticalStrut(7));
    }
}
