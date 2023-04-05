package Library;

import java.util.*;
import java.sql.*;
import java.util.Date;

// Class
public class Library {

    public static void printMainMenu() {
        Date date = new Date();
        System.out.println("===== Welcome to Book Ordering Management System =====");
        System.out.println(" + System Date: " + date);
        System.out.println(" + Database Records: Books (999), Customers (999), Orders (999)");
        System.out.println("--------------------------------------------");
        System.out.println("> 1. Database Initialization");
        System.out.println("> 2. Customer Operation");
        System.out.println("> 3. Bookstore Operation");
        System.out.println("> 4. Quit");
        System.out.println("\n>>> Please Enter Your Query: (1-4))");
    }

    public static void printCustomerMenu() {
        System.out.println("> 1 - Book Search");
        System.out.println("> 2 - Place an Order");
        System.out.println("> 3 - Check History Orders");
        System.out.println("> 4 - Back to Main Menu");
        System.out.println("\n>>> Please Enter Your Query: (1-4))");
    }

    public static void printBookstoreMenu() {
        System.out.println("> 1 - Order Update");
        System.out.println("> 2 - Order Query");
        System.out.println("> 3 - N Most Popular Books");
        System.out.println("> 4 - Back to Main Menu");
        System.out.println("\n>>> Please Enter Your Query: (1-4))");
    }

    // Main driver method
    public static void main(String[] args) {
        Connection conn = Conn.conn();
        Scanner input = new Scanner(System.in);

        int choice;
        int customerChoice;
        int bookstoreChoice;

        do {
            printMainMenu();
            choice = input.nextInt();
            switch (choice) {

                // Database Initialization
                case 1 -> {
                    try {
                        Init.init(conn);
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                }

                // book b = new book();
                // ob.addBook(b);
                // Customer Operation
                case 2 -> {
                    printCustomerMenu();
                    do {
                        customerChoice = input.nextInt();
                        switch (customerChoice) {
                            // - Book Search
                            case 1:
                                break;

                            // - Place an Order
                            case 2:
                                break;

                            // - Check History Orders
                            case 3:
                                break;
                        }
                    } while (customerChoice != 4);
                }
                // Case
                case 3 -> {
                    printBookstoreMenu();
                    do {
                        bookstoreChoice = input.nextInt();
                        switch (bookstoreChoice) {
                            // - Order Update
                            case 1:
                                break;

                            // - Order Query
                            case 2:
                                break;

                            // - N Most Popular Books
                            case 3:
                                break;
                        }
                    } while (bookstoreChoice != 4);
                }
                default -> System.out.println(">>> Please Enter Your Query: (1-4))");
            }

        } while (choice != 4);
    }
}
