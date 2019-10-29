package com.luv2code.springdemo.dao;

import java.util.List;
import com.luv2code.springdemo.entity.Customer;


public interface CustomerDAO {
	
	public Customer getCustomer(int customerId);
	
	public List<Customer> getCustomers();
	
	public void saveCustomer(Customer customer);

	public void deleteCustomer(int customerId);

	public List<Customer> searchCustomers(String theName);
}
