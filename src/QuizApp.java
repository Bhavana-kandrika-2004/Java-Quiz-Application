
import database.UserDAO;
import model.Question;
import screens.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class QuizApp extends JFrame {

    private final CardLayout cardLayout;
    private final JPanel mainPanel;
    private final UserDAO userDAO;

    private String currentUsername = "";
    private String selectedCategory = "";
    private String selectedDifficulty = "";

    private QuizPanel quizPanel;
    private ResultPanel resultPanel;
    private ReviewPanel reviewPanel;

    private List<Question> quizQuestions = new ArrayList<>();
    private List<Integer> submittedAnswers = new ArrayList<>();

    private int finalScore = 0;
    private int totalQuestions = 0;

    public QuizApp() {
        userDAO = new UserDAO();

        setTitle("Quiz Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setMinimumSize(new Dimension(850, 600));
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        add(mainPanel);

        showHomeScreen();
        setVisible(true);
    }

    private void showScreen(String screenName) {
        cardLayout.show(mainPanel, screenName);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void showHomeScreen() {
        HomePanel homePanel = new HomePanel(
                e -> showLoginScreen(),
                e -> showSignupScreen()
        );

        mainPanel.add(homePanel, "HOME");
        showScreen("HOME");
    }

    private void showLoginScreen() {
        LoginPanel loginPanel = new LoginPanel(
                e -> handleLogin(e),
                e -> showSignupScreen()
        );

        mainPanel.add(loginPanel, "LOGIN");
        showScreen("LOGIN");
    }

    private void showSignupScreen() {
        SignupPanel signupPanel = new SignupPanel(
                e -> handleSignup(e),
                e -> showLoginScreen()
        );

        mainPanel.add(signupPanel, "SIGNUP");
        showScreen("SIGNUP");
    }

    private void handleLogin(ActionEvent e) {
        String[] data = e.getActionCommand().split("\\|", -1);

        if (data.length < 2) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter your username and password.",
                    "Login",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String username = data[0].trim();
        String password = data[1];

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Username and password cannot be empty.",
                    "Login",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try {
            boolean success = userDAO.loginUser(username, password);

            if (success) {
                currentUsername = username;
                showCategoryScreen();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Invalid username or password.",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(
                    this,
                    "Unable to log in. Please check the database connection.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void handleSignup(ActionEvent e) {
        String[] data = e.getActionCommand().split("\\|", -1);

        if (data.length < 3) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter all registration details.",
                    "Sign Up",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String fullName = data[0].trim();
        String username = data[1].trim();
        String password = data[2];

        if (fullName.isEmpty() || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "All fields are required.",
                    "Sign Up",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if (password.length() < 6) {
            JOptionPane.showMessageDialog(
                    this,
                    "Password must contain at least 6 characters.",
                    "Sign Up",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try {
            boolean success = userDAO.registerUser(
                    fullName,
                    username,
                    password
            );

            if (success) {
                JOptionPane.showMessageDialog(
                        this,
                        "Registration successful! Please log in."
                );
                showLoginScreen();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Registration failed. The username may already exist.",
                        "Sign Up Failed",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(
                    this,
                    "Unable to register. Please check the database connection.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void showCategoryScreen() {
        CategoryPanel categoryPanel = new CategoryPanel(e -> {
            selectedCategory = e.getActionCommand().trim();

            if (selectedCategory.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Please select a category."
                );
                return;
            }

            showDifficultyScreen();
        });

        mainPanel.add(categoryPanel, "CATEGORY");
        showScreen("CATEGORY");
    }

    private void showDifficultyScreen() {
        DifficultyPanel difficultyPanel = new DifficultyPanel(e -> {
            selectedDifficulty = e.getActionCommand().trim();

            if (selectedDifficulty.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Please select a difficulty level."
                );
                return;
            }

            showInstructionsScreen();
        });

        mainPanel.add(difficultyPanel, "DIFFICULTY");
        showScreen("DIFFICULTY");
    }

    private void showInstructionsScreen() {
        InstructionsPanel instructionsPanel =
                new InstructionsPanel(e -> startQuiz());

        mainPanel.add(instructionsPanel, "INSTRUCTIONS");
        showScreen("INSTRUCTIONS");
    }

    private void startQuiz() {
        quizPanel = new QuizPanel(
                selectedCategory,
                selectedDifficulty,
                e -> handleQuizSubmission(e)
        );

        mainPanel.add(quizPanel, "QUIZ");
        showScreen("QUIZ");
    }

   
    private void showResultScreen() {
        resultPanel = new ResultPanel(
                finalScore,
                totalQuestions,
                e -> showCategoryScreen(),
                e -> openReviewScreen()
        );

        mainPanel.add(resultPanel, "RESULT");
        showScreen("RESULT");
    }

    private void openReviewScreen() {
        reviewPanel = new ReviewPanel(
                quizQuestions,
                submittedAnswers,
                () -> showScreen("RESULT")
        );

        mainPanel.add(reviewPanel, "REVIEW");
        showScreen("REVIEW");
    }
    
    private void handleQuizSubmission(ActionEvent e) {
    String command = e.getActionCommand();

    if (command == null) {
        JOptionPane.showMessageDialog(
                this,
                "Quiz result was not received.",
                "Quiz Error",
                JOptionPane.ERROR_MESSAGE
        );
        return;
    }

    try {
        String[] values = command.split(":");

        if (values.length == 3 && values[0].equalsIgnoreCase("score")) {
            // Format: score:3:5
            finalScore = Integer.parseInt(values[1]);
            totalQuestions = Integer.parseInt(values[2]);
        } else if (values.length == 2) {
            // Format: 3:5
            finalScore = Integer.parseInt(values[0]);
            totalQuestions = Integer.parseInt(values[1]);
        } else {
            throw new IllegalArgumentException(
                    "Unexpected result format: " + command
            );
        }

        quizQuestions = new ArrayList<>(quizPanel.getQuestions());
        submittedAnswers = new ArrayList<>(quizPanel.getUserAnswers());

        showResultScreen();

    } catch (Exception ex) {
        ex.printStackTrace();

        JOptionPane.showMessageDialog(
                this,
                "Unable to process quiz results:\n" + ex.getMessage(),
                "Quiz Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}


    public static void main(String[] args) {
        SwingUtilities.invokeLater(QuizApp::new);
    }
}
