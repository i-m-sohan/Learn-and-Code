package com.CustomerManagementSystem;

import java.sql.SQLException;
import java.util.List;

import com.CustomerManagementSystem.Customer.Customer;
import com.CustomerManagementSystem.Customer.CustomerSelector;
import com.CustomerManagementSystem.CustomerDataTransformer.CustomerDataTransformer;
import com.CustomerManagementSystem.Input.InputValue;

public class MainService{
	public static void main(String[] args)throws SQLException , ClassNotFoundException{	
		CustomerSelector selector = new CustomerSelector();
		List<Customer> customers  = selector.getCustomersByCountry("INDIA");
		CustomerDataTransformer customerDataTransformer = new CustomerDataTransformer();
		String customerCsvData = customerDataTransformer.exportToCSV(customers);
		System.out.println(customerCsvData);
	}
}
