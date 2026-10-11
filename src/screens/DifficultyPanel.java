
package screens;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DifficultyPanel extends JPanel {

    private String selectedDifficulty;
    private final ActionListener continueAction;
    private final JButton continueButton;
    private final JLabel selectedLabel;

    private final Color navy = new Color(18, 27, 52);
    private final Color blue = new Color(76, 110, 245);
    private final Color cardColor = new Color(29, 40, 69);
    private final Color muted = new Color(190, 200, 225);
    private final Color borderColor = new Color(65, 79, 119);

    public DifficultyPanel(ActionListener continueAction) {
        this.continueAction = continueAction;

        setLayout(new BorderLayout(15, 15));
        setBackground(navy);
        setBorder(new EmptyBorder(30, 35, 25, 35));

        // Heading
        JPanel heading = new JPanel();
        heading.setOpaque(false);
        heading.setLayout(new BoxLayout(heading, BoxLayout.Y_AXIS));

        JLabel brand = new JLabel("✦  QUIZ MASTER");
        brand.setFont(new Font("Arial", Font.BOLD, 13));
        brand.setForeground(new Color(165, 180, 252));
        brand.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title = new JLabel("Choose Your Difficulty");
        title.setFont(new Font("Arial", Font.BOLD, 29));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel(
                "Choose the level that matches your confidence."
        );
        subtitle.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitle.setForeground(muted);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        heading.add(brand);
        heading.add(Box.createVerticalStrut(12));
        heading.add(title);
        heading.add(Box.createVerticalStrut(8));
        heading.add(subtitle);

        add(heading, BorderLayout.NORTH);

        // Difficulty cards
        JPanel cards = new JPanel(new GridLayout(1, 3, 16, 16));
        cards.setOpaque(false);
        cards.setBorder(new EmptyBorder(45, 5, 45, 5));

        addDifficultyCard(
                cards, "Easy", "●", "Beginner friendly",
                "Build your confidence",
                new Color(52, 211, 153)
        );

        addDifficultyCard(
                cards, "Medium", "◆", "Moderate challenge",
                "Test your understanding",
                new Color(251, 191, 36)
        );

        addDifficultyCard(
                cards, "Hard", "▲", "Advanced questions",
                "Challenge your skills",
                new Color(248, 113, 113)
        );

        add(cards, BorderLayout.CENTER);

        // Bottom section
        JPanel bottom = new JPanel();
        bottom.setOpaque(false);
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));

        selectedLabel = new JLabel("Selected difficulty: None");
        selectedLabel.setFont(new Font("Arial", Font.BOLD, 13));
        selectedLabel.setForeground(muted);
        selectedLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        continueButton = new JButton("Continue  →");
        UIStyle.stylePrimaryButton(continueButton);
        continueButton.setFont(new Font("Arial", Font.BOLD, 15));
        continueButton.setForeground(Color.WHITE);
        continueButton.setBackground(blue);
        continueButton.setFocusPainted(false);
        continueButton.setBorder(
                BorderFactory.createEmptyBorder(12, 25, 12, 25)
        );
        continueButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        continueButton.setMaximumSize(new Dimension(240, 46));
        continueButton.setPreferredSize(new Dimension(240, 46));
        continueButton.setCursor(
                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
        );
        continueButton.setEnabled(false);

        continueButton.addActionListener(e -> {
            if (selectedDifficulty != null) {
                continueAction.actionPerformed(
                        new ActionEvent(
                                this,
                                ActionEvent.ACTION_PERFORMED,
                                selectedDifficulty
                        )
                );
            }
        });

        bottom.add(selectedLabel);
        bottom.add(Box.createVerticalStrut(12));
        bottom.add(continueButton);

        add(bottom, BorderLayout.SOUTH);
    }

    private void addDifficultyCard(
            JPanel parent,
            String difficulty,
            String symbol,
            String heading,
            String description,
            Color accent
    ) {
        JButton card = new JButton();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(cardColor);
        card.setFocusPainted(false);
        card.setCursor(
                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
        );
        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(borderColor, 1),
                        new EmptyBorder(20, 10, 20, 10)
                )
        );

        JLabel symbolLabel = new JLabel(symbol);
        symbolLabel.setFont(new Font("Arial", Font.BOLD, 30));
        symbolLabel.setForeground(accent);
        symbolLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel nameLabel = new JLabel(difficulty);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 22));
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel headingLabel = new JLabel(heading);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 12));
        headingLabel.setForeground(accent);
        headingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel descriptionLabel = new JLabel(
                "<html><div style='text-align:center;'>"
                        + description
                        + "</div></html>"
        );
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        descriptionLabel.setForeground(muted);
        descriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(Box.createVerticalGlue());
        card.add(symbolLabel);
        card.add(Box.createVerticalStrut(15));
        card.add(nameLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(headingLabel);
        card.add(Box.createVerticalStrut(8));
        card.add(descriptionLabel);
        card.add(Box.createVerticalGlue());

        card.addActionListener(e -> {
            selectedDifficulty = difficulty;
            selectedLabel.setText(
                    "Selected difficulty: " + selectedDifficulty
            );
            selectedLabel.setForeground(accent);
            continueButton.setEnabled(true);

            for (Component component : parent.getComponents()) {
                if (component instanceof JButton) {
                    JButton other = (JButton) component;
                    other.setBackground(cardColor);
                    other.setBorder(
                            BorderFactory.createCompoundBorder(
                                    BorderFactory.createLineBorder(
                                            borderColor, 1
                                    ),
                                    new EmptyBorder(20, 10, 20, 10)
                            )
                    );
                }
            }

            card.setBackground(new Color(39, 50, 84));
            card.setBorder(
                    BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(accent, 2),
                            new EmptyBorder(20, 10, 20, 10)
                    )
            );
        });

        parent.add(card);
    }
}
