package com.sights.dto;

import common.util.PagingCommonVO;

public class SightPagingDto extends PagingCommonVO{
	
	//페이지 별 시작 인덱스
	private int startRnum;
	
	//페이지 별 마지막 인덱스
	private int endRnum;
	
	public SightPagingDto() {
		super();
		this.setRecordCountPerPage(6);
		this.startRnum = ( (getPageIndex() -1 ) * getRecordCountPerPage() ) + 1 ;
		this.endRnum = this.startRnum + getRecordCountPerPage();
	}
	
	public SightPagingDto( int pageNum ) {
		super();
		this.setPageIndex( pageNum );
		this.setRecordCountPerPage(6);
		this.startRnum = ( (getPageIndex() -1 ) * getRecordCountPerPage() ) + 1 ;
		this.endRnum = this.startRnum + getRecordCountPerPage() - 1 ;
	}

	public int getStartRnum() {
		return startRnum;
	}

	public void setStartRnum(int startRnum) {
		this.startRnum = startRnum;
	}

	public int getEndRnum() {
		return endRnum;
	}

	public void setEndRnum(int endRnum) {
		this.endRnum = endRnum;
	}
	
	
}
