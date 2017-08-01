package org.kyll.common.paginated;

import org.kyll.common.Const;

import java.util.List;

/**
 * User: Kyll
 * Date: 2014-11-05 13:59
 */
public class Paginated {
	private Integer totalRecord;
	private Integer totalPage;
	private Integer currentRecord;
	private Integer currentPage;
	private Integer firstPage;
	private Integer lastPage;
	private Integer prevPage;
	private Integer nextPage;
	private Integer prevOnePage;
	private Integer nextOnePage;
	private Integer duePage;
	private Integer startRecord;
	private Integer maxRecord;

	private List<Sort> sortList;

	public Paginated() {
		this.startRecord = Const.DEFAULT_PAGINATED_STARTRECORD;
		this.maxRecord = Const.DEFAULT_PAGINATED_MAXRECORD;
		this.duePage = Const.DEFAULT_PAGINATED_DUEPAGE;
	}

	public Integer getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(Integer totalRecord) {
		this.totalRecord = totalRecord;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public Integer getCurrentRecord() {
		return currentRecord;
	}

	public void setCurrentRecord(Integer currentRecord) {
		this.currentRecord = currentRecord;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getFirstPage() {
		return firstPage;
	}

	public void setFirstPage(Integer firstPage) {
		this.firstPage = firstPage;
	}

	public Integer getPrevPage() {
		return prevPage;
	}

	public void setPrevPage(Integer prevPage) {
		this.prevPage = prevPage;
	}

	public Integer getPrevOnePage() {
		return prevOnePage;
	}

	public void setPrevOnePage(Integer prevOnePage) {
		this.prevOnePage = prevOnePage;
	}

	public Integer getDuePage() {
		return duePage;
	}

	public void setDuePage(Integer duePage) {
		this.duePage = duePage;
	}

	public Integer getNextOnePage() {
		return nextOnePage;
	}

	public void setNextOnePage(Integer nextOnePage) {
		this.nextOnePage = nextOnePage;
	}

	public Integer getNextPage() {
		return nextPage;
	}

	public void setNextPage(Integer nextPage) {
		this.nextPage = nextPage;
	}

	public Integer getLastPage() {
		return lastPage;
	}

	public void setLastPage(Integer lastPage) {
		this.lastPage = lastPage;
	}

	public Integer getStartRecord() {
		return startRecord;
	}

	public void setStartRecord(Integer startRecord) {
		this.startRecord = startRecord;
	}

	public Integer getMaxRecord() {
		return maxRecord;
	}

	public void setMaxRecord(Integer maxRecord) {
		this.maxRecord = maxRecord;
	}

	public List<Sort> getSortList() {
		return sortList;
	}

	public void setSortList(List<Sort> sortList) {
		this.sortList = sortList;
	}
}
