package Library;

import java.sql.*;

public class Customer {

    public static void printHeader() {
        System.out.printf("%-15s %-15s %-30s %n", "UID", "Name", "Address");
        System.out.println("---------------------------------------------------------------------------------------------");
    }
    public static void printRow(String UID, String Name, String Address) {
        System.out.printf("%-15s %-15s %-30s %n",UID, Name, Address);
    }
    public static void printAll(Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM CUSTOMER");
            printHeader();
            while (rs.next()) {
                printRow(rs.getString("UID"), rs.getString("NAME"), rs.getString("ADDRESS"));            }
            rs.close();
            stmt.close();
            Main.pressEnterToContinue();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
