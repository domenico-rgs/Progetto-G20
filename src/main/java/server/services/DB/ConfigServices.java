package server.services.DB;

import org.hibernate.Session;

import org.hibernate.SessionFactory;

public class ConfigServices {
	private SessionFactory sessionFactory;
	private Session s;
	
	 public ConfigServices(SessionFactory sessionFactory) {
	     this.sessionFactory = sessionFactory;
	 }
	
}
