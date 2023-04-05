package Library;

import java.sql.*;

public class Conn {
    public static Connection conn() {
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

    public static void exec(Connection conn, String sql) throws SQLException {
//        Statement stmt = conn.createStatement();
//        ResultSet rs = stmt.executeQuery(sql);
//        while (rs.next()) {
//            System.out.println(rs.getString(1) + " " + rs.getString(2));
//        }
//        rs.close();
//        stmt.close();
    }
}
