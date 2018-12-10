package theory;

import java.util.Date;

public class DataTypeConversionDemo {
    public static void main(String[] args) {
        /**
         *  Java Date and Time
         */

        Date javaDate = new Date();
        long javaTime = javaDate.getTime();

        System.out.println("Current date and time (Java): " + javaDate.toString());
        System.out.printf("Current time (Java): " + javaDate.getHours() + ":" +
                javaDate.getMinutes() + ":"
                + javaDate.getSeconds());

        /**
         * SQL Date and Time
         */

        java.sql.Timestamp sqlTimeStamp = new java.sql.Timestamp(javaTime);
        java.sql.Date sqlDate = new java.sql.Date(javaTime);
        java.sql.Time sqlTime = new java.sql.Time(javaTime);

        System.out.println("\n=======================================\n");
        System.out.println("Current timeStamp (SQL): " + sqlTimeStamp.toString());
        System.out.println("Current date (SQL): " + sqlDate.toString());
        System.out.println("Current time (SQL): " + sqlTime.toString());
    }
}
