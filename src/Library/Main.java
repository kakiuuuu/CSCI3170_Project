package Library;

import java.util.*;
import java.sql.*;
import java.util.Date;

public class Main {
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void pressEnterToContinue() {
        System.out.println("\nPress Enter key to continue...\n");
        try {
            Scanner Enter = new Scanner(System.in);
            Enter.nextLine();
        } catch (Exception ignored) {}
    }
    public static void printMainMenu(Connection conn) {
        Date date = new Date();
        clearScreen();
        System.out.println("===== Welcome to Book Ordering Management System =====");
        System.out.println(" + System Date: " + date);
        Library.printOverview(conn);
        System.out.println("--------------------------------------------");
        System.out.println("> 1. Database Initialization");
        System.out.println("> 2. Customer Operation");
        System.out.println("> 3. Bookstore Operation");
        System.out.println("> 4. View Database");
        System.out.println("> 5. Quit");
        System.out.print("\n>>> Please Enter Your Query (1-5): ");
    }

    public static void printCustomerMenu(Connection conn) {
        Date date = new Date();
        clearScreen();
        System.out.println("===== Welcome to Book Ordering Management System =====");
        System.out.println(" + System Date: " + date);
        Library.printOverview(conn);
        System.out.println("--------------------------------------------");
        System.out.println("> 1 - Book Search");
        System.out.println("> 2 - Place an Order");
        System.out.println("> 3 - Check History Orders");
        System.out.println("> 4 - Back to Main Menu");
        System.out.print("\n>>> Please Enter Your Query (1-4): ");
    }

    public static void printBookstoreMenu(Connection conn) {
        Date date = new Date();
        clearScreen();
        System.out.println("===== Welcome to Book Ordering Management System =====");
        System.out.println(" + System Date: " + date);
        Library.printOverview(conn);
        System.out.println("--------------------------------------------");
        System.out.println("> 1 - Order Update");
        System.out.println("> 2 - Order Query");
        System.out.println("> 3 - Most Popular Books");
        System.out.println("> 4 - Back to Main Menu");
        System.out.print("\n>>> Please Enter Your Query (1-4): ");
    }

    private static void printDBMenu(Connection conn) {
        Date date = new Date();
        clearScreen();
        System.out.println("===== Welcome to Book Ordering Management System =====");
        System.out.println(" + System Date: " + date);
        Library.printOverview(conn);
        System.out.println("--------------------------------------------");
        System.out.println("> 1 - Show All Books");
        System.out.println("> 2 - Show All Customers");
        System.out.println("> 3 - Show All Orders");
        System.out.println("> 4 - Back to Main Menu");
        System.out.print("\n>>> Please Enter Your Query (1-4): ");
    }

