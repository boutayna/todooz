package fr.todooz.service;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import fr.todooz.domain.Task;

public class TaskService {
		
	private SessionFactory sessionFactory;
	   public void save(Task task) {
		   
		   Session session = sessionFactory.openSession();

		    session.save(task);

		    session.close();
	   }

	   public void delete(Long id) {
		   Session session = sessionFactory.openSession();
		   Transaction tx = session.beginTransaction();

		   String hqlDelete = "delete Task t where t.id = :id";
		   // or String hqlDelete = "delete Customer where name = :oldName";
		   int deletedEntities = session.createQuery( hqlDelete )
		           .setLong( "id", id )
		           .executeUpdate();
		   tx.commit();
		   session.close();
	   }

	   public List<Task> findAll() {
		   Session session = sessionFactory.openSession();
		   Criteria criteria = session.createCriteria(Task.class);
		   List tasks= criteria.list();
		   return tasks;
		   
	   }

	   public List<Task> findByQuery(String query) {
		   Session session = sessionFactory.openSession();
		   Criteria crit = session.createCriteria(Task.class);
		   crit.add(Restrictions.like("text", '%'+query+'%' ).ignoreCase());
		   List results = crit.list();
		   return results;
		 
	   }

	   public int count() {
	      List tasks = this.findAll();
	      return tasks.size();
	   }

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory=sessionFactory;
	}

	
	}
