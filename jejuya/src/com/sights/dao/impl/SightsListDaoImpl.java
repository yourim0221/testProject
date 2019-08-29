package com.sights.dao.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.sights.dao.SightsListDao;
import com.sights.dto.SightPagingDto;
import com.sights.dto.SightSortCondition;
import com.sights.dto.SightsDto;

import common.db.DBClose;
import common.db.DBConnection;


public class SightsListDaoImpl implements SightsListDao, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private static SightsListDaoImpl dao = null;
	
	
	private SightsListDaoImpl() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static SightsListDaoImpl getInstance() {
		if(dao == null) {
			dao = new SightsListDaoImpl();
		}
		return dao;
	}
	
	
	
	// alt shift j 
	/**
	 *	매개변수로 받은 관광지 번호를 가지고 DB에서 관광지정보 하나를 찾아서 리턴해주는 메소드
	 */
	public SightsDto getOneSightDetail(String title) {
		String sql = " SELECT * "
				+ " FROM SIGHTS "
				+ " WHERE TITLE=? ";
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		SightsDto dto = null;
		
		try {
			conn = DBConnection.getConnection();
			
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, title);
			
			rs = psmt.executeQuery();
			
			if(rs.next()) {
				dto = new SightsDto(rs.getString(1), 
									rs.getInt(2), 
									rs.getInt(3), 
									rs.getString(4), 
									rs.getString(5), 
									rs.getString(6), 
									rs.getString(7), 
									rs.getString(8), 
									rs.getString(9), 
									rs.getInt(10), 
									rs.getInt(11), 
									rs.getInt(12));
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		return dto;
		
	}
	
	/**
	 * 매개변수로 받은 카테고리 번호를 가지고 DB에서 해당 카테고리 번호를 찾아서 리스트로 리턴 해주는 메소드
	 */
	public List<SightsDto> getSightslist(int category){
		String sql = " SELECT * "
				+ " FROM SIGHTS "
				+ " WHERE CATEGORY=? ";
		
//		System.out.println(sql);
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		List<SightsDto> list = new ArrayList<SightsDto>();
		
		try {
			conn = DBConnection.getConnection();
			
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, category);
			
			rs = psmt.executeQuery();
		while(rs.next()) {
			SightsDto dto = new SightsDto(rs.getString(1), 
											rs.getInt(2), 
											rs.getInt(3), 
											rs.getString(4), 
											rs.getString(5), 
											rs.getString(6), 
											rs.getString(7), 
											rs.getString(8), 
											rs.getString(9), 
											rs.getInt(10), 
											rs.getInt(11), 
											rs.getInt(12));
			list.add(dto);
			
		}
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		return list;
	}
	
	/**
	 * 매개변수로 받은 카테고리를 가지고 DB에서 해당 카테고리 DTO를 가져온다.
	 */
	@Override
	public SightsDto getOneCategoryDto(int category) {
		String sql = " SELECT * "
				+ " FROM SIGHTS "
				+ " WHERE CATEGORY=? ";
//		System.out.println(sql);
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		SightsDto dto = new SightsDto();
		
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, category);
			
			rs = psmt.executeQuery();
			if(rs.next()) {
				 dto = new SightsDto(rs.getString(1), 
												rs.getInt(2), 
												rs.getInt(3), 
												rs.getString(4), 
												rs.getString(5), 
												rs.getString(6), 
												rs.getString(7), 
												rs.getString(8), 
												rs.getString(9), 
												rs.getInt(10), 
												rs.getInt(11), 
												rs.getInt(12));
			}	
		
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		return dto;
	}
	
	
	/**
	 * 매개변수로 받은 카테고리를 가지고 DB에서 해당 카테고리의 테마를 중복제거해서 가져온다. 
	 */
	@Override
	public List<String> getThemelist(int category) {
		String sql = " SELECT DISTINCT THEME "
				 + " FROM SIGHTS "
				 + " WHERE CATEGORY=? ";
		
//		System.out.println(sql + category);
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		List<String> list = new ArrayList<String>();
		
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, category);
			
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				String dto = rs.getString(1);
				
				list.add(dto);
			}
			System.out.println("Db 완료");
			for (int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i));
			}
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		return list;
	}
	
	
	/**
	 * 매개변수로 받은 테마 이름을 가지고 DB에서 해당 테마들만 찾아서 리스트로 리턴 
	 */
	@Override
	public List<SightsDto> getSightThemelist(String theme) {
		String sql = " SELECT * "
				+ " FROM SIGHTS "
				+ " WHERE THEME=? ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		List<SightsDto> list = new ArrayList<SightsDto>();
		try {
			conn = DBConnection.getConnection();
			
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, theme);
			
			rs = psmt.executeQuery();
			while(rs.next()) {
				SightsDto dto = new SightsDto(rs.getString(1), 
												rs.getInt(2), 
												rs.getInt(3), 
												rs.getString(4), 
												rs.getString(5), 
												rs.getString(6), 
												rs.getString(7), 
												rs.getString(8), 
												rs.getString(9), 
												rs.getInt(10), 
												rs.getInt(11), 
												rs.getInt(12));
				list.add(dto);
				
			}	
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		return list;
	}
	
	/**
	 * 매개변수로 받은 카테고리, 조회카운트 로 DB에서 해당 타이틀의 조회순으로 정렬 
	 */
	@Override  
	public List<SightsDto> getReadSortSightlist(int category, String theme) {
		System.out.println("테마가 잘 넘어왔는지 알아보자" + theme);
		
		String sql = " SELECT * "
				+ " FROM SIGHTS "
				+ " WHERE CATEGORY=? ";
				if(theme.equals("all")) {
					sql += "";
				}else {
					sql += " AND THEME=? ";
				}
			sql	+= " ORDER BY READCOUNT DESC ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		List<SightsDto> list = new ArrayList<SightsDto>();
		
		try {
			conn = DBConnection.getConnection();
			
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, category);
			psmt.setString(2, theme);
			
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				SightsDto dto = new SightsDto(rs.getString(1), 
												rs.getInt(2), 
												rs.getInt(3), 
												rs.getString(4), 
												rs.getString(5), 
												rs.getString(6), 
												rs.getString(7), 
												rs.getString(8), 
												rs.getString(9), 
												rs.getInt(10), 
												rs.getInt(11), 
												rs.getInt(12));
				list.add(dto);
				
			}	
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		return list;
	}
		
	/**
	 *	매개변수로 받은 카테고리, 조회카운트 로 DB에서 해당 타이틀의 리뷰순으로 정렬 
	 */
	@Override 
	public List<SightsDto> getScheduleSortSightlist(int category, String theme) {
		String sql = " SELECT * "
				+ " FROM SIGHTS "
				+ " WHERE CATEGORY=? ";
				if(theme.equals("all")) {
					sql += "";
				}else {
					sql += " AND THEME=? ";
				}
			sql	+= " ORDER BY ADDSCHEDULE DESC ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		List<SightsDto> list = new ArrayList<SightsDto>();
		
		try {
			conn = DBConnection.getConnection();
			
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, category);
			
			psmt.setString(2, theme);
			
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				SightsDto dto = new SightsDto(rs.getString(1), 
												rs.getInt(2), 
												rs.getInt(3), 
												rs.getString(4), 
												rs.getString(5), 
												rs.getString(6), 
												rs.getString(7), 
												rs.getString(8), 
												rs.getString(9), 
												rs.getInt(10), 
												rs.getInt(11), 
												rs.getInt(12));
				list.add(dto);
				
			}	
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		return list;
	}

	
	/**매개변수로 받은 카테고리, 테마에 맞는 값을 sortSel 기준으로 정렬하여 리턴하는 메소드
	 * sortSel(정렬기준) : all, addschedule, readcount
	 * 	- all : order by seq desc;
	 *  - addschedule : order by addschedule desc;
	 *  - readcount : order by readcount desc;
	 * @param category
	 * @param theme
	 * @param sortSel
	 * @return
	 */
	public List<SightsDto> getScheduleSortSightlist(SightSortCondition cond){
		
		//테마가 all인지 아닌지 여부를 판단해서 psmt에 colString 추가하기 위한 변수
		boolean themeConditionAdded = false;
		
		String sql = " SELECT * "
				+ " FROM SIGHTS "
				+ " WHERE CATEGORY=? ";
		
		if( cond.getTheme().equals("all")) {
			sql += "";
		}else {
			sql += " AND THEME=? ";
			themeConditionAdded = true;
		}
		
		if( cond.getSortSel().equals("addschedule") ) {
			sql	+= " ORDER BY ADDSCHEDULE DESC ";
		}else if( cond.getSortSel().equals("readcount") ) {
			sql	+= " ORDER BY READCOUNT DESC ";
		}else {
			sql	+= " ORDER BY SEQ DESC ";
		}
				
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		List<SightsDto> list = new LinkedList<SightsDto>();
		
		try {
			//psmt 조건을 순차적으로 지정하기 위한 변수
			int colPointer = 1;
			conn = DBConnection.getConnection();
			
			psmt = conn.prepareStatement(sql);
			psmt.setInt( colPointer++, cond.getCategory());
			
			if( themeConditionAdded == true ) {
				psmt.setString( colPointer++, cond.getTheme() );
			}						
			
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				SightsDto dto = new SightsDto(rs.getString(1), 
												rs.getInt(2), 
												rs.getInt(3), 
												rs.getString(4), 
												rs.getString(5), 
												rs.getString(6), 
												rs.getString(7), 
												rs.getString(8), 
												rs.getString(9), 
												rs.getInt(10), 
												rs.getInt(11), 
												rs.getInt(12));
				list.add(dto);				
			}	
			
			//System.out.println("[SightsDaoImpl] list return done. 3 params getScheduleSortSightlist.");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		return list;
	}
	
	public int getPageNumCount(SightSortCondition cond) {
		
		boolean themeConditionAdded = false;
		String themeCondition = "";
		if( cond.getTheme().trim().equals("all") == false ) {
			themeCondition = " AND ( THEME= ? ) ";
			themeConditionAdded = true;
		}
		
			String sql = " SELECT COUNT(*) " 
					+ " FROM SIGHTS "
					+ " WHERE CATEGORY=? "
					+ themeCondition;
			
				Connection conn = null;
				PreparedStatement psmt = null;
				ResultSet rs = null;
				
				int pagecountnum = 0;
		System.out.println(sql);
		try {
			conn = DBConnection.getConnection();
			
			psmt = conn.prepareStatement(sql);
			
			psmt.setInt(1, cond.getCategory());
			
			if(themeConditionAdded == true) {
				psmt.setString(2, cond.getTheme());
			}
			
			rs = psmt.executeQuery();
			
			if(rs.next()) {
				pagecountnum = rs.getInt(1);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);			
		}		
		return pagecountnum;
	}
	
	
	/** 페이징이 적용된 매개변수로 받은 카테고리, 테마에 맞는 값을 sortSel 기준으로 정렬하여 리턴하는 메소드
	 * sortSel(정렬기준) : all, addschedule, readcount
	 * 	- all : order by seq desc;
	 *  - addschedule : order by addschedule desc;
	 *  - readcount : order by readcount desc;
	 * 기본페이지 속성
	 *  - 한 페이지 당 6개
	 * @param cond
	 * @param pageDto
	 * @return
	 */
	public List<SightsDto> getScheduleSortSightlist(SightSortCondition cond, SightPagingDto pageDto){
				
		//정렬기준(order by)을 결정하는 변수
		String sortCondition = "";
		if( cond.getSortSel().equals("addschedule") ) {
			sortCondition = " ORDER BY ADDSCHEDULE DESC, SEQ DESC ";
		}else if( cond.getSortSel().equals("readcount") ) {
			sortCondition = " ORDER BY READCOUNT DESC, SEQ DESC ";
		}else if( cond.getSortSel().equals("starcount")){
			sortCondition = " ORDER BY SCORE1 DESC ";
		}else {
			sortCondition = " ORDER BY SEQ DESC ";
		}	
		
		//테마가 all인지 아닌지 여부를 판단해서 psmt에 colString 추가하기 위한 변수
		boolean themeConditionAdded = false;
		String themeCondition = "";
		if( cond.getTheme().trim().equals("all") == false ) {
			themeCondition = " AND ( THEME= ? ) ";
			System.out.println( cond.getTheme() );
			themeConditionAdded = true;
		}	
		
		String searchStrCondition = "";
		if( cond.getSearchStr().trim().equals("all") == false) {
			searchStrCondition = " AND ( TITLE LIKE "+ "'%" + cond.getSearchStr() + "%' ) ";
		}
		
		String sql = " SELECT  RNUM, TITLE, SEQ, CATEGORY, THEME, FILENAME, ADDRESS, PHONE, HOMEPAGE, CONTENT, ADDSCHEDULE, DEL, READCOUNT, SCORE1 "
				+ " FROM (SELECT ROWNUM AS RNUM, SCORE1, TITLE, SEQ, CATEGORY, THEME, FILENAME, ADDRESS, PHONE, HOMEPAGE, CONTENT, ADDSCHEDULE, DEL, READCOUNT "
				+ " FROM (SELECT NVL(BB.SCORE11, 0) AS SCORE1, AA.TITLE, AA.SEQ, AA.CATEGORY, AA.THEME, AA.FILENAME, AA.ADDRESS, AA.PHONE, AA.HOMEPAGE, AA.CONTENT, AA.ADDSCHEDULE, AA.DEL, AA.READCOUNT "
				+ " FROM ( SELECT TITLE, SEQ, CATEGORY, THEME, FILENAME, ADDRESS, PHONE, HOMEPAGE, CONTENT, ADDSCHEDULE, DEL, READCOUNT FROM SIGHTS "
				+ " WHERE (CATEGORY = ?)" + themeCondition  + " " + searchStrCondition +  ") AA, "
				+ " ( SELECT TITLE, AVG(SCORE) AS SCORE11 FROM SIGHT_REVIEW GROUP BY TITLE	) BB WHERE AA.TITLE = BB.TITLE(+) "+ sortCondition + ") ) "
				+ " WHERE ( (RNUM >=  " + pageDto.getStartRnum() + "  ) AND (RNUM <=  " + pageDto.getEndRnum() + "  ) ) ";
		 	
		 
		System.out.println(sql);
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		List<SightsDto> list = new LinkedList<SightsDto>();
		
		try {
			//psmt 조건을 순차적으로 지정하기 위한 변수
			int colPointer = 1;
			conn = DBConnection.getConnection();
			
			psmt = conn.prepareStatement(sql);
			
			//쿼리에 category 설정
			psmt.setInt( colPointer++, cond.getCategory());
			
			//쿼리에 theme 설정
			if( themeConditionAdded == true ) {
				psmt.setString( colPointer++, cond.getTheme() );
			}
			
			
			
			System.out.println("sort condition : " + cond);
			System.out.println("query in dao : " + sql);
			
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				int i = 1;
				rs.getInt(i++);
				SightsDto dto = new SightsDto(rs.getString(i++), 
												rs.getInt(i++), 
												rs.getInt(i++), 
												rs.getString(i++), 
												rs.getString(i++), 
												rs.getString(i++), 
												rs.getString(i++), 
												rs.getString(i++), 
												rs.getString(i++), 
												rs.getInt(i++), 
												rs.getInt(i++), 
												rs.getInt(i++),
												rs.getDouble(i++) );
				list.add(dto);				
			}	
			
			System.out.println("[SightsDaoImpl] list return done. ");
			
		} catch (Exception e) {
			
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		return list;
	}	
	
	
	/**모든 카테고리의 sight를 리턴하는 메소드. 정렬기준은 cond의 SortSel만 활용
	 * @param pageDto
	 * @return
	 */
	public List<SightsDto> getPopularSightlist(SightSortCondition cond, SightPagingDto pageDto) {
		//정렬기준(order by)을 결정하는 변수
				String sortCondition = "";
				if( cond.getSortSel().equals("addschedule") ) {
					sortCondition = " ORDER BY ADDSCHEDULE DESC, SEQ DESC ";
				}else if( cond.getSortSel().equals("readcount") ) {
					sortCondition = " ORDER BY READCOUNT DESC, SEQ DESC ";
				}else {
					sortCondition = " ORDER BY SEQ DESC ";
				}				
				
				String sql = " SELECT A.RNUM, A.TITLE, A.SEQ, A.CATEGORY, A.THEME, A.FILENAME, A.ADDRESS, A.PHONE, A.HOMEPAGE, A.CONTENT, A.ADDSCHEDULE, A.DEL, A.READCOUNT, NVL(B.SCORE,0) "
						+ " FROM (  "
						+ " SELECT ROWNUM AS RNUM, TITLE, SEQ, CATEGORY, THEME, FILENAME, ADDRESS, PHONE, HOMEPAGE, CONTENT, ADDSCHEDULE, DEL, READCOUNT "
						+ " FROM ( SELECT TITLE, SEQ, CATEGORY, THEME, FILENAME, ADDRESS, PHONE, HOMEPAGE, CONTENT, ADDSCHEDULE, DEL, READCOUNT "
						+ " FROM SIGHTS  ) ) A , (SELECT TITLE, AVG(SCORE) AS SCORE FROM SIGHT_REVIEW GROUP BY TITLE) B "
						+ "  WHERE A.TITLE = B.TITLE(+) AND ( (RNUM >= 1 ) AND (RNUM <= 10 ) ) ";
				
				
				Connection conn = null;
				PreparedStatement psmt = null;
				ResultSet rs = null;
				
				List<SightsDto> list = new LinkedList<SightsDto>();
				
				try {
					//psmt 조건을 순차적으로 지정하기 위한 변수
					int colPointer = 1;
					conn = DBConnection.getConnection();
					
					psmt = conn.prepareStatement(sql);
						
					rs = psmt.executeQuery();
					
					while(rs.next()) {
						int i = 1;
						rs.getInt(i++);
						SightsDto dto = new SightsDto(rs.getString(i++), 
														rs.getInt(i++), 
														rs.getInt(i++), 
														rs.getString(i++), 
														rs.getString(i++), 
														rs.getString(i++), 
														rs.getString(i++), 
														rs.getString(i++), 
														rs.getString(i++), 
														rs.getInt(i++), 
														rs.getInt(i++), 
														rs.getInt(i++),
														rs.getDouble(i++) );
						list.add(dto);				
					}	
					
					//System.out.println("[SightsDaoImpl] list return done. ");
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					DBClose.close(conn, psmt, rs);
				}
				return list;
	}
}
