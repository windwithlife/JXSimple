package com.simple.core.database;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;


public class Page<T> {

	public static final String ASC = "asc";
	public static final String DESC = "desc";

	private Integer defaultPageSize = 20;
	private Integer totalPage = -1;
	private Integer totalCount = -1;
	protected Integer pageNo = 1;
	protected Integer pageSize = -1;
	protected String orderBy = null;
	protected String order = ASC;
	protected boolean autoCount = true;
	protected boolean needSummary = false;

	private List<SummaryField> summaryFieldList = Lists.newArrayList();
	private List<Map<String, Object>> hjList = Lists.newLinkedList();

	private List<T> result = new ArrayList<T>();

	public Page() {
	}

	public Page(Integer pageSize) {
		this.pageSize = pageSize;
		if (null == pageSize) {
			this.pageSize = 20;
		}
	}

	public Page(String pageSize) {
		this.pageSize = StringUtils.isNotBlank(pageSize) ? Integer.parseInt(pageSize) : defaultPageSize;
	}

	public Page(Integer pageSize, boolean autoCount) {
		this.pageSize = pageSize;
		this.autoCount = autoCount;
		if (null == pageSize) {
			this.pageSize = defaultPageSize;
		}
	}

	/**
	 * 鍙栧緱鍊掕浆鐨勬帓搴忔柟鍚�
	 */
	public String getInverseOrder() {
		if (order.endsWith(DESC))
			return ASC;
		else
			return DESC;
	}

	/**
	 * 椤靛唴鐨勬暟鎹垪琛�.
	 */

