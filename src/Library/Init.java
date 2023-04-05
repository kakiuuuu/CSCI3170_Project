package Library;

import java.sql.*;

public class Init {
    static String createBookSQL = "CREATE TABLE Book (ISBN varchar(13) NOT NULL PRIMARY KEY, title varchar(100) NOT NULL, authors varchar(50) NOT NULL, price int NOT NULL, inventory int NOT NULL);";
    static String createCustomerSQL = "CREATE TABLE Customer ( \"UID\" VARCHAR(10) not null PRIMARY KEY, \"NAME\" VARCHAR(50) not null, \"ADDRESS\" VARCHAR(200) not null);";
    static String createBookOrderSQL = "CREATE TABLE BOOK_ORDER ( \"OID\" VARCHAR(8) not null PRIMARY KEY, \"UID\" VARCHAR(10) NOT NULL, ISBN varchar(13) NOT NULL, \"date\" DATE NOT NULL, \"quantity\" integer NOT NULL, \"Status\" VARCHAR(10) NOT NULL);";

    public static void init(Connection conn) throws SQLException {
        initBook(conn);
        System.out.println("Init Book Successfully");
        initCustomer(conn);
        System.out.println("Init Customer Successfully");
        initOrder(conn);
        System.out.println("Init Order Successfully");
    }

    public static void initBook(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        System.out.println("Creating table Book...");
        ResultSet rs = stmt.executeQuery(createBookSQL);
        rs.close();
        stmt.close();
    }

    public static void initOrder(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        System.out.println("Creating table Order...");
        ResultSet rs = stmt.executeQuery(createCustomerSQL);
        rs.close();
        stmt.close();
    }

    public static void initCustomer(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        System.out.println("Creating table Customer...");
        ResultSet rs = stmt.executeQuery(createBookOrderSQL);
        rs.close();
        stmt.close();
    }

    public static void main(String[] args) {
        Book book = new Book("0-1951-5344-8", "Book1", new String[]{"Mark P. O. Morford"}, 10, 10);
        Book book2 = new Book("0-1951-5344-8", "Book2", new String[]{"Carlo D'Este"}, 10, 10);
        Book book3 = new Book("0-1951-5344-8", "Book3", new String[]{"Amy Tan"}, 10, 10);
        Book book4 = new Book("0-1951-5344-8", "Book4", new String[]{"Mark P. O. Morford"}, 10, 10);
        Book book5 = new Book("0-1951-5344-8", "Book5", new String[]{"Ann Beattie"}, 10, 10);
        Book book6 = new Book("0-1951-5344-8", "Book6", new String[]{"Mark P. O. Morford"}, 10, 10);
    }
}
