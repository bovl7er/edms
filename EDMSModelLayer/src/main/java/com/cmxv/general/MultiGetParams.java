package com.cmxv.general;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MultiGetParams {
	public MultiGetParams(){};
	private SortOrder sortOrder;
	private String sortColumn;
	private Integer pageNum;
	private Integer recsOnPage;
	public SortOrder getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(SortOrder sortOrder) {
		this.sortOrder = sortOrder;
	}
	public String getSortColumn() {
		return sortColumn;
	}
	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	public Integer getRecsOnPage() {
		return recsOnPage;
	}
	public void setRecsOnPage(Integer recsOnPage) {
		this.recsOnPage = recsOnPage;
	}
}
