package Library;

import java.sql.Connection;

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

}
