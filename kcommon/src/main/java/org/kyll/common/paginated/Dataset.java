package org.kyll.common.paginated;

import java.util.List;

/**
 * User: Kyll
 * Date: 2014-11-05 14:00
 */
public class Dataset<T> {
	private Paginated paginated;
	private List<T> dataList;

	public Dataset() {
	}

	public Paginated getPaginated() {
		return paginated;
	}

	public void setPaginated(Paginated paginated) {
		this.paginated = paginated;
	}

	public List<T> getDataList() {
		return dataList;
	}

	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}

	public static <T> Dataset<T> create(Paginated paginated, Long totalRecord, List<T> dataList) {
		return create(paginated, totalRecord.intValue(), dataList);
	}

	public static <T> Dataset<T> create(Paginated paginated, Integer totalRecord, List<T> dataList) {
		paginated.setTotalRecord(totalRecord);
		paginated.setTotalPage(totalRecord % paginated.getMaxRecord() == 0 ? totalRecord / paginated.getMaxRecord() : totalRecord / paginated.getMaxRecord() + 1);
		paginated.setCurrentPage(paginated.getStartRecord() / paginated.getMaxRecord() + 1);
		paginated.setFirstPage(0);
		paginated.setLastPage((paginated.getTotalPage() - 1) * paginated.getMaxRecord());
		paginated.setPrevPage(paginated.getStartRecord() == 0 ? 0 : (paginated.getStartRecord() - (paginated.getMaxRecord() * paginated.getDuePage()) > 0 ? paginated.getStartRecord() - (paginated.getMaxRecord() * paginated.getDuePage()) : 0));
		paginated.setNextPage(paginated.getStartRecord() + (paginated.getMaxRecord() * paginated.getDuePage()) < paginated.getTotalRecord() ? paginated.getStartRecord() + (paginated.getMaxRecord() * paginated.getDuePage()): paginated.getLastPage());
		paginated.setPrevOnePage(paginated.getStartRecord() == 0 ? 0 : paginated.getStartRecord() - paginated.getMaxRecord());
		paginated.setNextOnePage(paginated.getStartRecord() + paginated.getMaxRecord() < paginated.getTotalRecord() ? paginated.getStartRecord() + paginated.getMaxRecord() : paginated.getLastPage());

		Dataset<T> dataset = new Dataset<>();
		dataset.setPaginated(paginated);
		dataset.setDataList(dataList);
		return dataset;
	}
}
