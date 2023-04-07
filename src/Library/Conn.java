package Library;

import java.sql.*;

public class Conn {
    public static Connection makeConnection() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (Exception x) {
            System.err.println("Unable to load the driver class!");
        }
        try {
            Connection conn = DriverManager
                    .getConnection("jdbc:oracle:thin:@//db18.cse.cuhk.edu.hk:1521/oradb.cse.cuhk.edu.hk", "h020", "FoclalkO");
            if (!conn.isClosed()) {
                System.out.println("connect db Successfully");
            }
            return conn;
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        return null;
    }



}
