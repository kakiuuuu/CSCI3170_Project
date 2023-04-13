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

    public static void executeSQL(Connection conn, String sql) {
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void fixDateLanguage(Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("alter session set nls_date_language='american'");
            stmt.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

}
