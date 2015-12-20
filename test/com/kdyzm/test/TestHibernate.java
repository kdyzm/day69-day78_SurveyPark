package com.kdyzm.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class TestHibernate {
	private static SessionFactory sessionFactory;
	static{
		Configuration configuration=new Configuration();
		configuration.configure("hibernate/hibernate.cfg.xml");
		sessionFactory=configuration.buildSessionFactory();
	}
	public static void main(String[] args) {
		Session session=sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		
		transaction.commit();
		session.close();
	}
}
