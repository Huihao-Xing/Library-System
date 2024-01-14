import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date;



/**
 * Utility class for loading a collection of books from a text file.
 * 
 *  
 * @author Farzana R
 * @version 2019-11-26
 */
public class Driver {
    
    /**
     * Load the Patron file.
     * 
     * The data file should contain four comma-separated values pre line:
     * name, email, number (int), balance (double)
     * @param fileName = name of file for input.
     * @return Patron[] array of patrons.
     */
    public static Patron[] loadPatrons(String fileName) {
        ArrayList<Patron> patronList = new ArrayList<>();
        
        Scanner inFile = null;
        try {
            inFile = new Scanner(new File(fileName));
            while (inFile.hasNextLine()) {
                String[] terms = inFile.nextLine().split(",");
                Patron patron = new Patron(terms[0], terms[1],
                                           Integer.parseInt(terms[2].trim()),
                                           Double.parseDouble(terms[3].trim()));
                patronList.add(patron);
            }            
        } catch (IOException e) {
            System.out.println("Error reading patron file: " + fileName);
            System.exit(0);
        } finally {
            if (inFile != null) {
                inFile.close();
            }
        }
        
        return patronList.toArray(new Patron[0]);
    }
    
    
    /**
     * Load a set of books from the provided file.
     * 
     * @param fileName - The name of the file to load.
     * @param patrons - Array of patron objects one per ptraon from patron file.
     * @return - An array of Book objects, one per Book stored in the file
     */
    public static Book[] loadBooks(String fileName, Patron[] patrons) {
        Book[] books = null;
        String getLine;
        String[] splitLine;
        int rating;
        String[] reviewText;
        int numLines = 0;
        int bookIndex = 0;
        
        try {
            numLines = countLines(fileName);
            books = new Book[numLines];
            
            Scanner scanner = new Scanner(new File(fileName));
            // fields: title author year isbn pages
            while (scanner.hasNextLine()) {
                getLine = scanner.nextLine();
                // splitLine = getLine.split("[\\p{Punct}\\s]+");
                splitLine = getLine.split("~");
                String title = splitLine[0];
                String author = splitLine[1];
                String isbn = splitLine[2];
                int year = Integer.parseInt(splitLine[3]);
                int pages = Integer.parseInt(splitLine[4]);
                //create book
                books[bookIndex] = new Book(title, author, isbn, year, pages);
                //setup an initial dueDate of today
                Date dueDate = new Date();
                double rand2 = Math.random() * 60;
                int randLate = (int) rand2;  
                dueDate = DateUtils.addDays(dueDate, -randLate);
                int numPatrons = patrons.length;
                // for every 5th book - check it out to a random patron.
                if (bookIndex / 5 == 0) {
                    double rand = Math.random() * numPatrons;
                    int randPatron = (int) rand;
                    books[bookIndex].checkout(patrons[randPatron], dueDate);
                }
                bookIndex++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Problem reading " + fileName);
        }
        return books;
    }
    
    /**
     * Helper method for determining the number of lines in the file.
     * 
     * @param fileName - Name of the file
     * @return The number of lines in the file
     * @throws FileNotFoundException If the file cannot be opened for reading
     */
    private static int countLines(String fileName)
        throws FileNotFoundException {
        
        int numLines = 0;
        
        Scanner scanner = new Scanner(new File(fileName));
        
        while (scanner.hasNextLine()) {
            scanner.nextLine();
            numLines++;
        }
        return numLines;
    }
    
    /**
     * List all books in the library.
     * @param catalog = array of all books in libary.
     */
    public static void listCatalog(Book[] catalog) {
        //output the book catalog to the screen
        for (Book book: catalog) {
            String title = book.getTitle();
            String author = book.getAuthor();
            String isbn = book.getIsbn();
            int year = book.getYear();
            int pages = book.getPages();
            int status = book.getStatus();
            Date dueDate = book.getDue();
            System.out.println(book.toString() + "\t\t" + status + "\t\t" + dueDate);
        }
    }
    
    /**
     * List the Patron array.
     * 
     * @param patrons The Patron array
     */
    public static void listPatrons(Patron[] patrons) {
        for (Patron patron : patrons) {
            System.out.println(patron.toString());
        }
    }
    
    /**
     * Main Method.
     * Used to load and test books.
     * @param args = command line arguments.
     */
    public static void main(String[] args) {
        // filename lflbooks.txt
        
        String filename = "lflbooks.txt";
        
        //load patrons from file patrons.txt
        Patron[] patrons = loadPatrons("patrons.txt");
        
        //load the books from the file into the catalog
        Book[] catalog = loadBooks(filename, patrons);
        
        
        Library library = new Library(catalog, patrons);
        
        //Checkout some default books
        
        
        Scanner input = new Scanner(System.in);
        char option;
        
        do {
            System.out.println("\nEnter an option:");
            System.out.println("  B: List the book catalog");
            System.out.println("  b: List the details of a book");
            System.out.println("  P: List the Patrons");
            System.out.println("  p: List details of a patron");
            System.out.println("  S: Search for a book");
            System.out.println("  O: Check out a book");
            System.out.println("  I: Check in a book");
            System.out.println("  F: Determine the fine amount for a book");
            System.out.println("  Q: Quit");
            System.out.print("Option: ");
            
            String inLine = input.nextLine();
            if (inLine.length() > 0) {
                option = inLine.charAt(0);
            } else {
                option = ' ';
            }
            
            if (option  == 'B') {
                listCatalog(catalog);
            }
            if (option  == 'b') {
                System.out.print("Enter ISBN: ");
                String isbn = input.nextLine();
                for (Book b : catalog) {
                    if (isbn.equals(b.getIsbn())) {
                        System.out.println(b.toString());
                        System.out.println("Status: " + b.getStatus());
                        System.out.println("Due: " + b.getDue());
                        System.out.println("Patron: " + b.getPatron());
                    }
                }
            } else if (option  == 'P') {
                listPatrons(patrons);
            } else if (option  == 'p') {
                System.out.print("Enter Patron Nr: ");
                Integer patronNr = Integer.parseInt(input.nextLine());
                for (int x = 0; x < patrons.length; x++) {
                    if (patrons[x].equals(patronNr)) {
                        System.out.println(patrons[x].toString());
                    }
                }
            } else if (option  == 'S') {
                System.out.print("Enter Title: ");
                String searchTitle = input.nextLine();
                for (Book b : catalog) {
                    if (searchTitle.equals(b.getTitle())) {
                        System.out.println(b.toString());
                        System.out.println("Status: " + b.getStatus());
                        System.out.println("Due: " + b.getDue());
                        System.out.println("Patron: " + b.getPatron());
                    }
                }
                
            } else if (option  == 'O') {
                Book book = null;
                Patron patron = null;
                
                System.out.print("Enter ISBN: ");
                String isbn = input.nextLine();
                for (Book b : catalog) {
                    if (isbn.equals(b.getIsbn())) {
                        book = b;
                    }
                }
                if (book == null) {
                    System.out.println("That ISBN is not in the catalog.");
                    continue;
                }
                
                System.out.print("Enter Patron Nr: ");
                Integer patronNr = Integer.parseInt(input.nextLine());
                for (Patron p : patrons) {
                    if (p.equals(patronNr)) {
                        patron = p;
                    }
                }
                if (patron == null) {
                    System.out.println("That patron number does not exist.");
                    continue;
                }
                
                library.checkout(book, patron);     
            } else if (option  == 'I') {
                Book book = null;
                
                System.out.print("Enter ISBN: ");
                String isbn = input.nextLine();
                for (Book b : catalog) {
                    if (isbn.equals(b.getIsbn())) {
                        book = b;
                    }
                }
                if (book == null) {
                    System.out.println("That ISBN is not in the catalog.");
                    continue;
                }
                
                library.checkin(book);
                
            } else if (option  == 'F') {
                System.out.print("Enter ISBN: ");
                String isbn = input.nextLine();
                for (Book b : catalog) {
                    if (isbn.equals(b.getIsbn())) {
                        double fine = library.determineFine(b);
                        System.out.printf("Fine: %.2f%n", fine);
                    }
                }
            }
            
        } while (Character.toLowerCase(option) != 'q');
        
    }
}
