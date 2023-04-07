package Library;

import java.sql.*;
import java.util.*;
import java.util.Date;
enum Status
{
    ordered, shipped, received
}

public class Order {


    public String ISBN;
    public int inventory;
    public static int orderCount = 30;

    public Order(String bookID, int inventory) {
        this.ISBN = bookID;
        this.inventory = inventory;
    }

    public static void printHeader() {
        System.out.printf("%-10s %-10s %-15s %-10s %-15s %-15s %n", "OID", "UID", "ISBN", "Quantity", "Date", "Status");
        System.out.println("---------------------------------------------------------------------------------------------");
    }
    public static void printRow(String OID, String UID, String ISBN, int inventory, Date orderDate, Status status) {
        System.out.printf("%-10s %-10s %-15s %-10s %tY-%tm-%-7td %-15s %n", OID, UID, ISBN, inventory, orderDate,orderDate,orderDate, status);
    }
    public static void printAll(Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM BOOK_ORDER");
            printHeader();
            while (rs.next()) {
                printRow(rs.getString("OID"), rs.getString("UID"), rs.getString("ISBN"), rs.getInt("QUANTITY"), rs.getDate("DATE"), Status.valueOf(rs.getString("STATUS")));            }
            rs.close();
            stmt.close();
            Main.pressEnterToContinue();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

/**
 * • To update the shipping status of an order.
 * • Expected Output: (1) success or fail (2) reasons if failed, e.g., order has already been shipped.
 */
public static void updateStatus(Connection conn, String orderID, Status status) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM BOOK_ORDER WHERE OID = '" + orderID + "'");
            if (!rs.next()) {
                System.out.println("Failed to update order status!");
                System.out.println(">>> Order does not exist!");
            } else if (rs.getString("STATUS").equals(status.toString()) || rs.getString("STATUS").equals("received")) {
                System.out.println("Failed to update order status!");
                System.out.println(">>> Order has already been " + status + "!");
            } else {
                stmt.executeUpdate("UPDATE BOOK_ORDER SET STATUS = '" + status + "' WHERE OID = '" + orderID + "'");
                System.out.println("Order status updated successfully!");
            }
            stmt.close();
            Main.pressEnterToContinue();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void printByStatus(Connection conn, Status status) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM BOOK_ORDER WHERE STATUS = '" + status + "'");
            if (rs.next()){
                System.out.println("Order with status " + status + " :");
                printHeader();
                printRow(rs.getString("OID"), rs.getString("UID"), rs.getString("ISBN"), rs.getInt("QUANTITY"), rs.getDate("DATE"), status);
                while (rs.next()) {
                    printRow(rs.getString("OID"), rs.getString("UID"), rs.getString("ISBN"), rs.getInt("QUANTITY"), rs.getDate("DATE"), status);
                }
            } else {
                System.out.println("No order with status " + status);
            }
            rs.close();
            stmt.close();
            Main.pressEnterToContinue();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void printHistory(Connection conn, String customerID) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM BOOK_ORDER WHERE \"uid\" = '" + customerID + "'");
            System.out.println("\nOrder history for customer " + customerID + " :");
            printHeader();
            while (rs.next()) {
                printRow(rs.getString("OID"), rs.getString("UID"), rs.getString("ISBN"), rs.getInt("QUANTITY"), rs.getDate("DATE"), Status.valueOf(rs.getString("STATUS")));            }
            rs.close();
            stmt.close();
            Main.pressEnterToContinue();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

//    Expected Output: (1) success or fail (2) reasons if failed, e.g., inventory shortage.
    public static void placeOrder(Connection conn, String customerID, ArrayList<Order> orders) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM CUSTOMER WHERE \"uid\" = '" + customerID + "'");
            if (!rs.next()) {
                System.out.println("Failed to place order!");
                System.out.println("Error: Customer does not exist!");
            } else {
                for (Order order : orders) {
                    rs = stmt.executeQuery("SELECT * FROM BOOK WHERE ISBN = '" + order.ISBN + "'");
                    if (!rs.next()) {
                        System.out.println("Failed to place order!");
                        System.out.println("Error: Book " + order.ISBN + " does not exist!");
                        throw new SQLException();
                    } else if (rs.getInt("INVENTORY") < order.inventory) {
                        System.out.println("Failed to place order!");
                        System.out.println("Error: Book " + order.ISBN + " has insufficient inventory!");
                        throw new SQLException();
                    }
                }
                for (Order value : orders) {
                    stmt.executeUpdate("INSERT INTO BOOK_ORDER (OID, \"uid\", ISBN, QUANTITY, \"date\", STATUS) VALUES ('" + orderCount + "', '" + customerID + "', '" + value.ISBN + "', " + value.inventory + ", sysdate, 'ordered')");
                    stmt.executeUpdate("UPDATE BOOK SET INVENTORY = INVENTORY - " + value.inventory + " WHERE ISBN = '" + value.ISBN + "'");
                }
                System.out.println("Order placed successfully!");
                orderCount++;
            }
            rs.close();
            stmt.close();
        } catch (SQLException ignored) {
        } finally {
            Main.pressEnterToContinue();
        }
    }
}
