/**
 * Construct necessary methods and variables for Book initialization.
 *
 * @author Huihao Xing
 *
 * @version 2023-10-24
 */
import java.util.Date;

public class Book {

    public static int AVAILABLE = 1; 
    
    public static int UNAVAILABLE = 2;

    private String title;

    private String author;

    private String isbn;

    private int pages;

    private int year;

    private int status;

    private Date due;

    private Patron patron;

    public Book(String title,String author,String isbn, int year, int pages) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.year = year;
        this.pages = pages;
        this.status = AVAILABLE;
    }

    public void checkin() {
        this.status = Book.AVAILABLE;
        this.patron = null;
        this.due = null;
    }

    public void checkout(Patron patron, Date due) {
        this.status = Book.UNAVAILABLE;
        this.patron = patron;
        this.due = due;
    }

    public boolean equals(Object other) {
        if (other instanceof Book) {
            Book otherBook = (Book) other;
            return this.isbn.equals(otherBook.isbn);

        }
        else if (other instanceof String) {
            String otherIsbn = (String) other;
            return this.isbn.equals(otherIsbn);
        }

        return false;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getPages() {
        return pages;
    }

    public int getYear() {
        return year;
    }

    public int getStatus() {
        return status;
    }

    public Date getDue() {
        return due;
    }

    public Patron getPatron() {
        return patron;
    }

    @Override
    public String toString() {
        return "Title: " + title + ", Author: " + author + ", ISBN: " + isbn + ", Year: " + year + ", Pages: " + pages + ".";
    }

}
