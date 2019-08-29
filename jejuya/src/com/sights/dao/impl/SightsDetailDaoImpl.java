package com.sights.dao.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.sights.dao.SightsDetailDao;
import com.sights.dto.Paging;
import com.sights.dto.SightReviewDto;
import com.sights.dto.SightsDto;

import common.db.DBClose;
import common.db.DBConnection;

public class SightsDetailDaoImpl implements SightsDetailDao, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static SightsDetailDaoImpl dao = null;

	public static SightsDetailDaoImpl getInstance() {
		if(dao == null) {
			dao = new SightsDetailDaoImpl();
			DBConnection.initConnect();
		}
		return dao;
	}
	
	/**
	 *	매개변수로 받은 관광지 이름을 가지고 DB에서 관광지정보 하나를 찾아서 리턴해주는 메소드 
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
		
	// 여행지 등록
	public boolean addSight(SightsDto dto) {
		
		String sql = " INSERT INTO SIGHTS "
				+ " VALUES(?, SEQ_SIGHTS.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, 0, 0, 0) ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		
		int count = 0;
		
		try {
			conn = DBConnection.getConnection();
			
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, dto.getTitle());
			psmt.setInt(2, dto.getCategory());
			psmt.setString(3, dto.getTheme());
			psmt.setString(4, dto.getFilename());
			psmt.setString(5, dto.getAddress());
			psmt.setString(6, dto.getPhone());
			psmt.setString(7, dto.getHomepage());
			psmt.setString(8, dto.getContent());
			
			count = psmt.executeUpdate();
				
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, null);
		}
		return count>0?true:false;
	}
	
	// 여행지 title로 그 여행지의 리뷰를 가져옴(사용x 페이징 전)
	public List<SightReviewDto> getReviewList(String title) {	
		
		String sql = " SELECT SEQ, TITLE, ID, CONTENT, FILENAME, WDATE, SCORE, DEL  "
				+ " FROM SIGHT_REVIEW "
				+ " WHERE TITLE=? "
				+ " ORDER BY WDATE DESC ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		List<SightReviewDto> list = new ArrayList<SightReviewDto>();
		
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, title);
			
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				int i = 1;
				
				SightReviewDto dto = new SightReviewDto(rs.getInt(i++), 
														rs.getString(i++), 
														rs.getString(i++), 
														rs.getString(i++), 
														rs.getString(i++), 
														rs.getString(i++),
														rs.getInt(i++),
														rs.getInt(i++));
				list.add(dto);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		return list;		
	}
	
	// 리뷰등록 
	public boolean addReview(SightReviewDto dto) {
		
		String sql = " INSERT INTO SIGHT_REVIEW "
				+ " VALUES(SEQ_SIGHT_REVIEW.NEXTVAL, ?, ?, ?, ?, SYSDATE, ?, 0) ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		
		int count = 0;
		
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, dto.getTitle());
			psmt.setString(2, dto.getId());
			psmt.setString(3, dto.getContent());
			psmt.setString(4, dto.getFilename());
			psmt.setInt(5, dto.getScore());
			
			count = psmt.executeUpdate();
						
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, null);
		}
		return count>0?true:false;		
	}
	
	// 한 여행지(title)에 관한 리뷰의 총 갯수
	public int reviewAllCount(String title) {
		
		String sql = " SELECT COUNT(*) AS COUNT FROM SIGHT_REVIEW "
				+ " WHERE TITLE=? "
				+ " GROUP BY TITLE ";
				
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		int count = 0;
		
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, title);
			
			rs = psmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt("count");
			}					
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		return count;				
	}
	
	// 한 여행지(title)의 리뷰를 페이징처리해 가져오는 함수
	public List<SightReviewDto> pagingReviewList(String title, Paging paging, String scoreSorting){
		int startNum = paging.getStartNum();
		int endNum = paging.getEndNum();
		
		String sql = " SELECT B.SEQ, B.TITLE, B.ID, B.CONTENT, B.FILENAME, B.WDATE, B.SCORE, B.DEL "
					+ " FROM(SELECT ROWNUM AS RNUM, "
					+ " 	 A.SEQ, A.TITLE, A.ID, A.CONTENT, A.FILENAME, A.WDATE, A.SCORE, A.DEL " 
					+ " 	 FROM (SELECT SEQ, TITLE, ID, CONTENT, FILENAME, WDATE, SCORE, DEL "
					+ " 		   FROM SIGHT_REVIEW "
					+ " 		   WHERE TITLE=? ";
		
									if(scoreSorting.equals("score")) {
										sql += " ORDER BY SCORE DESC, WDATE DESC)A ";
									}else if(scoreSorting.equals("date")){
										sql += " ORDER BY WDATE DESC)A ";
									}

				sql	+= " 	 WHERE ROWNUM <=?)B " 
					+ " WHERE B.RNUM >=? ";		
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		List<SightReviewDto> list = new ArrayList<SightReviewDto>();
		
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, title);
			psmt.setInt(2, endNum);
			psmt.setInt(3, startNum);
						
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				int i = 1;
				
				SightReviewDto dto = new SightReviewDto(rs.getInt(i++), 
														rs.getString(i++), 
														rs.getString(i++), 
														rs.getString(i++), 
														rs.getString(i++), 
														rs.getString(i++), 
														rs.getInt(i++),
														rs.getInt(i++));
				list.add(dto);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		return list;
	}
	
	// 리뷰 삭제
	public boolean reviewDel(int seq) {
		
		String sql = " DELETE FROM SIGHT_REVIEW "
				+ " WHERE SEQ=? ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		
		int count = 0;
		
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, seq);
			
			count = psmt.executeUpdate();	
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, null);
		}
		return count>0?true:false;
	}
	
	// 리뷰 평점 평균
	public int avgReviewScore(String title) {
	
		String sql = " SELECT ROUND(AVG(SCORE)) AS COUNT "
				   + " FROM SIGHT_REVIEW "
				   + " WHERE TITLE=? ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		int count = 0;
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/6 avgReviewScore suc");
			
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, title);
			System.out.println("2/6 avgReviewScore suc");
			
			rs = psmt.executeQuery();
			System.out.println("3/6 avgReviewScore suc");
			
			if(rs.next()) {
				count = rs.getInt("count");
			}				
			System.out.println("4/6 avgReviewScore suc");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}		
		return count;
	}
		
	/**뷰 접근 시 readcount를 +1 해주기 위한 메소드
	 * @param title
	 */
	public void readCount(String title) {
		String sql = " UPDATE SIGHTS SET READCOUNT = READCOUNT+1 "
					+ " WHERE TITLE=? ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		try {
			
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, title);
			rs = psmt.executeQuery();
	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
	}
	
}

