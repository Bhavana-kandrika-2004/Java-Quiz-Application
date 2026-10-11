
package screens;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class CategoryPanel extends JPanel {

    private final Color navy = new Color(18, 27, 52);
    private final Color cardColor = new Color(29, 40, 69);
    private final Color blue = new Color(76, 110, 245);
    private final Color purple = new Color(139, 92, 246);
    private final Color muted = new Color(190, 200, 225);
    private final Color white = Color.WHITE;

    private String selectedCategory = null;
    private final JButton continueButton;
    private final JButton[] categoryButtons = new JButton[4];

    private final String[] categories = {
        "Java",
        "SQL",
        "Python",
        "Data Structures"
    };

    public CategoryPanel(ActionListener categoryAction) {

        setLayout(new BorderLayout(0, 20));
        setBackground(navy);
        setBorder(new EmptyBorder(35, 40, 35, 40));

        // Header
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(navy);

        JLabel title = new JLabel("Choose Your Category");
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setForeground(white);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel(
                "Select a topic to begin your quiz"
        );
        subtitle.setFont(new Font("Arial", Font.PLAIN, 15));
        subtitle.setForeground(muted);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        header.add(title);
        header.add(Box.createVerticalStrut(10));
        header.add(subtitle);

        add(header, BorderLayout.NORTH);

        // Category cards
        JPanel categoryGrid = new JPanel(new GridLayout(2, 2, 18, 18));
        categoryGrid.setBackground(navy);
        categoryGrid.setBorder(new EmptyBorder(15, 0, 15, 0));

        String[] descriptions = {
            "Core Java concepts and programming",
            "Queries, joins and database concepts",
            "Python syntax and programming",
            "Algorithms and data structures"
        };

        for (int i = 0; i < categories.length; i++) {

            final String category = categories[i];

            JButton categoryButton = new JButton();
            categoryButtons[i] = categoryButton;

            categoryButton.setLayout(new BorderLayout(0, 10));
            categoryButton.setBackground(cardColor);
            categoryButton.setForeground(white);
            categoryButton.setFocusPainted(false);
            categoryButton.setBorder(
                    BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(
                                    new Color(55, 70, 110), 1
                            ),
                            new EmptyBorder(20, 15, 20, 15)
                    )
            );
            categoryButton.setCursor(
                    Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
            );

            JLabel categoryName = new JLabel(category);
            categoryName.setFont(
                    new Font("Arial", Font.BOLD, 21)
            );
            categoryName.setForeground(white);
            categoryName.setHorizontalAlignment(
                    SwingConstants.CENTER
            );

            JLabel categoryDescription = new JLabel(
                    "<html><div style='text-align:center;width:180px;'>"
                    + descriptions[i]
                    + "</div></html>"
            );
            categoryDescription.setFont(
                    new Font("Arial", Font.PLAIN, 12)
            );
            categoryDescription.setForeground(muted);
            categoryDescription.setHorizontalAlignment(
                    SwingConstants.CENTER
            );

            categoryButton.add(categoryName, BorderLayout.CENTER);
            categoryButton.add(
                    categoryDescription,
                    BorderLayout.SOUTH
            );

            categoryButton.addActionListener(e -> {
                selectedCategory = category;
                updateCategorySelection();
            });

            categoryGrid.add(categoryButton);
        }

        add(categoryGrid, BorderLayout.CENTER);

        // Footer
        JPanel footer = new JPanel();
        footer.setLayout(new BoxLayout(footer, BoxLayout.Y_AXIS));
        footer.setBackground(navy);

        JLabel selectionLabel = new JLabel(
                "Select one category to continue"
        );
        selectionLabel.setFont(
                new Font("Arial", Font.PLAIN, 13)
        );
        selectionLabel.setForeground(muted);
        selectionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Initialize the button before styling or using it.
        continueButton = new JButton("Continue  →");
        UIStyle.stylePrimaryButton(continueButton);
        continueButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        continueButton.setPreferredSize(new Dimension(250, 46));
        continueButton.setMaximumSize(new Dimension(250, 46));
        continueButton.setEnabled(false);

        continueButton.addActionListener(e -> {
            if (selectedCategory != null) {
                categoryAction.actionPerformed(
                        new java.awt.event.ActionEvent(
                                this,
                                java.awt.event.ActionEvent.ACTION_PERFORMED,
                                selectedCategory
                        )
                );
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Please select a category first."
                );
            }
        });

        footer.add(selectionLabel);
        footer.add(Box.createVerticalStrut(12));
        footer.add(continueButton);

        add(footer, BorderLayout.SOUTH);
    }

    private void updateCategorySelection() {

        for (int i = 0; i < categoryButtons.length; i++) {

            JButton button = categoryButtons[i];

            if (categories[i].equals(selectedCategory)) {
                button.setBackground(new Color(39, 50, 84));
                button.setBorder(
                        BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(
                                        blue, 3
                                ),
                                new EmptyBorder(18, 13, 18, 13)
                        )
                );
            } else {
                button.setBackground(cardColor);
                button.setBorder(
                        BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(
                                        new Color(55, 70, 110), 1
                                ),
                                new EmptyBorder(20, 15, 20, 15)
                        )
                );
            }
        }

        continueButton.setEnabled(selectedCategory != null);
        repaint();
    }
}
