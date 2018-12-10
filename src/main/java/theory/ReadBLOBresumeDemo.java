package theory;

import java.io.*;
import java.sql.*;

public class ReadBLOBresumeDemo {
    /**
     * read BLOB(resume) from the proselyte_tutorial.personalinfo table
     * @param args
     */

//    static final String JDBC_DRIVER =
//            "com.mysql.cj.jdbc.Driver";

    static final String DATABASE_URL =
            "jdbc:mysql://localhost:3306/proselyte_tutorial";

    static final String USER = "root";
    static final String PASSWORD = "root";

    public static void main(String[] args) {

        InputStream inputStream = null;

        String SQL = "SELECT resume FROM personalinfo WHERE id=1";

        File file = new File("src\\main\\resources\\readMyResume.pdf");

        try (Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL);
             FileOutputStream outputStream = new FileOutputStream(file)){

            if (resultSet.next()) {
                inputStream =
                        resultSet.getBinaryStream("resume");
                byte[] buff = new byte[1024];
                while (inputStream.read(buff) > 0) {
                    outputStream.write(buff);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
