package fr.todooz.service;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import fr.todooz.domain.Task;

public class TaskServiceImpl implements TaskService {

	@Inject
	private SessionFactory sessionFactory;

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.todooz.service.TaskService#save(fr.todooz.domain.Task)
	 */
	@Override
	@Transactional
	public void save(Task task) {
		sessionFactory.getCurrentSession().save(task);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.todooz.service.TaskService#delete(java.lang.Long)
	 */
	@Override
	@Transactional
	public void delete(Long id) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		String hqlDelete = "delete Task t where t.id = :id";
		int deletedEntities = session.createQuery(hqlDelete).setLong("id", id)
				.executeUpdate();
		tx.commit();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.todooz.service.TaskService#findAll()
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Task> findAll() {
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(Task.class);
		List tasks = criteria.list();
		return tasks;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.todooz.service.TaskService#findByQuery(java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Task> findByQuery(String query) {
		Session session = sessionFactory.openSession();
		Criteria crit = session.createCriteria(Task.class);
		crit.add(Restrictions.like("text", '%' + query + '%').ignoreCase());
		List results = crit.list();
		return results;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.todooz.service.TaskService#count()
	 */
	@Override
	@Transactional(readOnly = true)
	public int count() {
		List tasks = this.findAll();
		return tasks.size();
	}

}
