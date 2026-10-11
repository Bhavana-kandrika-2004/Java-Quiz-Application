import database.QuestionDAO;
import model.Question;

import java.util.List;

public class TestQuestions {

    public static void main(String[] args) {

        QuestionDAO questionDAO = new QuestionDAO();

        List<Question> questions =
                questionDAO.getRandomQuestions("Java", "Easy", 5);

        System.out.println("Questions fetched: " + questions.size());

        System.out.println("--------------------------------");

        for (Question q : questions) {

            System.out.println("Question: " + q.getQuestion());
            System.out.println("Option 1: " + q.getOption1());
            System.out.println("Option 2: " + q.getOption2());
            System.out.println("Option 3: " + q.getOption3());
            System.out.println("Option 4: " + q.getOption4());
            System.out.println("Correct Answer: " + q.getCorrectAnswer());

            System.out.println("--------------------------------");
        }
    }
}