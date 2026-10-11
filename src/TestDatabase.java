import database.DatabaseConnection;
import java.sql.Connection;

public class TestDatabase {

    public static void main(String[] args) {

        Connection connection = DatabaseConnection.getConnection();

        if (connection != null) {
            System.out.println("SUCCESS: Java is connected to MySQL!");

            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("FAILED: Could not connect to MySQL.");
        }
    }
}