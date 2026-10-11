
package screens;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public final class UIStyle {

    public static final Color NAVY =
            new Color(18, 27, 52);

    public static final Color BLUE =
            new Color(76, 110, 245);

    public static final Color PURPLE =
            new Color(139, 92, 246);

    public static final Color CARD =
            new Color(29, 40, 69);

    public static final Color FIELD =
            new Color(39, 50, 84);

    public static final Color MUTED =
            new Color(190, 200, 225);

    private UIStyle() {
        // Utility class
    }

    public static void stylePrimaryButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(BLUE);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setBorder(
                new EmptyBorder(12, 22, 12, 22)
        );
        button.setCursor(
                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
        );

        Color normalColor = BLUE;
        Color hoverColor = new Color(95, 126, 255);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (button.isEnabled()) {
                    button.setBackground(hoverColor);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(normalColor);
            }
        });
    }

    public static void styleSecondaryButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setForeground(Color.WHITE);
        button.setBackground(FIELD);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(72, 85, 125)
                        ),
                        new EmptyBorder(10, 18, 10, 18)
                )
        );
        button.setCursor(
                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
        );

        Color normalColor = FIELD;
        Color hoverColor = new Color(55, 70, 112);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (button.isEnabled()) {
                    button.setBackground(hoverColor);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(normalColor);
            }
        });
    }

    public static void styleCard(JPanel panel) {
        panel.setBackground(CARD);
        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(65, 79, 119)
                        ),
                        new EmptyBorder(18, 18, 18, 18)
                )
        );
    }
}
