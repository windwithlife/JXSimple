package com.simple.core.database;


import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;



/**
 * 基于SqlDao接口的实现类
 * 
 * @author hfren
 * 
 */
@Repository
@SuppressWarnings("all")
public class SqlDAO extends HibernateDaoSupport{
	/**
	 * log4j 记录器
	 */
	private static final Logger log = Logger.getLogger(SqlDAO.class);

	private boolean cacheQueries;

	public void setCacheQueries(boolean cacheQueries) {
		this.cacheQueries = cacheQueries;
	}
	
	@Autowired
	public void setMySessionFactory(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}

	/**
	 * 根据sql查询数据库,返回唯一一条数据
	 * 
	 * @author xxx
	 * @version 1.0 2010-1-4
	 * @param sql
	 * @param cls
	 *            实例
	 * @param values
	 *            代替sql中的?占位符
	 */
	public <T> T query(final String sql, final Class<T> cls, final Object... values) {
		try {
			if (log.isDebugEnabled()) {
				log.debug("开始查询返回唯一结果的SQL语句," + sql);
			}
			return getHibernateTemplate().executeWithNewSession(new HibernateCallback<T>() {
				public T doInHibernate(Session s) throws HibernateException, SQLException {
					Query query = s.createSQLQuery(sql);
					query = setParameters(query, cls, values);
					return (T) query.uniqueResult();
				}
			});
		}
		catch (RuntimeException e) {
			log.error("查询指定SQL异常，SQL：" + sql);
			throw e;
		}
	}

