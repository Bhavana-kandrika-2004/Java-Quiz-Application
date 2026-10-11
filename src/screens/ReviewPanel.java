
package screens;

import model.Question;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class ReviewPanel extends JPanel {

    private int currentQuestion = 0;

    private final List<Question> questions;
    private final List<Integer> userAnswers;
    private final Runnable backAction;

    private final Color backgroundColor = new Color(17, 27, 53);
    private final Color cardColor = new Color(30, 43, 76);
    private final Color optionColor = new Color(39, 53, 88);
    private final Color blueColor = new Color(76, 110, 255);
    private final Color textColor = new Color(240, 244, 255);
    private final Color mutedColor = new Color(170, 186, 220);
    private final Color greenColor = new Color(72, 201, 145);
    private final Color redColor = new Color(255, 105, 120);

    private JLabel questionNumberLabel;
    private JLabel questionLabel;
    private JLabel yourAnswerLabel;
    private JLabel correctAnswerLabel;
    private JLabel resultLabel;
    private JLabel explanationLabel;
    private JProgressBar progressBar;

    private JButton previousButton;
    private JButton nextButton;
    private JButton backButton;

    public ReviewPanel(
            List<Question> questions,
            List<Integer> userAnswers,
            Runnable backAction
    ) {
        this.questions = questions;
        this.userAnswers = userAnswers;
        this.backAction = backAction;

        setLayout(new BorderLayout(0, 0));
        setBackground(backgroundColor);
        setBorder(new EmptyBorder(20, 24, 18, 24));

        if (questions == null || questions.isEmpty()) {
            showEmptyState();
            return;
        }

        createHeader();
        createContent();
        createFooter();
        showQuestion();
    }

    private void createHeader() {
        JPanel header = new JPanel(new BorderLayout(15, 8));
        header.setBackground(backgroundColor);
        header.setBorder(new EmptyBorder(0, 0, 18, 0));

        JPanel headingPanel = new JPanel();
        headingPanel.setLayout(
                new BoxLayout(headingPanel, BoxLayout.Y_AXIS)
        );
        headingPanel.setBackground(backgroundColor);

        JLabel brandLabel = new JLabel("▪  QUIZ MASTER");
        brandLabel.setFont(new Font("Arial", Font.BOLD, 12));
        brandLabel.setForeground(new Color(150, 180, 255));

        questionNumberLabel = new JLabel();
        questionNumberLabel.setFont(
                new Font("Arial", Font.BOLD, 23)
        );
        questionNumberLabel.setForeground(textColor);

        JLabel subtitle = new JLabel("REVIEW YOUR ANSWERS");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 11));
        subtitle.setForeground(mutedColor);

        headingPanel.add(brandLabel);
        headingPanel.add(Box.createVerticalStrut(10));
        headingPanel.add(questionNumberLabel);
        headingPanel.add(Box.createVerticalStrut(5));
        headingPanel.add(subtitle);

        header.add(headingPanel, BorderLayout.CENTER);

        JPanel progressPanel = new JPanel(
                new BorderLayout(0, 8)
        );
        progressPanel.setBackground(backgroundColor);
        progressPanel.setPreferredSize(new Dimension(250, 45));

        JLabel progressText = new JLabel("REVIEW PROGRESS");
        progressText.setForeground(mutedColor);
        progressText.setFont(new Font("Arial", Font.BOLD, 10));

        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(false);
        progressBar.setForeground(blueColor);
        progressBar.setBackground(new Color(43, 55, 87));
        progressBar.setBorderPainted(false);
        progressBar.setPreferredSize(new Dimension(250, 6));

        progressPanel.add(progressText, BorderLayout.NORTH);
        progressPanel.add(progressBar, BorderLayout.CENTER);

        header.add(progressPanel, BorderLayout.SOUTH);

        add(header, BorderLayout.NORTH);
    }

    private void createContent() {
        JPanel outerPanel = new JPanel(new BorderLayout());
        outerPanel.setBackground(backgroundColor);

        JPanel content = new JPanel();
        content.setLayout(
                new BoxLayout(content, BoxLayout.Y_AXIS)
        );
        content.setBackground(cardColor);
        content.setBorder(
                new EmptyBorder(28, 30, 28, 30)
        );

        JLabel questionHeading = new JLabel("YOUR QUESTION");
        questionHeading.setFont(
                new Font("Arial", Font.BOLD, 11)
        );
        questionHeading.setForeground(
                new Color(150, 180, 255)
        );
        questionHeading.setAlignmentX(Component.LEFT_ALIGNMENT);

        questionLabel = new JLabel();
        questionLabel.setFont(
                new Font("Arial", Font.BOLD, 19)
        );
        questionLabel.setForeground(textColor);
        questionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        content.add(questionHeading);
        content.add(Box.createVerticalStrut(12));
        content.add(questionLabel);
        content.add(Box.createVerticalStrut(28));

        yourAnswerLabel = createInfoLabel();
        correctAnswerLabel = createInfoLabel();

        content.add(createAnswerCard(
                "YOUR ANSWER", yourAnswerLabel
        ));
        content.add(Box.createVerticalStrut(12));

        content.add(createAnswerCard(
                "CORRECT ANSWER", correctAnswerLabel
        ));
        content.add(Box.createVerticalStrut(18));

        resultLabel = new JLabel();
        resultLabel.setFont(
                new Font("Arial", Font.BOLD, 16)
        );
        resultLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        content.add(resultLabel);
        content.add(Box.createVerticalStrut(18));

        JLabel explanationHeading = new JLabel("EXPLANATION");
        explanationHeading.setFont(
                new Font("Arial", Font.BOLD, 11)
        );
        explanationHeading.setForeground(
                new Color(150, 180, 255)
        );
        explanationHeading.setAlignmentX(Component.LEFT_ALIGNMENT);

        explanationLabel = new JLabel();
        explanationLabel.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );
        explanationLabel.setForeground(mutedColor);
        explanationLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        content.add(explanationHeading);
        content.add(Box.createVerticalStrut(8));
        content.add(explanationLabel);

        outerPanel.add(content, BorderLayout.NORTH);
        add(outerPanel, BorderLayout.CENTER);
    }

    private JLabel createInfoLabel() {
        JLabel label = new JLabel();
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setForeground(textColor);
        return label;
    }

    private JPanel createAnswerCard(
            String heading,
            JLabel answerLabel
    ) {
        JPanel card = new JPanel();
        card.setLayout(
                new BoxLayout(card, BoxLayout.Y_AXIS)
        );
        card.setBackground(optionColor);
        card.setBorder(
                new EmptyBorder(14, 16, 14, 16)
        );
        card.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 85)
        );

        JLabel title = new JLabel(heading);
        title.setFont(new Font("Arial", Font.BOLD, 10));
        title.setForeground(mutedColor);

        answerLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(title);
        card.add(Box.createVerticalStrut(8));
        card.add(answerLabel);

        return card;
    }

    private void createFooter() {
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(backgroundColor);
        footer.setBorder(new EmptyBorder(18, 0, 0, 0));

        JPanel navigation = new JPanel(
                new FlowLayout(FlowLayout.RIGHT, 10, 0)
        );
        navigation.setBackground(backgroundColor);

        previousButton = createButton(
                "← Previous", optionColor, textColor
        );
        nextButton = createButton(
                "Next →", blueColor, Color.WHITE
        );
        backButton = createButton(
                "Back to Result", optionColor, textColor
        );

        previousButton.addActionListener(e -> {
            if (currentQuestion > 0) {
                currentQuestion--;
                showQuestion();
            }
        });

        nextButton.addActionListener(e -> {
            if (currentQuestion < questions.size() - 1) {
                currentQuestion++;
                showQuestion();
            }
        });

        backButton.addActionListener(e -> backAction.run());

        navigation.add(previousButton);
        navigation.add(nextButton);
        navigation.add(backButton);

        footer.add(navigation, BorderLayout.EAST);
        add(footer, BorderLayout.SOUTH);
    }

    private JButton createButton(
            String text,
            Color background,
            Color foreground
    ) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBackground(background);
        button.setForeground(foreground);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(
                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
        );
        button.setBorder(
                new EmptyBorder(12, 18, 12, 18)
        );
        return button;
    }

    private void showQuestion() {
        Question question = questions.get(currentQuestion);

        questionNumberLabel.setText(
                "Question " + (currentQuestion + 1)
                        + " of " + questions.size()
        );

        progressBar.setValue(
                (currentQuestion + 1) * 100 / questions.size()
        );

        questionLabel.setText(
                "<html><div style='width:650px'>"
                        + escapeHtml(question.getQuestion())
                        + "</div></html>"
        );

        int userAnswer = 0;

        if (userAnswers != null
                && currentQuestion < userAnswers.size()) {
            userAnswer = userAnswers.get(currentQuestion);
        }

        String yourAnswer = getAnswerText(question, userAnswer);
        String correctAnswer = getAnswerText(
                question, question.getCorrectAnswer()
        );

        yourAnswerLabel.setText(
                "<html><div style='width:650px'>"
                        + escapeHtml(yourAnswer)
                        + "</div></html>"
        );

        correctAnswerLabel.setText(
                "<html><div style='width:650px'>"
                        + escapeHtml(correctAnswer)
                        + "</div></html>"
        );

        if (userAnswer == question.getCorrectAnswer()
                && userAnswer != 0) {
            resultLabel.setText("✓  Correct answer");
            resultLabel.setForeground(greenColor);
        } else {
            resultLabel.setText("✕  Incorrect answer");
            resultLabel.setForeground(redColor);
        }

        String explanation = question.getExplanation();

        if (explanation == null || explanation.trim().isEmpty()) {
            explanation = "No explanation is available for this question.";
        }

        explanationLabel.setText(
                "<html><div style='width:650px'>"
                        + escapeHtml(explanation)
                        + "</div></html>"
        );

        previousButton.setEnabled(currentQuestion > 0);
        nextButton.setEnabled(
                currentQuestion < questions.size() - 1
        );

        nextButton.setText(
                currentQuestion == questions.size() - 1
                        ? "Last Question"
                        : "Next →"
        );
    }

    private String getAnswerText(
            Question question,
            int answer
    ) {
        switch (answer) {
            case 1:
                return question.getOption1();
            case 2:
                return question.getOption2();
            case 3:
                return question.getOption3();
            case 4:
                return question.getOption4();
            default:
                return "Not answered";
        }
    }

    private String escapeHtml(String value) {
        if (value == null) {
            return "";
        }

        return value.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }

    private void showEmptyState() {
        setLayout(new BorderLayout());

        JLabel message = new JLabel(
                "No answers are available to review.",
                SwingConstants.CENTER
        );
        message.setForeground(textColor);
        message.setFont(new Font("Arial", Font.BOLD, 18));

        JButton back = createButton(
                "Back to Result", blueColor, Color.WHITE
        );
        back.addActionListener(e -> backAction.run());

        JPanel footer = new JPanel();
        footer.setBackground(backgroundColor);
        footer.add(back);

        add(message, BorderLayout.CENTER);
        add(footer, BorderLayout.SOUTH);
    }
}