	public List<T> getResult() {
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
		if (this.needSummary) {
			try {
				this.hjList.add(summaryRecordForCurrentPage());
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 鎬婚〉鏁�
	 * 
	 * @return
	 */
	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	/**
	 * 鎬昏褰曟暟.
	 */
	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * 璁＄畻鎬婚〉鏁�.
	 */
	public Integer getTotalPages() {
		if (totalCount == -1)
			return -1;

		Integer count = totalCount / pageSize;
		if (totalCount % pageSize > 0) {
			count++;
		}
		return new Integer(count);
	}

	/**
	 * 鏄惁杩樻湁涓嬩竴椤�.
	 */
	public boolean isHasNext() {
		return (pageNo + 1 <= getTotalPages());
	}

	/**
	 * 杩斿洖涓嬮〉鐨勯〉鍙�,搴忓彿浠�1寮�濮�.
	 */
	public Integer getNextPage() {
		if (isHasNext())
			return pageNo + 1;
		else
			return pageNo;
	}

	/**
	 * 鏄惁杩樻湁涓婁竴椤�.
	 */
	public boolean isHasPre() {
		return (pageNo - 1 >= 1);
	}

	/**
	 * 杩斿洖涓婇〉鐨勯〉鍙�,搴忓彿浠�1寮�濮�.
	 */
	public Integer getPrePage() {
		if (isHasPre())
			return pageNo - 1;
		else
			return pageNo;
	}

	/**
	 * 鑾峰緱姣忛〉鐨勮褰曟暟閲�,鏃犻粯璁ゅ��.
	 */
	public Integer getPageSize() {
		return pageSize;
	}

	/**
	 * 杩斿洖Page瀵硅薄鑷韩鐨剆etPageSize鍑芥暟,鍙敤浜庤繛缁缃��
	 */
	public Page<T> pageSize(final Integer thePageSize) {
		setPageSize(thePageSize);
		return this;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 鏄惁宸茶缃瘡椤电殑璁板綍鏁伴噺.
	 */
	public boolean isPageSizeSetted() {
		return pageSize > -1;
	}

	/**
	 * 鑾峰緱褰撳墠椤电殑椤靛彿,搴忓彿浠�1寮�濮�,榛樿涓�1.
	 */
	public Integer getPageNo() {
		return pageNo;
	}

	/**
	 * 杩斿洖Page瀵硅薄鑷韩鐨剆etPageNo鍑芥暟,鍙敤浜庤繛缁缃��
	 */
	public Page<T> pageNo(final String thePageNo) {
		setPageNo(thePageNo);
		return this;
	}

	public void setPageNo(final String pageNo) {
		if (StringUtils.isNotBlank(pageNo)) {
			this.pageNo = new Integer(pageNo);
			if (new Integer(pageNo) < 1) {
				this.pageNo = 1;
			}
		}
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
		if (null == pageNo || new Integer(pageNo) < 1) {
			this.pageNo = 1;
		}
	}

	/**
	 * 鏍规嵁pageNo鍜宲ageSize璁＄畻褰撳墠椤电涓�鏉¤褰曞湪鎬荤粨鏋滈泦涓殑浣嶇疆,搴忓彿浠�0寮�濮�.
	 */
	public Integer getFirst() {
		if (pageNo < 1 || pageSize < 1)
			return -1;
		else
			return ((pageNo - 1) * pageSize + 1);
	}

	/**
	 * 鏄惁宸茶缃涓�鏉¤褰曡褰曞湪鎬荤粨鏋滈泦涓殑浣嶇疆.
	 */
	public boolean isFirstSetted() {
		return (pageNo > 0 && pageSize > 0);
	}

	/**
	 * 鑾峰緱鎺掑簭瀛楁,鏃犻粯璁ゅ��.
	 */
	public String getOrderBy() {
		return orderBy;
	}

	/**
	 * 杩斿洖Page瀵硅薄鑷韩鐨剆etOrderBy鍑芥暟,鍙敤浜庤繛缁缃��
	 */
	public Page<T> orderBy(final String theOrderBy) {
		setOrderBy(theOrderBy);
		return this;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	/**
	 * 鑾峰緱鎺掑簭鏂瑰悜,榛樿涓篴sc.
	 */
	public String getOrder() {
		return order;
	}

	/**
	 * 杩斿洖Page瀵硅薄鑷韩鐨剆etOrder鍑芥暟,鍙敤浜庤繛缁缃��
	 */
	public Page<T> order(final String theOrder) {
		setOrder(theOrder);
		return this;
	}

	/**
	 * 璁剧疆鎺掑簭鏂瑰紡鍚�.
	 * 
	 * @param order
	 *            鍙�夊�间负desc鎴朼sc.
	 */
	public void setOrder(String order) {
		if (ASC.equalsIgnoreCase(order) || DESC.equalsIgnoreCase(order)) {
			this.order = order.toLowerCase();
		}
		else
			throw new IllegalArgumentException("order should be 'desc' or 'asc'");
	}

	/**
	 * 鏄惁鑷姩鑾峰彇鎬婚〉鏁�,榛樿涓篺alse. 娉ㄦ剰鏈睘鎬т粎浜巕uery by Criteria鏃舵湁鏁�,query by HQL鏃舵湰灞炴�ф棤鏁�.
	 */
	public boolean isAutoCount() {
		return autoCount;
	}

	/**
	 * 杩斿洖Page瀵硅薄鑷韩鐨剆etAutoCount鍑芥暟,鍙敤浜庤繛缁缃��
	 */
	public Page<T> autoCount(final boolean theAutoCount) {
		setAutoCount(theAutoCount);
		return this;
	}

	public void setAutoCount(boolean autoCount) {
		this.autoCount = autoCount;
	}

	public boolean getNeedSummary() {
		return needSummary;
	}

	public void setNeedSummary(boolean needSummary) {
		this.needSummary = needSummary;
	}

	public List<Map<String, Object>> getHjList() {
		return hjList;
	}

	public void setHjList(List<Map<String, Object>> hjList) {
		this.hjList = hjList;
	}

	public List<SummaryField> getSummaryFieldList() {
		return summaryFieldList;
	}

	public void setSummaryFieldList(List<SummaryField> summaryFieldList) {
		this.summaryFieldList = summaryFieldList;
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> summaryRecordForCurrentPage() throws Exception {
		Map<String, Object> summaryXjMap = Maps.newHashMapWithExpectedSize(1);
		if (this.result.size() > 0) {
			for (SummaryField field : this.summaryFieldList) {
				BigDecimal value = new BigDecimal("0.00");
				String propertyName = field.getPropertyName();
				if (!"comment".equals(field.getPropertyValue())) {
					for (T m : this.result) {
						if (m instanceof Map) {
							value = value.add(new BigDecimal(((Map<String, Object>) m).get(propertyName).toString()));
						}
						else {
							value = value.add(new BigDecimal(BeanUtils.getProperty(m, propertyName).toString()));
						}
					}
					summaryXjMap.put(propertyName, value.setScale(2, BigDecimal.ROUND_HALF_UP));
				}
				else {
					summaryXjMap.put(propertyName, "灏忚锛�");
				}
			}
		}
		return summaryXjMap;
	}

}
