package theory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.*;

public class ReadBLOBresumeDemo {
    /**
     * read BLOB(resume) from the proselyte_tutorial.personalinfo table
     * @param args
     */

    static final String JDBC_DRIVER =
            "com.mysql.cj.jdbc.Driver";

    static final String DATABASE_URL =
            "jdbc:mysql://localhost:3306/proselyte_tutorial";

    static final String USER = "root";
    static final String PASSWORD = "root";

    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        FileOutputStream outputStream = null;

        try {
            Class.forName(JDBC_DRIVER);

            connection =
                    DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
            statement = connection.createStatement();

            String SQL = "SELECT resume FROM personalinfo WHERE id=1";

            resultSet = statement.executeQuery(SQL);

            File file = new File("");
        }
    }
}
