
package database;

import model.Question;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAO {

    public List<Question> getRandomQuestions(
            String category,
            String difficulty,
            int limit) {

        List<Question> questions = new ArrayList<>();

        // Select one database row for each unique question first.
        // Then randomly select up to the requested number.
        String sql =
                "SELECT q.id, q.category, q.difficulty, q.question, " +
                "q.option1, q.option2, q.option3, q.option4, " +
                "q.correct_answer, q.explanation " +
                "FROM questions q " +
                "WHERE LOWER(TRIM(q.category)) = LOWER(TRIM(?)) " +
                "AND LOWER(TRIM(q.difficulty)) = LOWER(TRIM(?)) " +
                "AND q.question IS NOT NULL " +
                "AND TRIM(q.question) <> '' " +
                "AND q.id = (" +
                "   SELECT MIN(q2.id) " +
                "   FROM questions q2 " +
                "   WHERE LOWER(TRIM(q2.category)) = LOWER(TRIM(q.category)) " +
                "   AND LOWER(TRIM(q2.difficulty)) = LOWER(TRIM(q.difficulty)) " +
                "   AND LOWER(TRIM(q2.question)) = LOWER(TRIM(q.question))" +
                ") " +
                "ORDER BY RAND() " +
                "LIMIT ?";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {
            statement.setString(1, category);
            statement.setString(2, difficulty);
            statement.setInt(3, Math.min(Math.max(limit, 0), 5));

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Question question = new Question(
                            rs.getInt("id"),
                            rs.getString("category"),
                            rs.getString("difficulty"),
                            rs.getString("question"),
                            rs.getString("option1"),
                            rs.getString("option2"),
                            rs.getString("option3"),
                            rs.getString("option4"),
                            rs.getInt("correct_answer"),
                            rs.getString("explanation")
                    );

                    questions.add(question);
                }
            }

        } catch (SQLException e) {
            System.err.println("Failed to load quiz questions.");
            e.printStackTrace();
        }

        System.out.println(
                "Category: " + category
                + " | Difficulty: " + difficulty
                + " | Unique questions loaded: " + questions.size()
        );

        return questions;
    }
}
