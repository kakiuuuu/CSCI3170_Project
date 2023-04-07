package Library;

import java.sql.*;

// Class
public class Library {
    public static void printOverview(Connection conn){
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM BOOK_ORDER");
            rs.next();
            String orderCount = rs.getString("Count(*)");
            rs = stmt.executeQuery("SELECT COUNT(*) FROM CUSTOMER");
            rs.next();
            String customerCount = rs.getString("Count(*)");
            rs = stmt.executeQuery("SELECT COUNT(*) FROM BOOK");
            rs.next();
            String bookCount = rs.getString("Count(*)");
            System.out.printf(" + Database Records: Books (%s), Customers (%s), Orders (%s)%n", bookCount, customerCount, orderCount);
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            Main.clearScreen();
            System.out.println("Cannot Find Table. Auto-Initializing Database...");
            Init.InitTable(conn);
        }
    }

}
