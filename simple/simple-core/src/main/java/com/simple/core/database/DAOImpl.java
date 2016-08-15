package com.simple.core.database;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;


@SuppressWarnings("all")
public class DAOImpl<T extends Object> extends HibernateDaoSupport implements DAO<T> {

	
	private static final Logger log = Logger.getLogger(DAOImpl.class);

	private boolean cacheQueries;

	public void setCacheQueries(boolean cacheQueries) {
		this.cacheQueries = cacheQueries;
	}

	@Autowired
	public void setMySessionFactory(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	public T get(Class<T> cls, Serializable id) {
		try {
			return (T) getHibernateTemplate().get(cls, id);
		}
		catch (DataAccessException e) {
			throw e;
		}
	}

	
	public T find(T entity) {
		List<T> list = getHibernateTemplate().findByExample(entity);
		if (list.size() > 1) {
			throw new RuntimeException("when query entity an error occurred, more than a record");
		}
		return list.size() > 0 ? list.get(0) : null;
	}

	
	public final <T> T find(final String hql, final Object... values) {
		try {
			return (T) getHibernateTemplate().executeWithNativeSession(new HibernateCallback<T>() {
				public T doInHibernate(Session s) throws HibernateException, SQLException {
					Query query = s.createQuery(hql);
					setParameters(query, values);
					return (T) query.uniqueResult();
				}
			});
		}
		catch (DataAccessException e) {
			log.error("query hql an error occurred, hql is " + hql);
			throw e;
		}
	}

	public final <T> T find(final String hql, final List<FilterData> filters, final Object... values) {
		try {
			return (T) getHibernateTemplate().executeWithNewSession(new HibernateCallback<T>() {
				public T doInHibernate(Session s) throws HibernateException, SQLException {
					Query query = s.createQuery(hql);
					addFilter(s, filters);
					setParameters(query, values);
					return (T) query.uniqueResult();
				}
			});
		}
		catch (DataAccessException e) {
			log.error("query hql an error occurred, hql is " + hql);
			throw e;
		}
	}

	
	public final T find(final String hql, final List<? super Object> param) {
		try {
			return (T) getHibernateTemplate().executeWithNewSession(new HibernateCallback<T>() {
				public T doInHibernate(Session s) throws HibernateException, SQLException {
					Query query = s.createQuery(hql);
					setParameters(query, param);
					return (T) query.uniqueResult();
				}
			});
		}
		catch (DataAccessException e) {
			log.error("query hql an error occurred, hql is " + hql);
			throw e;
		}
	}

	
	public void save(Object entity) {
		try {
			if (log.isDebugEnabled()) {
				log.debug("Save entity success,name is " + entity.getClass().getName());
			}
			getHibernateTemplate().save(entity);
		}
		catch (DataAccessException e) {
			log.error("Save entity exception, name is " + entity.getClass().getName());
			throw new RuntimeException(e);
		}
	}

	
	public void delete(Object entity) {
		try {
			if (log.isDebugEnabled()) {
				log.debug("Delete entity success, name is " + entity.getClass().getName());
			}
			getHibernateTemplate().delete(entity);
		}
		catch (DataAccessException e) {
			log.error("Delete entity an error occurred, name is " + entity.getClass().getName());
			throw e;
		}
	}

	
	public List<T> findAll(Class<T> cls) {
		try {
			return getHibernateTemplate().find("from " + cls.getName());
		}
		catch (DataAccessException e) {
			log.error("Query entity an error occurred, name is " + cls.getName());
			throw e;
		}
	}

	
	public void saveOrUpdate(Object entity) {
		try {
			getHibernateTemplate().saveOrUpdate(entity);
			if (log.isDebugEnabled()) {
				log.debug("update or save entity success!");
			}
		}
		catch (DataAccessException e) {
			log.error("update or save entity exception");
			throw e;
		}
	}

	
	public T findById(Serializable id, Class<T> cls) {
		try {
			return getHibernateTemplate().get(cls, id);
		}
		catch (DataAccessException e) {
			log.error("" + id);
			throw e;
		}
	}

	
	public List<T> loadAsList(T entity) {

		return getHibernateTemplate().findByExample(entity);
	}

	public <V> V loadAsList(final String hql, final List<? super Object> param) {
		return (V) getHibernateTemplate().executeWithNewSession(new HibernateCallback() {
			public V doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(hql);
				setParameters(query, param);
				return (V) query.list();
			}
		});
	}

	
	public <V> V loadAsList(final String hql, final Object... values) {
		// executeWithNativeSession鍦╤ibernate鐨勫欢杩熷姞杞介潪甯告湁鐢�,蹇呴』鍚屼竴涓猻ession
		return (V) getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {
			public V doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(hql);
				setParameters(query, values);
				return (V) query.list();
			}
		});
	}

	
	public <V> V loadAsList(final String hql, final List<FilterData> filters, final Object... values) {
		return (V) getHibernateTemplate().executeWithNewSession(new HibernateCallback() {
			public V doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(hql);
				addFilter(s, filters);
				setParameters(query, values);
				return (V) query.list();
			}
		});
	}

	
	public Map<String, Object> queryAsMap(String hql, Object... values) {
		List<Map<String, Object>> list = getHibernateTemplate().find(hql, values);
		return list.size() > 0 ? list.get(0) : new HashMap<String, Object>();
	}


	public List<Object[]> queryAsList(final String hql, final Object... values) {
		return (List<Object[]>) getHibernateTemplate().executeWithNewSession(new HibernateCallback() {
			public List<Object[]> doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(hql);
				setParameters(query, values);
				return query.list();
			}
		});
	}

	
	public List<T> findByProperty(String propertyName, Class<T> cls, Object... value) {
		try {
			String queryStr = "from " + cls.getName() + " as model where model." + propertyName + "=?";
			return getHibernateTemplate().find(queryStr, value);
		}
		catch (DataAccessException e) {
			log.error("鏌ユ壘鎸囧畾鏉′欢瀹炰綋闆嗗悎寮傚父锛屾潯浠讹細" + propertyName);
			throw e;
		}
	}

	
	public <V> Page<V> loadAsPage(final Page<V> page, final String hql, final Object... values) {
		try {
			return (Page<V>) getHibernateTemplate().executeWithNewSession(new HibernateCallback() {
				public Page<V> doInHibernate(Session s) throws HibernateException, SQLException {
					Query query = s.createQuery(hql);
					setParameters(query, values);
					if (page.isFirstSetted()) {
						query.setFirstResult(page.getFirst() - 1);
					}
					if (page.isPageSizeSetted()) {
						query.setMaxResults(page.getPageSize());
					}
					page.setResult(query.list());
					// 璁＄畻鎬昏褰曟暟
					Integer totalCount = new Integer(countHqlResult(hql, values).toString());
					page.setTotalCount(totalCount);
					return page;
				}
			});
		}
		catch (DataAccessException e) {
			log.error("鍒嗛〉鏌ヨ寮傚父锛孒QL锛�" + hql);
			throw e;
		}
	}

	
	public <V> Page<V> loadAsPage(final Page<V> page, final String hql, final List<? super Object> param) {
		try {
			return (Page<V>) getHibernateTemplate().executeWithNewSession(new HibernateCallback() {
				public Page<V> doInHibernate(Session s) throws HibernateException, SQLException {
					Query query = s.createQuery(hql);
					setParameters(query, param);
					if (page.isFirstSetted()) {
						query.setFirstResult(page.getFirst() - 1);
					}
					if (page.isPageSizeSetted()) {
						query.setMaxResults(page.getPageSize());
					}
					page.setResult(query.list());
					// 璁＄畻鎬昏褰曟暟
					Integer totalCount = new Integer(countHqlResult(hql, param).toString());
					page.setTotalCount(totalCount);
					return page;
				}
			});
		}
		catch (DataAccessException e) {
			log.error("鍒嗛〉鏌ヨ寮傚父锛孒QL锛�" + hql);
			throw e;
		}
	}

	
	public void update(Object obj) {
		try {
			getHibernateTemplate().update(obj);
		}
		catch (DataAccessException e) {
			log.error("Update entity :" + obj.getClass().getName() + " an error occurred!", e);
			throw e;
		}
	}

	//@Override
	public void batchSave(final Collection<T> obj) {
		try {
			getHibernateTemplate().executeWithNewSession(new HibernateCallback() {
				public Object doInHibernate(Session s) throws HibernateException, SQLException {
					int count = 0;
					if (obj.size() > 0) {
						for (T o : obj) {
							count++;
							s.save(o);
							if (count % 100 == 0) {
								s.flush();
								s.evict(o);
								s.clear();
							}
						}
					}
					return s;
				}
			});
		}
		catch (DataAccessException e) {
			log.error("Batch save an error occurred!");
			throw e;
		}

	}

	//@Override
	public void batchUpdate(final Collection<T> obj) {
		try {
			getHibernateTemplate().executeWithNewSession(new HibernateCallback() {
				public Object doInHibernate(Session s) throws HibernateException, SQLException {
					int rows = 0;
					if (obj.size() > 0) {
						for (T o : obj) {
							s.update(o);
							rows++;
							if (rows % 100 == 0) {
								s.flush();
								s.evict(o);
								s.clear();
							}
						}
					}
					return s;
				}
			});
		}
		catch (DataAccessException e) {
			log.error("Batch update an error occurred!");
			throw e;
		}
	}

	//@Override
	public int batchUpdate(String hql, Object... values) {
		return getHibernateTemplate().bulkUpdate(hql, values);
	}

	public <V> V merge(V entity) {
		return getHibernateTemplate().merge(entity);
	}

	
	public Long countHqlResult(final String hql, final Object... values) {
		final String ccountHql = prepareCountHql(hql);
		try {
			return (Long) getHibernateTemplate().executeWithNewSession(new HibernateCallback() {
				public Long doInHibernate(Session s) throws HibernateException, SQLException {
					Query query = s.createQuery(ccountHql);
					setParameters(query, values);
					return (Long) query.uniqueResult();
				}
			});
		}
		catch (DataAccessException e) {
			throw e;
		}
	}

	
	public Long countHqlResult(final String hql, final List<? super Object> param) {
		String ccountHql = prepareCountHql(hql);
		try {
			return (Long) getHibernateTemplate().executeWithNewSession(new HibernateCallback() {
				public Long doInHibernate(Session s) throws HibernateException, SQLException {
					Query query = s.createQuery(hql);
					setParameters(query, param);
					return (Long) query.uniqueResult();
				}
			});
		}
		catch (DataAccessException e) {
			throw e;
		}
	}

	
	public <V> V uniqueResult(final String hql, final Object... values) {
		try {
			return (V) getHibernateTemplate().executeWithNewSession(new HibernateCallback<V>() {
				public V doInHibernate(Session s) throws HibernateException, SQLException {
					Query query = s.createQuery(hql);
					setParameters(query, values);
					return (V) query.uniqueResult();
				}
			});
		}
		catch (DataAccessException e) {
			throw e;
		}
	}

	private String prepareCountHql(String orgHql) {
		String fromHql = orgHql;
		// select瀛愬彞涓巓rder by瀛愬彞浼氬奖鍝峜ount鏌ヨ,杩涜绠�鍗曠殑鎺掗櫎.
		fromHql = "from " + StringUtils.substringAfter(fromHql, "from");
		fromHql = StringUtils.substringBefore(fromHql, "order by");

		String countHql = "select count(*) " + fromHql;
		return countHql;
	}

	public <V> V reloadAsList(final String hql, final Object... values) {
		return (V) getHibernateTemplate().executeWithNewSession(new HibernateCallback() {
			public V doInHibernate(Session s) throws HibernateException, SQLException {
				Query query = s.createQuery(hql);
				query.setCacheable(false);
				return (V) query.list();
			}
		});
	}

	private Query setParameters(Query query, Object... objects) {
		if (objects != null) {
			for (int i = 0; i < objects.length; i++)
				query.setParameter(i, objects[i]);
		}
		if (cacheQueries) {
			query.setCacheable(cacheQueries);
		}
		return query;
	}

	private Query setParameters(Query query, List<? super Object> param) {
		if (param != null) {
			for (int i = 0; i < param.size(); i++)
				query.setParameter(i, param.get(i));
		}
		if (cacheQueries) {
			query.setCacheable(cacheQueries);
		}
		return query;
	}

	private void addFilter(Session s, List<FilterData> filters) {
		if (null != filters && filters.size() > 0) {
			for (FilterData filter : filters) {
				s.enableFilter(filter.getFilterDef()).setParameter(filter.getFilterParamName(),
						filter.getFilterParamValue());
			}
		}
	}

	
	public HibernateTemplate getTemplate() {
		  return getHibernateTemplate();
		}
	
}
