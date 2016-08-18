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

	
	public String getInverseOrder() {
		if (order.endsWith(DESC))
			return ASC;
		else
			return DESC;
	}

	
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

	
	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}


	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	
	public Integer getTotalPages() {
		if (totalCount == -1)
			return -1;

		Integer count = totalCount / pageSize;
		if (totalCount % pageSize > 0) {
			count++;
		}
		return new Integer(count);
	}

	
	public boolean isHasNext() {
		return (pageNo + 1 <= getTotalPages());
	}


	public Integer getNextPage() {
		if (isHasNext())
			return pageNo + 1;
		else
			return pageNo;
	}

	
	public boolean isHasPre() {
		return (pageNo - 1 >= 1);
	}

	public Integer getPrePage() {
		if (isHasPre())
			return pageNo - 1;
		else
			return pageNo;
	}

	
	public Integer getPageSize() {
		return pageSize;
	}

	
	public Page<T> pageSize(final Integer thePageSize) {
		setPageSize(thePageSize);
		return this;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}


	public boolean isPageSizeSetted() {
		return pageSize > -1;
	}

	
	public Integer getPageNo() {
		return pageNo;
	}

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

	
	public Integer getFirst() {
		if (pageNo < 1 || pageSize < 1)
			return -1;
		else
			return ((pageNo - 1) * pageSize + 1);
	}

	
	public boolean isFirstSetted() {
		return (pageNo > 0 && pageSize > 0);
	}

	public String getOrderBy() {
		return orderBy;
	}

	
	public Page<T> orderBy(final String theOrderBy) {
		setOrderBy(theOrderBy);
		return this;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	
	public String getOrder() {
		return order;
	}

	
	public Page<T> order(final String theOrder) {
		setOrder(theOrder);
		return this;
	}

	
	public void setOrder(String order) {
		if (ASC.equalsIgnoreCase(order) || DESC.equalsIgnoreCase(order)) {
			this.order = order.toLowerCase();
		}
		else
			throw new IllegalArgumentException("order should be 'desc' or 'asc'");
	}

	public boolean isAutoCount() {
		return autoCount;
	}


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
