package com.admin.dto;

import java.io.Serializable;

import common.util.PagingCommonVO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NoticePagingDto extends PagingCommonVO implements Serializable {
	
	private int startnum;	//현재 페이지 시작 인덱스
	private int lastnum;	//현재 페이지 끝 인덱스
	private int navStartNum;	//네비게이션 바 시작 인덱스
	private int navEndNum;	//네비게이션 바 끝 인덱스
	private int pageNum;	//현재 페이지 번호
	//총 페이지 개수 = super.pageSize
	
	/**현재 페이지 번호만을 받아서 페이징 객체 생성
	 * @param pageNum
	 */
	public NoticePagingDto(int pageNum) {
		super();
		this.pageNum = pageNum;
		this.setRecordCountPerPage(10);
		this.startnum = ( ( pageNum - 1 ) * this.getRecordCountPerPage() ) + 1;
		this.lastnum = startnum + this.getRecordCountPerPage() - 1;
		this.setNav();
	}
	
	
	/**현재 페이지 번호와 총 게시글의 수를 매개변수로 받아서 페이징 객체 생성
	 * @param pageNum
	 * @param totalSize
	 */
	public NoticePagingDto(int pageNum, int totalSize) {
		super();
		this.pageNum = pageNum;
		this.setRecordCountPerPage(10);
		this.startnum = ( ( pageNum - 1 ) * this.getRecordCountPerPage() ) + 1;
		this.lastnum = startnum + this.getRecordCountPerPage() - 1;
		//페이지 네비게이션의 사이즈 = (총글수-1)/(페이지당수)+1
		this.setPageSize( ( (totalSize-1) / this.getRecordCountPerPage() ) + 1 );
		this.setNav();
	}
	
	public NoticePagingDto(int startnum, int lastnum, int pageNum) {
		super();
		this.startnum = startnum;
		this.lastnum = lastnum;
		this.pageNum = pageNum;
		this.setRecordCountPerPage(10);
	}
	
	/**네비게이션 바 설정
	 * 
	 */
	public void setNav() {
		this.navStartNum = this.pageNum;
		this.navEndNum = this.navStartNum + getPageSize() - 1;
	}
}
