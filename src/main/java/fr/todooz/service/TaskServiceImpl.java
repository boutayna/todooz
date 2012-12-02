package fr.todooz.service;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.joda.time.Interval;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.todooz.domain.Task;

@Service
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
		sessionFactory.getCurrentSession().saveOrUpdate(task);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.todooz.service.TaskService#delete(java.lang.Long)
	 */
	@Override
	@Transactional
	public void delete(Long id) {
		Session session = sessionFactory.getCurrentSession();
		String hqlDelete = "delete Task t where t.id = :id";
		session.createQuery(hqlDelete).setLong("id", id)
				.executeUpdate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.todooz.service.TaskService#findAll()
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Task> findAll() {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Task.class);
		criteria.addOrder(Order.desc("date"));
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
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Task.class);
		crit.add(Restrictions.ilike("title", query, MatchMode.ANYWHERE));
		crit.addOrder(Order.desc("date"));
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

	@Override
	@Transactional(readOnly = true)
	public List<Task> findByTag(String tag) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Task.class);
		crit.add(Restrictions.ilike("tags", tag, MatchMode.ANYWHERE));
		crit.addOrder(Order.desc("date"));
		List results = crit.list();
		return results;
	
	}


	@Override
	@Transactional(readOnly = true)
	public List<Task> findByInterval(Interval interval) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Task.class);
		crit.add(Restrictions.between("date",interval.getStart().toDate(),interval.getEnd().toDate()));
		crit.addOrder(Order.desc("date"));
		List results = crit.list();
		return results;	
	}

	@Override
	@Transactional(readOnly = true)
	public Task findById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Task.class);
		crit.add(Restrictions.eq("id",id));
		 List results = crit.list();
		return (Task) results.get(0);
	}

}
