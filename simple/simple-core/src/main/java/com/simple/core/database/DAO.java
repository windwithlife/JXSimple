package com.simple.core.database;


import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate3.HibernateTemplate;





public interface DAO<T> {

	
	T get(Class<T> cls, Serializable id);

	
	T find(T entity);


	<V> V find(String hql, Object... values);

	
	<V> V find(final String hql, final List<FilterData> filters, final Object... values);

	
	T find(String hql, List<? super Object> param);

	
	void save(Object entity);

	
	void delete(Object entity);

	
	List<T> findAll(Class<T> cls);

	
	void saveOrUpdate(Object po);

	T findById(Serializable id, Class<T> cls);

	
	<v> v loadAsList(final String hql, final Object... values);

	
	<v> v reloadAsList(final String hql, final Object... values);

	
	<v> v loadAsList(final String hql, final List<FilterData> filters, final Object... values);

	<v> v loadAsList(String hql, List<? super Object> param);

	
	List<T> loadAsList(T entity);

	
	Map<String, Object> queryAsMap(String hql, Object... values);

	
	public List<Object[]> queryAsList(String hql, Object... values);

	
	List<T> findByProperty(String propertyName, Class<T> cls, Object... value);

	
	public <V> Page<V> loadAsPage(final Page<V> page, final String hql, final Object... values);

	
	public <V> Page<V> loadAsPage(final Page<V> page, final String hql, final List<? super Object> param);

	
	void update(Object obj);

	
	void batchSave(final Collection<T> obj);

	int batchUpdate(String hql, Object... values);

	
	void batchUpdate(final Collection<T> obj);

	
	<V> V merge(V entity);

	
	Long countHqlResult(String hql, Object... values);

	
	Long countHqlResult(String hql, List<? super Object> param);

	<V> V uniqueResult(String hql, Object... values);

	
	HibernateTemplate getHibernateTemplate() ;
	
}
