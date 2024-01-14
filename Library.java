import java.util.Date;

/**
 * Represents a Little "free" Library.
 * 
 * @author Farzana Rahman
 * 
 * @version 2019-11-26
 */
public class Library {
    
    public static final char AUTHOR_SEARCH = 'A';
    
    public static final char OVERDUE_SEARCH = 'O';
    
    public static final char PATRON_SEARCH = 'P';
    
    public static final char TITLE_SEARCH = 'T';
    
    private Book[] books;
    
    private Patron[] patrons;
    
    /**
     * Initialize a Library object.
     * 
     * @param books the books in the library
     * @param patrons the library's patrons
     */
    public Library(Book[] books, Patron[] patrons) {
        this.books = books;
        this.patrons = patrons;
    }
    
    /**
     * Checks in a book from the library.
     * 
     * @param book the book
     * @return success or error
     */
    public boolean checkin(Book book) {
        if (book != null) {
            
            // make sure it's a library book
            for (Book item : books) {
                if (item.equals(book)) {
                    
                    // make sure it's checked out
                    if (item.getStatus() == Book.UNAVAILABLE) {
                        
                        // calculate/save the fine
                        double fine = determineFine(book);
                        book.getPatron().adjustBalance(fine);
                        book.checkin();
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    /**
     * Checks out a book from the library.
     * 
     * @param book the book
     * @param patron the patron
     * @return success or error
     */
    public boolean checkout(Book book, Patron patron) {
        if (book != null && patron != null) {
            
            // make sure it's a library book
            for (Book item : books) {
                if (item.equals(book)) {
                    
                    // make sure it's checked in
                    if (item.getStatus() == Book.AVAILABLE) {
                        
                        // calculate the due date
                        Date due = DateUtils.addDays(new Date(), 10);
                        book.checkout(patron, due);
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    /**
     * Determine the fine currently due for a book that is checked out.
     * The fine rate is $0.50 per day after the due date.
     * 
     * @param book the book (checked out)
     * @return the fine amount
     */
    public double determineFine(Book book) {
        if (book.getDue() != null) {
            int days = DateUtils.interval(book.getDue(), new Date());
            if (days > 0) {
                return days * 0.5;
            }
        }
        return 0.0;
    }
    
    /**
     * Searches the library for the given key.
     * 
     * @param key the String, Date, or Patron to find
     * @param type one of the _SEARCH constant values
     * @return books that match the search criteria
     */
    public Book[] searchBooks(Object key, char type) {
        
        // count the number of books
        int count = 0;
        for (Book b : books) {
            if (type == TITLE_SEARCH && b.getTitle().equals(key)) {
                count++;
            } else if (type == AUTHOR_SEARCH && b.getAuthor().equals(key)) {
                count++;
            } else if (type == PATRON_SEARCH && b.getPatron() != null
                           && b.getPatron().equals(key)) {
                count++;
            } else if (type == OVERDUE_SEARCH && b.getDue() != null
                           && key instanceof Date) {
                if (DateUtils.interval(b.getDue(), (Date) key) > 0) {
                    count++;
                }
            }
        }
        
        // return the matching books
        Book[] booksFound = new Book[count];
        int i = 0;
        for (Book b : books) {
            if (type == TITLE_SEARCH && b.getTitle().equals(key)) {
                booksFound[i++] = b;
            } else if (type == AUTHOR_SEARCH && b.getAuthor().equals(key)) {
                booksFound[i++] = b;
            } else if (type == PATRON_SEARCH && b.getPatron() != null
                           && b.getPatron().equals(key)) {
                booksFound[i++] = b;
            } else if (type == OVERDUE_SEARCH && b.getDue() != null
                           && key instanceof Date) {
                if (DateUtils.interval(b.getDue(), (Date) key) > 0) {
                    booksFound[i++] = b;
                }
            }
        }
        
        return booksFound;
    }
    
}
