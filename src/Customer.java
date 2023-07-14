/**
 * This class containing information about customers (i.e ID, Name, Email);
 */
public class Customer {

    private static int idGeneration = 0;
    private int customerId;
    private final String name;
    private final String email;


    public Customer(String name, String email) {
        this.name = name;
        this.email = email;
        customerId = idGeneration++;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
