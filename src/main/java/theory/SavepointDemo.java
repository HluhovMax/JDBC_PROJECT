package theory;

import java.sql.*;

public class SavepointDemo {
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

        connection.setAutoCommit(false);

        statement = connection.createStatement();

        String SQL = "SELECT * FROM developers";

        ResultSet resultSet = statement.executeQuery(SQL);

        System.out.println("Retrieving data from database...");
        System.out.println("\nDevelopers:");
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String specialty = resultSet.getString("specialty");
            int salary = resultSet.getInt("salary");

            System.out.println("\n================\n");
            System.out.println("id: " + id);
            System.out.println("Name: " + name);
            System.out.println("Specialty: " + specialty);
            System.out.println("Salary: $" + salary);
        }

        System.out.println("\n===========================\n");
        System.out.println("Creating savepoint...");
        Savepoint savepointOne = connection.setSavepoint("SavepointOne");

        try {
            SQL = "INSERT INTO developer VALUES (6, 'John','C#', 2200)";
            statement.executeUpdate(SQL);

            SQL = "INSE INTHE developers VALUES (7, 'Ron', 'Ruby', 1900)";
            statement.executeUpdate(SQL);

            connection.commit();
        } catch (SQLException e) {
            System.out.println("SQLException. Executing rollback to savepoint...");
            connection.rollback(savepointOne);
        }

        SQL = "SELECT * FROM developers";
        resultSet = statement.executeQuery(SQL);
        System.out.println("Retrieving data from database...");
        System.out.println("\nDevelopers:");
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String specialty = resultSet.getString("specialty");
            int salary = resultSet.getInt("salary");

            System.out.println("id: " + id);
            System.out.println("Name: " + name);
            System.out.println("Specialty: " + specialty);
            System.out.println("Salary: $" + salary);
            System.out.println("\n================\n");
        }

        System.out.println("Closing connection and releasing resources...");
        resultSet.close();
        statement.close();
        connection.close();
    }
}
