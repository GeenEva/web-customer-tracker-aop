package com.luv2code.springdemo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.luv2code.springdemo.entity.Customer;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

	@Autowired
	private SessionFactory sessionFactory;


	@Override
	public Customer getCustomer(int customerId) {

		Session currentSession = sessionFactory.getCurrentSession();

		return currentSession.get(Customer.class, customerId);
	}


	@Override
	public List<Customer> getCustomers() {

		Session currentSession = sessionFactory.getCurrentSession();

		// In HQL query, don't write the table name but write your Entity class name in your query!
		Query<Customer> theQuery = currentSession.createQuery("FROM Customer order by lastName", Customer.class);

		List<Customer> customers = theQuery.getResultList();


		return customers;
	}

	@Override
	public void saveCustomer(Customer customer) {

		Session currentSession = sessionFactory.getCurrentSession();

		currentSession.saveOrUpdate(customer);

	}


	@Override
	public void deleteCustomer(int customerId) {

		Session currentSession = sessionFactory.getCurrentSession();

		Customer customer = currentSession.get(Customer.class, customerId);

		currentSession.delete(customer);
	}

	public void deleteCustomerChadVersionint (int customerId) {

		Session currentSession = sessionFactory.getCurrentSession();

		Query theQuery = currentSession.createQuery("DELETE FROM Customer WHERE id=:customerId");
		theQuery.setParameter("customerId", customerId);

		theQuery.executeUpdate();
	}


	@Override
	public List<Customer> searchCustomers(String theName) {

		Session currentSession = sessionFactory.getCurrentSession();

		Query<Customer> theQuery = null;

		if (theName != null && theName.trim().length() > 0) {

			// search for firstName or lastName ... case insensitive
			theQuery =currentSession.createQuery("FROM Customer WHERE lower(firstName) LIKE :theName or lower(lastName) like :theName", Customer.class)
					//% can be used for the wildcarts, so that search for pat gives
					//both patterson and patty brard
					.setParameter("theName", "%" + theName.toLowerCase() + "%");

		}
		else {
			// theSearchName is empty ... so just get all customers
			theQuery =currentSession.createQuery("from Customer", Customer.class);            
		}
		return theQuery.getResultList();
	}



}
