import Model.Customer;

public class MainSystem {
    public static void main(String[] args){
        Customer customer = new Customer("Sohan","Jangid",5000);
        System.out.println(customer.getFirstName());
        System.out.println(customer.getLastName());
        System.out.println(customer.getWalletBalance());
    }
}
