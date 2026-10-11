
package screens;

import database.QuestionDAO;
import model.Question;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizPanel extends JPanel {

    private static final int QUESTION_COUNT = 5;
    private static final int QUIZ_TIME_SECONDS = 90;

    private final String category;
    private final String difficulty;
    private final ActionListener resultAction;

    private final List<Question> questions = new ArrayList<>();
    private final List<Integer> userAnswers = new ArrayList<>();

    private int currentQuestionIndex = 0;
    private int timeRemaining = QUIZ_TIME_SECONDS;
    private int score = 0;
    private boolean quizSubmitted = false;

    private Timer timer;

    private JLabel titleLabel;
    private JLabel progressLabel;
    private JLabel timerLabel;
    private JLabel questionLabel;
    private JLabel feedbackLabel;
    private JProgressBar progressBar;

    private JRadioButton[] options;
    private ButtonGroup optionGroup;

    private JButton nextButton;
    private JButton submitButton;

    private final Color NAVY = new Color(15, 35, 75);
    private final Color BLUE = new Color(45, 105, 225);
    private final Color BACKGROUND = new Color(244, 247, 252);
    private final Color WHITE = Color.WHITE;
    private final Color TEXT = new Color(35, 48, 70);

    public QuizPanel(
            String category,
            String difficulty,
            ActionListener resultAction
    ) {
        this.category = category;
        this.difficulty = difficulty;
        this.resultAction = resultAction;

        setLayout(new BorderLayout(15, 15));
        setBackground(BACKGROUND);
        setBorder(new EmptyBorder(20, 25, 20, 25));

        loadQuestions();
    }

    private void loadQuestions() {
        try {
            QuestionDAO dao = new QuestionDAO();

            List<Question> fetched =
                    dao.getRandomQuestions(category, difficulty, QUESTION_COUNT);

            if (fetched != null) {
                questions.addAll(fetched);
            }

            System.out.println(
                    "Quiz: " + category + " / " + difficulty
                    + " / loaded: " + questions.size()
            );

            // Require five questions instead of silently starting a shorter quiz.
            if (questions.size() != QUESTION_COUNT) {
                showLoadError(
                        "This quiz needs 5 questions, but only "
                        + questions.size()
                        + " were loaded for "
                        + category + " - " + difficulty
                        + ". Check QuestionDAO.java and your database."
                );
                return;
            }

            for (int i = 0; i < QUESTION_COUNT; i++) {
                userAnswers.add(0);
            }

            buildUI();
            showQuestion();
            startTimer();

        } catch (Exception ex) {
            ex.printStackTrace();
            showLoadError("Unable to load questions: " + ex.getMessage());
        }
    }

    private void showLoadError(String message) {
        removeAll();
        setLayout(new BorderLayout(15, 15));

        JLabel error = new JLabel(
                "<html><div style='text-align:center;'>"
                + "<h2>Quiz could not start</h2>"
                + message
                + "</div></html>",
                SwingConstants.CENTER
        );

        error.setForeground(NAVY);
        error.setFont(new Font("Arial", Font.PLAIN, 16));

        JButton backButton = createButton("Back to Categories", NAVY);
        backButton.addActionListener(e -> fireResultEvent("back"));

        add(error, BorderLayout.CENTER);
        add(backButton, BorderLayout.SOUTH);

        revalidate();
        repaint();
    }

    private void buildUI() {
        removeAll();
        setLayout(new BorderLayout(15, 15));
        setBackground(BACKGROUND);

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BACKGROUND);

        JPanel headings = new JPanel();
        headings.setLayout(new BoxLayout(headings, BoxLayout.Y_AXIS));
        headings.setBackground(BACKGROUND);

        titleLabel = new JLabel(category + " Quiz - " + difficulty);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 21));
        titleLabel.setForeground(NAVY);

        progressLabel = new JLabel();
        progressLabel.setFont(new Font("Arial", Font.BOLD, 12));
        progressLabel.setForeground(BLUE);
        progressLabel.setBorder(new EmptyBorder(10, 0, 0, 0));

        headings.add(titleLabel);
        headings.add(progressLabel);

        timerLabel = new JLabel("Time: " + timeRemaining);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 14));
        timerLabel.setForeground(BLUE);

        header.add(headings, BorderLayout.WEST);
        header.add(timerLabel, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);

        // Question card
        JPanel card = new JPanel(new BorderLayout(20, 25));
        card.setBackground(WHITE);
        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(220, 228, 240)
                        ),
                        new EmptyBorder(22, 22, 22, 22)
                )
        );

        questionLabel = new JLabel();
        questionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        questionLabel.setForeground(TEXT);
        questionLabel.setVerticalAlignment(SwingConstants.TOP);

        JPanel answerPanel = new JPanel();
        answerPanel.setLayout(new BoxLayout(answerPanel, BoxLayout.Y_AXIS));
        answerPanel.setBackground(WHITE);

        options = new JRadioButton[4];
        optionGroup = new ButtonGroup();

        for (int i = 0; i < options.length; i++) {
            options[i] = new JRadioButton();
            options[i].setFont(new Font("Arial", Font.PLAIN, 14));
            options[i].setForeground(TEXT);
            options[i].setBackground(WHITE);
            options[i].setFocusPainted(false);
            optionGroup.add(options[i]);

            final int answerNumber = i + 1;
            options[i].addActionListener(e -> {
                if (!quizSubmitted) {
                    userAnswers.set(currentQuestionIndex, answerNumber);
                    feedbackLabel.setText("Answer saved");
                }
            });

            JPanel row = new JPanel(new BorderLayout());
            row.setBackground(WHITE);
            row.setBorder(new EmptyBorder(10, 0, 10, 0));
            row.add(options[i], BorderLayout.WEST);

            answerPanel.add(row);
        }

        feedbackLabel = new JLabel(" ");
        feedbackLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        feedbackLabel.setForeground(BLUE);

        JPanel content = new JPanel(new BorderLayout(10, 20));
        content.setBackground(WHITE);
        content.add(questionLabel, BorderLayout.NORTH);
        content.add(answerPanel, BorderLayout.CENTER);
        content.add(feedbackLabel, BorderLayout.SOUTH);

        card.add(content, BorderLayout.CENTER);

        progressBar = new JProgressBar(0, QUESTION_COUNT);
        progressBar.setStringPainted(true);
        progressBar.setForeground(BLUE);
        progressBar.setBackground(new Color(225, 232, 244));

        JPanel center = new JPanel(new BorderLayout(10, 12));
        center.setBackground(BACKGROUND);
        center.add(progressBar, BorderLayout.NORTH);
        center.add(card, BorderLayout.CENTER);

        add(center, BorderLayout.CENTER);

        // Buttons
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        buttons.setBackground(BACKGROUND);

        nextButton = createButton("Next Question", BLUE);
        submitButton = createButton("Submit Quiz", NAVY);

        nextButton.addActionListener(e -> nextQuestion());
        submitButton.addActionListener(e -> confirmSubmit());

        buttons.add(nextButton);
        buttons.add(submitButton);

        add(buttons, BorderLayout.SOUTH);

        revalidate();
        repaint();
    }

    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setForeground(WHITE);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(145, 38));
        return button;
    }

    private void showQuestion() {
        if (quizSubmitted) {
            return;
        }

        Question q = questions.get(currentQuestionIndex);

        progressLabel.setText(
                "Question " + (currentQuestionIndex + 1)
                + " of " + QUESTION_COUNT
        );

        progressBar.setValue(currentQuestionIndex + 1);
        progressBar.setString(
                (currentQuestionIndex + 1) + " / " + QUESTION_COUNT
        );

        questionLabel.setText(
                "<html><div style='width:650px;'>"
                + escapeHtml(q.getQuestion())
                + "</div></html>"
        );

        options[0].setText(escapeHtml(q.getOption1()));
        options[1].setText(escapeHtml(q.getOption2()));
        options[2].setText(escapeHtml(q.getOption3()));
        options[3].setText(escapeHtml(q.getOption4()));

        optionGroup.clearSelection();

        int saved = userAnswers.get(currentQuestionIndex);
        if (saved >= 1 && saved <= 4) {
            options[saved - 1].setSelected(true);
        }

        feedbackLabel.setText(" ");

        nextButton.setText(
                currentQuestionIndex == QUESTION_COUNT - 1
                        ? "Finish Quiz"
                        : "Next Question"
        );
    }

    private String escapeHtml(String value) {
        if (value == null) {
            return "";
        }

        return value.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }

    private void nextQuestion() {
        if (quizSubmitted) {
            return;
        }

        saveCurrentAnswer();

        if (currentQuestionIndex < QUESTION_COUNT - 1) {
            currentQuestionIndex++;
            showQuestion();
        } else {
            finishQuiz();
        }
    }

    private void saveCurrentAnswer() {
        if (quizSubmitted || options == null) {
            return;
        }

        for (int i = 0; i < options.length; i++) {
            if (options[i].isSelected()) {
                userAnswers.set(currentQuestionIndex, i + 1);
                return;
            }
        }

        userAnswers.set(currentQuestionIndex, 0);
    }

    private void confirmSubmit() {
        if (quizSubmitted) {
            return;
        }

        int choice = JOptionPane.showConfirmDialog(
                this,
                "Submit your 5-question quiz now?",
                "Confirm Submission",
                JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            finishQuiz();
        }
    }

    private void startTimer() {
        timer = new Timer(1000, e -> {
            if (quizSubmitted) {
                return;
            }

            timeRemaining--;
            timerLabel.setText("Time: " + timeRemaining);

            if (timeRemaining <= 10) {
                timerLabel.setForeground(Color.RED);
            }

            if (timeRemaining <= 0) {
                timer.stop();
                finishQuiz();
            }
        });

        timer.start();
    }

    private void finishQuiz() {
        if (quizSubmitted) {
            return;
        }

        saveCurrentAnswer();
        quizSubmitted = true;

        if (timer != null) {
            timer.stop();
        }

        score = 0;

        for (int i = 0; i < QUESTION_COUNT; i++) {
            int selected = userAnswers.get(i);
            int correct = questions.get(i).getCorrectAnswer();

            if (selected != 0 && selected == correct) {
                score++;
            }
        }

        nextButton.setEnabled(false);
        submitButton.setEnabled(false);

        fireResultEvent("score:" + score + ":" + QUESTION_COUNT);
    }

    private void fireResultEvent(String command) {
        if (resultAction != null) {
            resultAction.actionPerformed(
                    new ActionEvent(
                            this,
                            ActionEvent.ACTION_PERFORMED,
                            command
                    )
            );
        }
    }

    public List<Question> getQuestions() {
        return Collections.unmodifiableList(
                new ArrayList<>(questions)
        );
    }

    public List<Integer> getUserAnswers() {
        return Collections.unmodifiableList(
                new ArrayList<>(userAnswers)
        );
    }

    public int getScore() {
        return score;
    }

    public int getTotalQuestions() {
        return QUESTION_COUNT;
    }
}
