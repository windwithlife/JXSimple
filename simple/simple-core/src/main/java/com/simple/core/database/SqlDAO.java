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
 * ����SqlDao�ӿڵ�ʵ����
 * 
 * @author hfren
 * 
 */
@Repository
@SuppressWarnings("all")
public class SqlDAO extends HibernateDaoSupport{
	/**
	 * log4j ��¼��
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
	 * ����sql��ѯ���ݿ�,����Ψһһ������
	 * 
	 * @author xxx
	 * @version 1.0 2010-1-4
	 * @param sql
	 * @param cls
	 *            ʵ��
	 * @param values
	 *            ����sql�е�?ռλ��
	 */
	public <T> T query(final String sql, final Class<T> cls, final Object... values) {
		try {
			if (log.isDebugEnabled()) {
				log.debug("��ʼ��ѯ����Ψһ�����SQL���," + sql);
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
			log.error("��ѯָ��SQL�쳣��SQL��" + sql);
			throw e;
		}
	}

	/**
	 * ��ѯָ��SQL�������ؼ���
	 * 
	 * @param sql
	 *            SQL���
	 * @param cls
	 *            ָ�������Classʵ������ Map����po
	 * @param values
	 *            �ɱ�Ĳ����б�
	 * @return list����
	 */
	//@Override
	public <T> List<T> queryAsList(final String sql, final Class cls, final Object... values) {
		try {
			if (log.isDebugEnabled()) {
				log.debug("��ʼ��ѯָ��SQL��䣺" + sql);
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
			log.error("��ѯָ��SQL�쳣��" + sql);
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * ִ���ض�hql����Ψһֵ
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
	 * ִ��count��ѯ��ñ���Sql��ѯ���ܻ�õĶ�������.
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
	 * ִ��sum��ѯ��ñ���Sql��ѯ���ܻ�õĶ��������.
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
			result.put(titleField, "�ϼƣ�");
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
	 * ��SQL��ҳ��ѯ.
	 * 
	 * @page pageҳ�����
	 * @param sql
	 *            SQL���
	 * @param cls
	 *            ʵ��
	 * @param values
	 *            �ɱ�����б�
	 * @return ��ҳ����
	 */
	public <T> Page<T> queryAsPage(final Page<T> page, final String sql, final Class cls, final Object... values) {
		try {
			if (log.isDebugEnabled()) {
				log.debug("��ʼ����ָ��sql��ҳ����," + sql);
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
						// �ܼ�¼��
						if (page.isAutoCount()) {
							Integer totalCount = new Integer(countSqlResult(sql, values).toString());
							page.setTotalCount(totalCount);
						}
						// ��¼�ϼ���
						if (page.getNeedSummary()) {
							page.getHjList().add(summarySqlResult(page, sql, values));
						}
						page.setResult(query.list());
					}
					catch (HibernateException e) {
						log.error("��ҳ��ѯ�쳣��" + sql);
						throw e;
					}
					return page;
				}
			});
		}
		catch (HibernateException e) {
			log.error("��ҳ��ѯ�쳣��sql��" + sql);
			throw e;
		}
	}

	/**
	 * ִ���������º�ɾ�������Լ���ѯ��SQL
	 * 
	 * @param sql
	 *            sql���
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
	 * ���롢���º�ɾ������
	 * 
	 * @param sql
	 *            sql���
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
	 * ִ���������º�ɾ�������Լ���ѯ��SQL
	 * 
	 * @param sql
	 *            sql���
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
