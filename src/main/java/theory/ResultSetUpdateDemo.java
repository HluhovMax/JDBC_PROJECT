package theory;
import java.sql.*;

public class ResultSetUpdateDemo {
    static final String JDBC_DRIVER =
            "com.mysql.jdbc.Driver";

    static final String DATABASE_URL =
            "jdbc:mysql://localhost:3306/proselyte_tutorial";

    static final String USER = "root";
    static final String PASSWORD = "root";

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Connection connection = null;
        Statement statement = null;

        Class.forName(JDBC_DRIVER);

        connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);

        System.out.println("Creating statement...");

        try {
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            String SQL = "SELECT * FROM developers";
            ResultSet resultSet = statement.executeQuery(SQL);

            System.out.println("Initial list of records:");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String specialty = resultSet.getString("specialty");
                int salary = resultSet.getInt("salary");

                System.out.println("Last record in result set:");
                System.out.println("id: " + id);
                System.out.println("Name: " + name);
                System.out.println("Specialty: " + specialty);
                System.out.println("Salary: $" + salary);
                System.out.println("\n=========================\n");
            }

            System.out.println("Increasing all developer's salary (+ $1,000)...");
            resultSet.first();
            while (resultSet.next()) {
                int newSalary = resultSet.getInt("salary") + 1000;
                resultSet.updateInt("salary", newSalary);
                resultSet.updateRow();
            }

            resultSet.first();
            System.out.println("Final list of records:");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String specialty = resultSet.getString("specialty");
                int salary = resultSet.getInt("salary");

                System.out.println("id: " + id);
                System.out.println("Name: " + name);
                System.out.println("Specialty: " + specialty);
                System.out.println("Salary: $" + salary);
                System.out.println("\n=========================\n");
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }
}
