package theory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;

public class WriteBLOBDemo {

    /**
     * write BLOB(resume) to the proselyte_tutorial.personalinfo table
     * @param args
     */

    static final String JDBC_DRIVER =
            "com.mysql.cj.jdbc.Driver";

    static final String DATABASE_URL =
            "jdbc:mysql://localhost:3306/proselyte_tutorial";

    static final String USER = "root";
    static final String PASSWORD = "root";

    public static void main(String[] args)   {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        FileInputStream inputStream = null;

        try {

            Class.forName(JDBC_DRIVER);

            connection =
                    DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
            String SQL = "UPDATE personalinfo SET resume=? WHERE id=1";

            preparedStatement = connection.prepareStatement(SQL);

            File file =
                    new File("src\\main\\resources\\myResume.pdf");

            inputStream = new FileInputStream(file);

            preparedStatement.setBinaryStream(1, inputStream);

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