    // Main driver method
    public static void main(String[] args) {

        Connection conn = Conn.makeConnection();
        if (conn == null) {
            System.out.println("Connection failed");
            return;
        }
        Scanner input = new Scanner(System.in);

        String mainMenuChoice;
        String customerMenuChoice;
        String bookstoreMenuChoice;
        String dbMenuChoice;
        String initializeChoice;
        String customerID;
        String orderID;
        int statusChoice;
        do {
            printMainMenu(conn);
            mainMenuChoice = input.nextLine();
            switch (mainMenuChoice) {
                // TODO - Database Initialization
                case "1" -> {
                    do {
                        printInitializeMenu(conn);
                        initializeChoice = input.nextLine();
                        switch (initializeChoice) {
                            case "1" -> Init.InitTable(conn);
                            case "2" -> Init.loadData(conn);
                            case "3" -> Init.resetTable(conn);
                            case "4" -> {}
                            default -> System.out.println(">>> Please Enter Your Query: (1-4)");
                        }
                    } while (!initializeChoice.equals("4"));
                }
                // Customer Operation
                case "2" -> {
                    do {
                        printCustomerMenu(conn);
                        customerMenuChoice = input.nextLine();
                        switch (customerMenuChoice) {
                            // DONE - Book Search
                            case "1" -> {
                                System.out.println("Enter the search Phrase: (Book Title, Author,or ISBN) ");
                                String searchPhrase = input.nextLine();
                                Book.search(conn, searchPhrase);
                            }

                            // DONE - Place an Order
                            case "2" -> {
                                System.out.print("Enter the customer ID: ");
                                customerID = input.next();
                                ArrayList <Order> order = new ArrayList<>();
                                while (true) {
                                    System.out.print("Enter the ISBN (book ID): ");
                                    String bookID = input.next();
                                    System.out.print("Enter the quantity: ");
                                    int quantity = input.nextInt();
                                    order.add(new Order(bookID, quantity));
                                    System.out.print("Do you want to add more books? (Y/N) :");
                                    String choice = input.next();
                                    if (choice.equals("N") || choice.equals("n")) {
                                        break;
                                    }
                                }
                                Order.placeOrder(conn, customerID, order);
                            }
                            // DONE - Check History Orders
                            case "3" -> {
                                System.out.print("Enter the customer ID: ");
                                customerID = input.next();
                                Order.printHistory(conn, customerID);
                            }
                            case "4" -> {}
                            default -> System.out.println(">>> Please Enter Your Query: (1-4)");
                        }
                    } while (!customerMenuChoice.equals("4"));
                }
                // Bookstore Operation
                case "3" -> {
                    do {
                        printBookstoreMenu(conn);
                        bookstoreMenuChoice = input.nextLine();
                        switch (bookstoreMenuChoice) {
                            // DONE - Order Update
                            case "1" -> {
                                System.out.print("Enter the order ID: ");
                                orderID = input.nextLine();
                                System.out.println("Update action:");
                                System.out.println("1: Updated to Shipped");
                                System.out.println("2: Updated to Received");
                                System.out.print(">>> Please Enter Your Query (1-2): ");
                                statusChoice = input.nextInt();
                                if (statusChoice < 1 || statusChoice > 2) {
                                    System.out.println("Error: Please Enter valid input (1-2)!");
                                    pressEnterToContinue();
                                    break;
                                }
                                Status status = Status.values()[statusChoice];
                                clearScreen();
                                Order.updateStatus(conn, orderID, status);
                            }

                            // DONE - Order Query by status
                            case "2" -> {
                                System.out.print("Enter the query (1: Ordered, 2: Shipped, 3: received) : ");
                                statusChoice = input.nextInt();
                                if (statusChoice < 1 || statusChoice > 3) {
                                    System.out.println("Error: Please Enter valid input (1-3)!");
                                    pressEnterToContinue();
                                    break;
                                }
                                Status status = Status.values()[statusChoice - 1];
                                Order.printByStatus(conn, status);
                            }

                            // DONE - Most Popular Books
                            case "3" -> Book.printMostPopular(conn);
                            case "4" -> {}
                            default -> System.out.println(">>> Please Enter Your Query: (1-4)");
                        }
                    } while (!bookstoreMenuChoice.equals("4"));
                }
                case "4" -> {
                    do {
                        printDBMenu(conn);
                        dbMenuChoice = input.nextLine();
                        switch (dbMenuChoice) {
                            case "1" -> Book.printAll(conn);
                            case "2" -> Customer.printAll(conn);
                            case "3" -> Order.printAll(conn);
                            case "4" -> {}
                            default -> System.out.println(">>> Please Enter Your Query: (1-4)");
                        }
                    } while (!dbMenuChoice.equals("4"));
                }
                case "5" -> System.out.println("Bye!");
                default -> System.out.println(">>> Please Enter Your Query: (1-5)");
            }
        } while (!mainMenuChoice.equals("5"));
    }

    private static void printInitializeMenu(Connection conn) {
        Date date = new Date();
        clearScreen();
        System.out.println("===== Welcome to Book Ordering Management System =====");
        System.out.println(" + System Date: " + date);
        Library.printOverview(conn);
        System.out.println("--------------------------------------------");
        System.out.println("> 1 - Initialize Database");
        System.out.println("> 2 - Load initialize records");
        System.out.println("> 3 - Drop All records");
        System.out.println("> 4 - Back to Main Menu");
        System.out.print("\n>>> Please Enter Your Query (1-4): ");
    }
}
