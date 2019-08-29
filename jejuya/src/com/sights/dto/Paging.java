package com.sights.dto;

import java.io.Serializable;

public class Paging implements Serializable{
	
	private int nowpage = 1;  // 현재페이지
	private int totalCount;		// row전체의 수 글의 수(get)
	private int beginPage;  // 출력시작
	private int endPage;	// 출력끝 
	private int displayRow = 5; // 한페이지에 몇 개의 글(set)
	private int displayPage = 2;	// 한번에 몇 개의 페이지(set)
	private int startNum;
	private int endNum;
	boolean prev;
	boolean next;
			
	public Paging() {		
	}
		
	public int getNowpage() {
		return nowpage;
	}

	public void setNowpage(int nowpage) {
		this.nowpage = nowpage;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		// setTotalCount를 호출해야 paging이 된다
		this.totalCount = totalCount;
		paging();
	}

	public int getBeginPage() {
		return beginPage;
	}

	public void setBeginPage(int beginPage) {
		this.beginPage = beginPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public int getDisplayRow() {
		return displayRow;
	}

	public void setDisplayRow(int displayRow) {
		this.displayRow = displayRow;
	}

	public int getDisplayPage() {
		return displayPage;
	}

	public void setDisplayPage(int displayPage) {
		this.displayPage = displayPage;
	}
		
	public int getStartNum() {
		startNum = (nowpage-1)*displayRow+1;
		return startNum;
	}

	public void setStartNum(int startNum) {
		this.startNum = startNum;
	}

	public int getEndNum() {
		endNum = nowpage*displayRow;
		return endNum;
	}

	public void setEndNum(int endNum) {
		this.endNum = endNum;
	}

	public boolean getPrev() {
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public boolean getNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

	private void paging() {
		// 현재 페이지가 3일때 1~10번까지 나타나게, 현재 페이지가 13이면 11~20까지
		// 소수점 나오면 +1페이지해야 되니까 올림(ceil)
		endPage = ((int)Math.ceil(nowpage / (double)displayPage)) * displayPage;	
		System.out.println("endPage=" + endPage);
		
		beginPage = endPage - (displayPage - 1);
		System.out.println("beginPage=" + beginPage);
		
		// 글 개수에 따른 총페이지
		int totalPage = (int)Math.ceil(totalCount/ (double)displayRow);
		
		if(totalPage <= endPage) {
			endPage = totalPage;
			next = false;
		}else {
			next = true;
		}		
		prev = (beginPage == 1)?false:true;	// 시작페이지가 1이면 prev 안나타남(false)
		System.out.println("endPage=" + endPage);
		System.out.println("totalPage=" + totalPage);
				
	}
	
	
}










