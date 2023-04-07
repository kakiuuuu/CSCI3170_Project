package Library;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Init {
    static String DropTableSQL = """
            BEGIN
               EXECUTE IMMEDIATE 'DROP TABLE ' || 'BOOK_ORDER' ;
               EXECUTE IMMEDIATE 'DROP TABLE ' || 'BOOK' ;
               EXECUTE IMMEDIATE 'DROP TABLE ' || 'customer' ;
            EXCEPTION
               WHEN OTHERS THEN
                  IF SQLCODE != -942 THEN
                     RAISE;
                  END IF;
            END;
            """;
    static String createBookSQL = """
            CREATE TABLE BOOK (
               ISBN       VARCHAR2(13 Byte) PRIMARY KEY ,
               TITLE      VARCHAR2(100 Byte)  NOT NULL,
               AUTHORS    VARCHAR2(50 Byte)  NOT NULL,
               PRICE      NUMBER  NOT NULL,
               INVENTORY  NUMBER  NOT NULL
            )
            """;
    static String createCustomerSQL = """
            CREATE TABLE CUSTOMER (
               "uid"      VARCHAR2(10 Byte)  PRIMARY KEY,
               NAME     VARCHAR2(50 Byte)    NOT NULL,
               ADDRESS  VARCHAR2(200 Byte)   NOT NULL
            )
            """;
    static String createBookOrderSQL = """
            CREATE TABLE BOOK_ORDER (
                   OID         VARCHAR2(8 Byte),
                   "uid"       VARCHAR2(10 Byte),
                   ISBN        VARCHAR2(13 Byte) ,
                   "date"      DATE                NOT NULL,
                   Quantity  NUMBER              NOT NULL,
                   Status    VARCHAR2(10 Byte)   NOT NULL,
                   FOREIGN KEY ("uid") REFERENCES CUSTOMER("uid"),
                   FOREIGN KEY (ISBN) REFERENCES BOOK(ISBN),
                   CONSTRAINT PK_BOOK_ORDER PRIMARY KEY (OID,"uid",ISBN)
            )
            """;

    public static void InitTable(Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            CallableStatement cs = conn.prepareCall(DropTableSQL);
            cs.execute();
            System.out.println("Dropping tables if exist...");
            System.out.println("Creating table Book...");
            ResultSet rs = stmt.executeQuery(createBookSQL);
            System.out.println("Creating table Customer...");
            rs = stmt.executeQuery(createCustomerSQL);
            System.out.println("Creating table Order...");
            rs = stmt.executeQuery(createBookOrderSQL);
            System.out.println("Finished creating tables.");
            Main.pressEnterToContinue();
//            Main.clearScreen();
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void resetTable(Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            System.out.println("Delete table Order record...");
            ResultSet rs = stmt.executeQuery("TRUNCATE TABLE BOOK_ORDER");
            System.out.println("Delete table Book record...");
            rs = stmt.executeQuery("TRUNCATE TABLE Book");
            System.out.println("Delete table Customer record...");
            rs = stmt.executeQuery("TRUNCATE TABLE Customer");
            System.out.println("Finished dropping tables.");
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

//    load init records from local files (e.g., txt, csv, tsv
    public static void loadData(Connection conn) {
        try {
            Scanner scanner = new Scanner(new File("src/Book.txt"));
            Statement stmt = conn.createStatement();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split("\t");
                String ISBN = data[0];
                String title = data[1];
                String authors = data[2];
                int price = Integer.parseInt(data[3]);
                int inventory = Integer.parseInt(data[4]);
                String sql = "INSERT INTO Book VALUES ('" + ISBN + "', '" + title + "', '" + authors + "', " + price + ", " + inventory + ")";
                stmt.executeUpdate(sql);
                System.out.println(sql);
            }

            scanner.close();
            scanner = new Scanner(new File("src/Customer.txt"));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split("\t");
                String UID = data[0];
                String name = data[1];
                String address = data[2];
                String sql = "INSERT INTO Customer VALUES ('" + UID + "', '" + name + "', '" + address + "')";
                stmt.executeUpdate(sql);
                System.out.println(sql);
            }
            scanner.close();
            scanner = new Scanner(new File("src/Order.txt"));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split("\t");
                String OID = data[0];
                String UID = data[1];
                String ISBN = data[2];
                String date = data[3];
//                Date date = new Date(data[3]);
//                java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                int quantity = Integer.parseInt(data[4]);
                String status = data[5];
                String sql = "INSERT INTO BOOK_ORDER VALUES ('" + OID + "', '" + UID + "', '" + ISBN + "', '" + date + "', " + quantity + ", '" + status + "')";
                stmt.executeUpdate(sql);
                System.out.println(sql);
            }
            scanner.close();
            stmt.close();
        } catch (FileNotFoundException | SQLException e) {
            throw new RuntimeException(e);
//            System.err.println(e.getMessage());
        }
    }


}
