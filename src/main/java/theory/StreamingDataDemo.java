package theory;
import java.sql.*;
import java.io.*;

public class StreamingDataDemo {
    static final String JDBC_DRIVER =
            "com.mysql.cj.jdbc.Driver";

    static final String DATABASE_URL =
            "jdbc:mysql://localhost:3306/proselyte_tutorial";

    static final String USER = "root";
    static final String PASSWORD = "root";

    public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            Class.forName(JDBC_DRIVER);

            System.out.println("Connecting to database...");
            connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);

            statement = connection.createStatement();
            createXMLTable(statement);

            File f = new File("src\\main\\resources\\files\\PROSELYTE_DEVELOPER.xml");
            long fileLength = f.length();
            FileInputStream fis = new FileInputStream(f);

            String SQL = "INSERT INTO XML_Developer VALUES (?,?)";
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setInt(1, 1);
            preparedStatement.setAsciiStream(2, fis, (int) fileLength);
            preparedStatement.execute();

            fis.close();

            SQL = "SELECT Data FROM XML_Developer WHERE id=1";
            resultSet = statement.executeQuery(SQL);
            if (resultSet.next()) {
                InputStream xmlInputStream = resultSet.getAsciiStream(1);
                int c;
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                while ((c = xmlInputStream.read()) != -1)
                    bos.write(c);
                System.out.println(bos.toString());
            }
            resultSet.close();
            statement.close();
            preparedStatement.close();
            connection.close();
        }catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null)
                    statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        System.out.println("Thank You!");
    }
    public static void createXMLTable(Statement statement) throws SQLException{
        System.out.println("Creating XML_Developer table...");
        String SQL = "CREATE TABLE XML_Developer " +
                "(id INTEGER, Data LONG)";
        try {
            statement.executeUpdate("DROP TABLE XML_Developer");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        statement.executeUpdate(SQL);
    }

}
