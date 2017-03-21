package com.chan.page.model;

import java.io.Serializable;
import java.util.List;


/**
 * @author LeoMs
 *
 * @param <T>
 */
/**
 * @author LeoMs
 *
 * @param <T>
 */
/**
 * @author LeoMs
 *
 * @param <T>
 */
public class Pager<T> implements Serializable {


	private static final long serialVersionUID = 1736066849610059848L;
	
	private int pageSize;		//每页显示多少记录
	private int currentPage;		//当前第几页数据
	private int totalRecord;		//一共多少条记录
	private int totalPage;		//一共多少页记录
	private List<T> dataList;		//要显示的数据
	
	
	
	public Pager() {
		super();
	}
	
	
	
	public Pager(int pageSize, int currentPage, int totalRecord, int totalPage, List<T> dataList) {
		super();
		this.pageSize = pageSize;
		this.currentPage = currentPage;
		this.totalRecord = totalRecord;
		this.totalPage = totalPage;
		this.setDataList(dataList);
	}
	
	
	/**
	 * @param pageNum	传入可能的当前页数
	 * @param pageSize	传入每页显示多少条数
	 * @param sourceList	数据数组
	 */
	public Pager(int pageNum, int pageSize, List<T> sourceList)
	{
		if(sourceList == null || sourceList.isEmpty())
		{
			return ;
		}
		
		//总记录条数
		this.totalRecord = sourceList.size();
		
		//每页显示条数
		this.pageSize = pageSize;
		
		//总页数
		this.totalPage = this.totalRecord / this.pageSize;
		if(this.totalRecord % this.pageSize != 0)
		{
			this.totalPage += 1;
		}
		
		//当前第几页数据
		this.currentPage = this.totalPage < pageNum ? this.totalPage : pageNum;
		
		//开始索引
		int fromIndex = this.pageSize * (this.currentPage - 1);
		
		//结束索引
		int toIndex = this.pageSize * this.currentPage > this.totalRecord ? this.totalRecord : this.pageSize * this.currentPage;
		
		this.setDataList(sourceList.subList(fromIndex, toIndex));
	}
	
	
	public int getPageSize() {
		return pageSize;
	}



	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}



	public int getCurrentPage() {
		return currentPage;
	}



	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}



	public int getTotalRecord() {
		return totalRecord;
	}



	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}



	public int getTotalPage() {
		return totalPage;
	}



	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}




	public List<T> getDataList() {
		return dataList;
	}



	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}
	
	

}
