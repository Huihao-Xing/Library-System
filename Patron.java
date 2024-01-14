/**
 * Construct necessary methods and variables for Patron initialization.
 *
 * @author Huihao Xing
 *
 * @version 2023-10-24
 */
public class Patron {

    private String name;

    private String email;
    
    private int idNumber;
    
    private double balance = 0;

    public Patron(String name, String email, int idNumber, double balance) {
        this.name = name;
        this.email = email;
        this.idNumber = idNumber;
        this.balance = balance;
    }

    public double adjustBalance(double amount){
        this.balance += amount;
        return this.balance;
    }


    @Override
    public boolean equals(Object other) {
        if (other instanceof Patron) {
            Patron otherPatron = (Patron) other;
            return this.idNumber == otherPatron.idNumber;

        }
        else if (other instanceof Integer) {
            Integer otherId = (Integer) other;
            return this.idNumber == otherId;
        }

        return false;
    }

    @Override
    public String toString() {
      return String.format("Name: %s, Email: %s, ID: %d, Balance: $%.2f.", name, email, idNumber, balance);
}

}