	/**
	 * 查询指定SQL，并返回集合
	 * 
	 * @param sql
	 *            SQL语句
	 * @param cls
	 *            指定对象的Class实例类型 Map或者po
	 * @param values
	 *            可变的参数列表
	 * @return list集合
	 */
	//@Override
	public <T> List<T> queryAsList(final String sql, final Class cls, final Object... values) {
		try {
			if (log.isDebugEnabled()) {
				log.debug("开始查询指定SQL语句：" + sql);
			}
			return getHibernateTemplate().executeWithNewSession(new HibernateCallback<List<T>>() {
				public List<T> doInHibernate(Session s) throws HibernateException, SQLException {
					Query query = s.createSQLQuery(sql);
					query = setParameters(query, cls, values);
					return (List<T>) query.list();
				}
			});
		}
		catch (RuntimeException e) {
			log.error("查询指定SQL异常：" + sql);
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 执行特定hql返回唯一值
	 * 
	 * @param hql
	 * @param values
	 * @return
	 */
	public <T> T uniqueResult(final String sql, final Object... values) {
		try {
			return getHibernateTemplate().executeWithNewSession(new HibernateCallback<T>() {
				public T doInHibernate(Session s) throws HibernateException, SQLException {
					Query query = s.createSQLQuery(sql);
					query = setParameters(query, values);
					return (T) query.uniqueResult();
				}
			});
		}
		catch (DataAccessException e) {
			throw e;
		}
	}

	/**
	 * 执行count查询获得本次Sql查询所能获得的对象总数.
	 * 
	 * @param sql
	 * @param values
	 * @return
	 */
	public Long countSqlResult(final String sql, final Object... values) {
		try {
			return getHibernateTemplate().executeWithNewSession(new HibernateCallback<Long>() {
				public Long doInHibernate(Session s) throws HibernateException, SQLException {
					String countSql = prepareCountSql(sql);
					Query query = s.createSQLQuery(countSql);
					query = setParameters(query, values);
					return Long.parseLong(query.uniqueResult().toString());
				}
			});
		}
		catch (HibernateException e) {
			log.error("sql can't be auto count,sql is :" + sql);
			throw e;
		}
	}

	private String prepareCountSql(String orgSql) {
		String fromSql = orgSql;
		String countSql = "select count(*) from (" + fromSql + ") _table";
		return countSql;
	}

	/**
	 * 执行sum查询获得本次Sql查询所能获得的对象汇总数.
	 * 
	 * @param page
	 * @param sql
	 * @param values
	 * @return
	 */
	public Map<String, Object> summarySqlResult(final Page page, final String sql, final Object... values) {
		int index = 0;
		String titleField = "";
		List<SummaryField> summaryFieldList = page.getSummaryFieldList();
		String[] fieldsString = new String[summaryFieldList.size() - 1];
		for (SummaryField field : summaryFieldList) {
			if (!"comment".equals(field.getPropertyValue())) {
				fieldsString[index++] = field.getPropertyValue() + " " + field.getPropertyName();
			}
			else {
				titleField = field.getPropertyName();
			}
		}
		String summarySql = prepareSummarySql(sql, StringUtils.join(fieldsString, ","), page);
		try {
			Map<String, Object> result = this.query(summarySql, Map.class, values);
			result.put(titleField, "合计：");
			return result;
		}
		catch (HibernateException e) {
			log.error("sql can't be auto summary,sql is :" + sql);
			throw e;
		}
	}

	private String prepareSummarySql(String orgSql, String fieldNames, Page page) {
		String fromSql = orgSql;
		fromSql = " from " + StringUtils.substringAfterLast(fromSql, "from");
		fromSql = StringUtils.substringBefore(fromSql, "group by");
		String summarySql = "select " + fieldNames + fromSql;
		return summarySql;
	}

	/**
	 * 按SQL分页查询.
	 * 
	 * @page page页面对象
	 * @param sql
	 *            SQL语句
	 * @param cls
	 *            实体
	 * @param values
	 *            可变参数列表
	 * @return 分页数据
	 */
	public <T> Page<T> queryAsPage(final Page<T> page, final String sql, final Class cls, final Object... values) {
		try {
			if (log.isDebugEnabled()) {
				log.debug("开始查找指定sql分页数据," + sql);
			}
			return getHibernateTemplate().executeWithNewSession(new HibernateCallback<Page<T>>() {
				public Page<T> doInHibernate(Session s) throws HibernateException, SQLException {
					try {
						Query query = s.createSQLQuery(sql);
						setParameters(query, cls, values);

						if (page.isFirstSetted()) {
							query.setFirstResult(page.getFirst() - 1);
						}
						if (page.isPageSizeSetted()) {
							query.setMaxResults(page.getPageSize());
						}
						// 总记录数
						if (page.isAutoCount()) {
							Integer totalCount = new Integer(countSqlResult(sql, values).toString());
							page.setTotalCount(totalCount);
						}
						// 记录合计数
						if (page.getNeedSummary()) {
							page.getHjList().add(summarySqlResult(page, sql, values));
						}
						page.setResult(query.list());
					}
					catch (HibernateException e) {
						log.error("分页查询异常：" + sql);
						throw e;
					}
					return page;
				}
			});
		}
		catch (HibernateException e) {
			log.error("分页查询异常，sql：" + sql);
			throw e;
		}
	}

	/**
	 * 执行批量更新和删除操作以及查询的SQL
	 * 
	 * @param sql
	 *            sql语句
	 * @return list
	 */
	public final int update(final String sql, final List<? super Object> param) {
		try {
			getHibernateTemplate().executeWithNewSession(new HibernateCallback() {
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					Query query = session.createSQLQuery(sql);
					if (param != null && param.size() > 0) {
						for (int i = 0; i < param.size(); i++) {
							query.setParameter(i, param.get(i));
						}
					}
					log.debug("update sql successed:" + sql);
					return query.executeUpdate();
				}
			});
		}
		catch (HibernateException e) {
			log.error("update sql error:" + sql);
			throw e;
		}
		return 0;
	}

	/**
	 * 插入、更新和删除操作
	 * 
	 * @param sql
	 *            sql语句
	 * @return list
	 */
	public final int update(final String sql, final Object... objects) {
		try {
			getHibernateTemplate().executeWithNewSession(new HibernateCallback() {
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					Query query = session.createSQLQuery(sql);
					setParameters(query, objects);
					log.debug("update sql successed:" + sql);
					return query.executeUpdate();
				}
			});
		}
		catch (HibernateException e) {
			log.error("update sql error:" + sql);
			throw e;
		}
		return 0;
	}

	/**
	 * 执行批量更新和删除操作以及查询的SQL
	 * 
	 * @param sql
	 *            sql语句
	 * @return list
	 */
	//@Override
	public void batchUpdate(final Collection<String> sqlList) {
		try {
			getHibernateTemplate().executeWithNewSession(new HibernateCallback() {
				public Object doInHibernate(Session s) throws HibernateException, SQLException {
					int rows = 0;
					if (sqlList.size() > 0) {
						for (String sql : sqlList) {
							s.createSQLQuery(sql).executeUpdate();
							if (rows++ % 1000 == 0) {
								s.flush();
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

	private Query setParameters(Query query, Object... objects) {
		if (objects != null) {
			for (int i = 0; i < objects.length; i++)
				query.setParameter(i, objects[i]);
		}
		return query;
	}

	private Query setParameters(Query query, Class<?> cls, Object... objects) {
		if (objects != null) {
			for (int i = 0; i < objects.length; i++)
				query.setParameter(i, objects[i]);
		}
		if (cls.getName().equals("java.util.Map")) {
			query = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		}
		else {
			query = query.setResultTransformer(new ResultToBeanTransformers(cls));
			//query = query.setResultTransformer(Transformers.aliasToBean(cls));	
		}
		return query;
	}
}
