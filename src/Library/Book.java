package Library;

import java.sql.*;

// Class
public class Book {

    public String ISBN;
    public String title;
    public String[] authors;
    public int price;
    public int inventory;

    // Method
    // To add book details
    public Book(String ISBN, String title, String[] authors, int price, int inventory)
    {
        this.ISBN = ISBN;
        this.title = title;
        this.authors = authors;
        this.price = price;
        this.inventory = inventory;
    }

    public static void printHeader() {
        System.out.printf("%-15S %-15S %-30S %-10S %-5S %n","ISBN","TITLE", "AUTHORS", "PRICE", "INVENTORY");
        System.out.println("-------------------------------------------------------------------------------------");
    }
    public static void printHeader(Boolean ranking) {
        System.out.printf("%-10S %-15S %-15S %-30S %-10S %-15S %-10S %n","RANKING", "ISBN","TITLE", "AUTHORS", "PRICE", "INVENTORY", "SALES");
        System.out.println("---------------------------------------------------------------------------------------------------------------");
    }
    public static void printRow(String ISBN, String title, String authors, int price, int inventory) {
        System.out.printf("%-15s %-15s %-30s %-10d %-5d %n",ISBN, title, authors, price, inventory);
    }
    public static void printRow(Integer Ranking, String ISBN, String title, String authors, int price, int inventory, int sales) {
        System.out.printf("%-10s %-15s %-15s %-30s %-10d %-15d %-10d %n",Ranking, ISBN, title, authors, price, inventory, sales);
    }
    public static void printAll(Connection conn){
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM BOOK");
            printHeader();
            while (rs.next()) {
                printRow(rs.getString("ISBN"), rs.getString("TITLE"), rs.getString("AUTHORS"), rs.getInt("PRICE"), rs.getInt("INVENTORY"));
            }
            rs.close();
            stmt.close();
            Main.pressEnterToContinue();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     *To query the most populars book so that the staff can prepare more before book runs out.
     *Expected Output: (1) results or empty (2) results should contain book information in detail.
     */
    public static void printMostPopular(Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(
                    """
                            SELECT B.ISBN, B.TITLE, B.AUTHORS, B.PRICE, B.INVENTORY, SUM(BO.QUANTITY) AS SALES
                            FROM BOOK B
                            JOIN BOOK_ORDER BO ON B.ISBN = BO.ISBN
                            GROUP BY B.ISBN, B.TITLE, B.AUTHORS, B.PRICE, B.INVENTORY ORDER BY SALES DESC""");
            if(rs.next()) {
                int Ranking = 1;
                System.out.println("Most popular books:");
                printHeader(true);
                printRow(Ranking, rs.getString("ISBN"), rs.getString("TITLE"), rs.getString("AUTHORS"), rs.getInt("PRICE"), rs.getInt("INVENTORY"), rs.getInt("SALES"));
                while (rs.next()) {
                    Ranking++;
                    printRow(Ranking, rs.getString("ISBN"), rs.getString("TITLE"), rs.getString("AUTHORS"), rs.getInt("PRICE"), rs.getInt("INVENTORY"), rs.getInt("SALES"));
                }
            } else {
                System.out.println("No popular books found!");
            }
            rs.close();
            stmt.close();
            Main.pressEnterToContinue();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

/**
 * • To query a book by ISBN, Book Title or Author Name.
 * • Expected Output: (1) book information (2) empty if not found (3) well formatted output.
 */
public static void search(Connection conn, String searchPhrase) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM BOOK WHERE TITLE LIKE '%" + searchPhrase + "%' or ISBN LIKE '%" + searchPhrase + "%' or AUTHORS LIKE '%" + searchPhrase + "%'");
            if(rs.next()) {
                System.out.println("Book found!");
                printHeader();
                printRow(rs.getString("ISBN"), rs.getString("TITLE"), rs.getString("AUTHORS"), rs.getInt("PRICE"), rs.getInt("INVENTORY"));
                while (rs.next()) {
                    printRow(rs.getString("ISBN"), rs.getString("TITLE"), rs.getString("AUTHORS"), rs.getInt("PRICE"), rs.getInt("INVENTORY"));
                }
            } else {
                System.out.println("Book not found!");
            }
            rs.close();
            stmt.close();
            Main.pressEnterToContinue();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
