package com.admin.dao.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.admin.dao.AdminDao;
import com.member.dto.MemberDto;
import com.sights.dto.SightsDto;

import common.db.DBClose;
import common.db.DBConnection;

public class AdminDaoImpl implements AdminDao, Serializable {

	private static final long serialVersionUID = 1L;

	private static AdminDaoImpl dao = null;

	public static AdminDaoImpl getInstance() {
		if(dao == null) {
			dao = new AdminDaoImpl();
			DBConnection.initConnect();
		}
		return dao;
	
	}
	
	
	
	/* 회원 */
	
	/**
	 * 회원들의 리스트를 전부 가져오는 메소드
	 */
	@Override
	public MemberDto getMember(int seq) {
		String sql = " SELECT * FROM MEMBER_JEJU "
				+ " WHERE SEQ=? "; 
		
//		System.out.println("getMemberList sql : " + sql);
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		MemberDto dto = null;
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/6 getMemberLsit suc");
			
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, seq);
			System.out.println("2/6 getMemberLsit suc");
			
			rs = psmt.executeQuery();
			System.out.println("3/6 getMemberLsit suc");
			
			while(rs.next()) {
				dto = new MemberDto(rs.getString(1), 
									rs.getInt(2),
									rs.getString(3),
									rs.getString(4),
									rs.getString(5), 
									rs.getString(6), 
									rs.getInt(7), 
									rs.getInt(8));
				System.out.println("4/6 getMemberLsit suc");
			}
			
			
		} catch (Exception e) {
			System.out.println("getMemberList fail");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, null);
		}
		return dto;
	}


	
	/**
	 * 회원 리스트에서 체크박스를 이용해 멀티 삭제하는 메소드
	 */
	@Override
	public boolean MemberMultiDelete(String[] ids) {
	
		String sql = " DELETE FROM MEMBER_JEJU "
				+ " WHERE ID=? ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
	
		int count[] = new int[ids.length];
		
		try {
			conn = DBConnection.getConnection();
			conn.setAutoCommit(false);	// 자동 commit 실행XX
			
			psmt = conn.prepareStatement(sql);
			
			for(int i = 0; i < ids.length; i++) {
				psmt.setString(1, ids[i]);
				psmt.addBatch();	// 돌리면 새로운 값을 집어넣으면서 계속 동작하라
			}
			count = psmt.executeBatch();	// 모든 동작을 한꺼번에 처리한다
			
			conn.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DBClose.close(conn, psmt, null);
		}
		
		boolean isS = true;
		
		for(int i = 0; i < count.length; i++) {
			if(count[i] != -2) {		// -2 == 정상종료
				isS = false;
				break;		
			}
		}
		return isS;
	}


	/**
	 * 선택 항목으로 회원을 검색하는 메소드
	 */
	@Override
	public List<MemberDto> getSearchMember(String sel, String searchW) {
		String sql = " SELECT ID, SEQ, PW, NAME, EMAIL, BIRTH, DEL, ISADMIN "
				+ " FROM MEMBER_JEJU "
				+ " WHERE ISADMIN=1 ";
		
		String sqlword = "";
		
		if(sel.equals("ID")) {
			sqlword = " AND ID LIKE '%" + searchW.trim() + "%' ";
		}else if(sel.equals("NAME")) {
			sqlword = " AND NAME LIKE '%" + searchW.trim() + "%' ";
		}else if(sel.equals("EMAIL")) {
			sqlword = " AND EMAIL LIKE '%" + searchW.trim() + "%' ";
		}
		sql += sqlword;
		sql += " ORDER BY SEQ ASC ";
		
//		System.out.println("search sql : " + sql);
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		List<MemberDto> list = new ArrayList<MemberDto>();
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/6 SearchMember suc");
			
			psmt = conn.prepareStatement(sql);
			System.out.println("2/6 SearchMember suc");
			
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				MemberDto dto = new MemberDto(	rs.getString(1), 
												rs.getInt(2), 
												rs.getString(3),
												rs.getString(4), 
												rs.getString(5), 
												rs.getString(6), 
												rs.getInt(7), 
												rs.getInt(8));
				list.add(dto);
			}
			System.out.println("3/6 SearchMember suc");
		} catch (Exception e) {
			System.out.println("SearchMember fail");
			e.printStackTrace();
		}finally {
			DBClose.close(conn, psmt, rs);
		}
		
		return list;
	}


	/**
	 * 전체 멤버의 수를 카운트하는 메소드
	 */
	public int getAllMember(String sel, String searchW) {
		String sql = " SELECT COUNT(*) FROM MEMBER_JEJU WHERE ISADMIN=1 ";

		
		String sqlword = "";
		if(sel == null) {
			sql += "";
		}else if(sel.equals("ID")) {
			sqlword = " AND ID LIKE '%" + searchW.trim() + "%' ";
		}else if(sel.equals("NAME")) {
			sqlword = " AND NAME LIKE '%" + searchW.trim() + "%' ";
		}else if(sel.equals("EMAIL")) {
			sqlword = " AND EMAIL LIKE '%" + searchW.trim() + "%' ";
		}
		sql += sqlword;
		
//		System.out.println("getAllMember sql : " + sql);
	
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		int BbsLen = 0;
		
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/6 getAllMember suc");
			
			psmt = conn.prepareStatement(sql);
			System.out.println("2/6 getAllMember suc");
			
			rs = psmt.executeQuery();
			System.out.println("3/6 getAllMember suc");
			
			if(rs.next()) {
				BbsLen = rs.getInt(1);
				}
			
		} catch (Exception e) {
			System.out.println("getAllMember fail");
			e.printStackTrace();
		}finally {
			DBClose.close(conn, psmt, rs);
		}
		return BbsLen;
	}
	
	
	/**
	 * 멤버리스트를 페이징 해주는 메소드
	 */
	public List<MemberDto> getMemberListPaging(int page, String sel, String searchW){
		String sql = " SELECT ID, SEQ, PW, NAME, EMAIL, "
				+ " BIRTH, DEL, ISADMIN " 
				+ " FROM ";
		
			sql += " (SELECT ROW_NUMBER()OVER (ORDER BY SEQ DESC)AS RNUM, "
				+ " ID, SEQ, PW, NAME, EMAIL, BIRTH, DEL, ISADMIN "
				+ " FROM MEMBER_JEJU ";
			
		
		String sqlword = "";
		if(sel == null) {
			sql += "";
		}else if(sel.equals("ID")) {
			sqlword = " WHERE ID LIKE '%" + searchW.trim() + "%' ";
		}else if(sel.equals("NAME")) {
			sqlword = " WHERE NAME LIKE '%" + searchW.trim() + "%' ";
		}else if(sel.equals("EMAIL")) {
			sqlword = " WHERE EMAIL LIKE '%" + searchW.trim() + "%' ";
		}
		sql += sqlword;
		sql += " ORDER BY SEQ DESC) ";
		sql += " WHERE RNUM >= ? AND RNUM <=? AND ISADMIN=1";
		
//		System.out.println("getMemberListPaging sql :" + sql);
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		List<MemberDto> list = new ArrayList<MemberDto>();
		
		int startP, endP;
		startP = 1 + 10 *(page - 1);
		endP = page * 10;
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/6 getMemberListPaging suc");
			
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, startP);
			psmt.setInt(2, endP);
			System.out.println("2/6 getMemberListPaging suc");
			
			rs = psmt.executeQuery();
			System.out.println("3/6 getMemberListPaging suc");
			
			while(rs.next()) {
				MemberDto dto = new MemberDto(	rs.getString(1), 
												rs.getInt(2), 
												rs.getString(3),
												rs.getString(4),
												rs.getString(5),
												rs.getString(6),
												rs.getInt(7),
												rs.getInt(8));
				
				list.add(dto);
				
			}
			System.out.println("4/6 getMemberListPaging suc");
			
		} catch (Exception e) {
			System.out.println("getMemberListPaging fail");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		
		return list;
		
	}

	
	
	/* 게시판 */
	
	
	@Override
	public SightsDto getBbs(int seq) {
		String sql = " SELECT * FROM SIGHTS WHERE SEQ=?";
		
//		System.out.println("getMemberList sql : " + sql);
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		SightsDto dto = null;
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/6 getBbs suc");
			
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, seq);
			System.out.println("2/6 getBbs suc");
			
			rs = psmt.executeQuery();
			System.out.println("3/6 getBbs suc");
			
			while(rs.next()) {
				 dto = new SightsDto(	rs.getString(1),
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

				System.out.println("4/6 getBbs suc");
			}
			
			
		} catch (Exception e) {
			System.out.println("getBbs fail");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, null);
		}
		
		return dto;
	}

	
	/**
	 * 업데이트를 위한 게시글 리스트를 뿌려주는 메소드
	 */
	@Override
	public List<SightsDto> getBbsList(int page, String sel, String searchW) {
		String sql = " SELECT TITLE, SEQ, CATEGORY, THEME, FILENAME,"
				+ " ADDRESS, PHONE, HOMEPAGE, CONTENT, ADDSCHEDULE, DEL, READCOUNT "
				+ " FROM ";
				
		sql += " (SELECT ROW_NUMBER() OVER (ORDER BY SEQ DESC) AS RNUM,"
				+ " TITLE, SEQ, CATEGORY, THEME, FILENAME, ADDRESS,"
				+ " PHONE, HOMEPAGE, CONTENT, ADDSCHEDULE, DEL, READCOUNT "
				+ " FROM SIGHTS WHERE 1=1 ";
		
		String sqlword = "";
		
		
		/*
		 * if(searchW != null) { sqlword = " AND TITLE LIKE '%" + searchW.trim() +
		 * "%' "; }
		 */
		if(sel != null) {
			if(sel.equals("TITLE")) {
				sqlword = " AND TITLE LIKE '%" + searchW.trim() + "%' ";
			}
		}
		
		sql += sqlword;
		
		sql += " ORDER BY ADDSCHEDULE DESC )";
		sql += " WHERE RNUM >= ? AND RNUM <=? ";

		System.out.println("getBbsListPaging sql :" + sql);
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		List<SightsDto> list = new ArrayList<SightsDto>();
		
		int startP, endP;
		startP = 1 + 10 *(page - 1);
		endP = page * 10;
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/6 getBbsListPaging suc");
			
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, startP);
			psmt.setInt(2, endP);
			System.out.println("2/6 getBbsListPaging suc");
			
			rs = psmt.executeQuery();
			System.out.println("3/6 getBbsListPaging suc");
			
			while(rs.next()) {
				SightsDto dto = new SightsDto(	rs.getString(1),
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
			System.out.println("4/6 getBbsListPaging suc");
			
		} catch (Exception e) {
			System.out.println("getBbsListPaging fail");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		return list;
	}


	
	/**
	 * 전체 게시글 카운트
	 */
	@Override
	public int getAllBbs(String category, String sel, String searchW) {
		String sql = " SELECT COUNT(*) FROM SIGHTS WHERE DEL=0 ";
		
		System.out.println("cat" + category);
		System.out.println("sel" + sel);
		System.out.println("search" + searchW);

//		if(category != null) {
//			if(category.equals("관광지")) {
//			sqlword = " AND CATEGORY = 0 ";
//			} else if(category.equals("음식점")) {
//				sqlword = " AND CATEGORY = 1 ";
//			} else if(category.equals("숙소")) {
//				sqlword = " AND CATEGORY = 2 ";
//			}
//		}
//		
//		if(category.equals("All") && sel != null) {
//			sqlword = " AND TITLE LIKE '%" + searchW.trim() + "%' ";
//		}
//		
		if(category.equals("All") && sel.equals("TITLE")) {
			sql += " AND TITLE LIKE ? ";
		}else {
			if(category.equals("All")) {
				sql += " AND TITLE LIKE ? ";
			} else if(category.equals("관광지")) {
				sql += " AND CATEGORY LIKE ? AND TITLE LIKE ? ";
			} else if(category.equals("음식점")) {
				sql += " AND CATEGORY LIKE ?  AND TITLE LIKE ? ";
			} else if(category.equals("숙소")) {
				sql += " AND CATEGORY LIKE ? AND TITLE LIKE ? ";
			}
		
		}
		
//		sql += sqlword;
		
		System.out.println("getAllBbs sql : " + sql);
		
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		int BbsLen = 0;
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/6 getAllBbs suc");
			
			psmt = conn.prepareStatement(sql);
			if(category.equals("All") && sel.equals("TTTLE")) {
				psmt.setString(1, "'%" + searchW.trim() +  "%'");
			}else{
				if(category.equals("All")) {
					psmt.setString(1, "%" + searchW.trim() + "%");
				}else if(category.equals("관광지")) {
					psmt.setInt(1, 0);
					psmt.setString(2, "%" + searchW.trim() +  "%");
				} else if(category.equals("음식점")) {
					psmt.setInt(1, 1);
					psmt.setString(2, "%" + searchW.trim() +  "%");
				} else if(category.equals("숙소")) {
					psmt.setInt(1, 2);
					psmt.setString(2, "%" + searchW.trim() +  "%");
				}
			
			}

		
		
			System.out.println("2/6 getAllBbs suc");
			
			rs = psmt.executeQuery();
			System.out.println("3/6 getAllBbs suc");
			
			if(rs.next()) {
				BbsLen = rs.getInt(1);
				}
			
		} catch (Exception e) {
			System.out.println("getAllBbs fail");
			e.printStackTrace();
		}finally {
			DBClose.close(conn, psmt, rs);
		}
		return BbsLen;
	}



	/**
	 * 사이트 게시글 리스트를  검색 및 페이징 해주는 메소드 
	 */
	@Override
	public List<SightsDto> getBbsListPaging(String category, int page, String sel, String searchW) {
		String sql = " SELECT TITLE, SEQ, CATEGORY, THEME, FILENAME,"
				+ " ADDRESS, PHONE, HOMEPAGE, CONTENT, ADDSCHEDULE, DEL, READCOUNT "
				+ " FROM ";
				
		sql += " (SELECT ROW_NUMBER() OVER (ORDER BY SEQ DESC) AS RNUM,"
				+ " TITLE, SEQ, CATEGORY, THEME, FILENAME, ADDRESS,"
				+ " PHONE, HOMEPAGE, CONTENT, ADDSCHEDULE, DEL, READCOUNT "
				+ " FROM SIGHTS WHERE 1=1 ";
		
		String sqlword = "";
		
		if(category.equals("All") && sel.equals("TITLE")) {
			sql += " AND TITLE LIKE ? ) ";
		}else {
			if(category.equals("All")) {
				sql += " AND TITLE LIKE ? ) ";
			} else if(category.equals("관광지")) {
				sql += " AND CATEGORY LIKE ? AND TITLE LIKE ? ) ";
			} else if(category.equals("음식점")) {
				sql += " AND CATEGORY LIKE ?  AND TITLE LIKE ? ) ";
			} else if(category.equals("숙소")) {
				sql += " AND CATEGORY LIKE ? AND TITLE LIKE ? ) ";
			}
		
		}

		sql += " WHERE RNUM >= ? AND RNUM <=? ";

		System.out.println("getBbsListPaging sql :" + sql);
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		List<SightsDto> list = new ArrayList<SightsDto>();
		
		int startP, endP;
		startP = 1 + 10 *(page - 1);
		endP = page * 10;
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/6 getBbsListPaging suc");
			
			psmt = conn.prepareStatement(sql);
			if(category.equals("All") && sel.equals("TITLE")) {
				psmt.setString(1, "%" + searchW.trim() + "%");
				psmt.setInt(2, startP);
				psmt.setInt(3, endP);
				
			}else{
				if(category.equals("All") ) {
					psmt.setString(1, "%" + searchW.trim() + "%");
				}else if(category.equals("관광지")) {
						psmt.setInt(1, 0);
						psmt.setString(2, "%" + searchW.trim() + "%");
					} else if(category.equals("음식점")) {
						psmt.setInt(1, 1);
						psmt.setString(2, "%" + searchW.trim() + "%");
					} else if(category.equals("숙소")) {
						psmt.setInt(1, 2);
						psmt.setString(2, "%" + searchW.trim() + "%");
					}
				
				psmt.setInt(3, startP);
				psmt.setInt(4, endP);
				}

			System.out.println("2/6 getBbsListPaging suc");
			
			rs = psmt.executeQuery();
			System.out.println("3/6 getBbsListPaging suc");
			
			while(rs.next()) {
				SightsDto dto = new SightsDto(	rs.getString(1),
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
			System.out.println("4/6 getBbsListPaging suc");
		} catch (Exception e) {
			System.out.println("getBbsListPaging fail");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		return list;
		
	}  
	
		/**
		 * 글 작성 후 등록하는 메소드
		 */
	@Override
	public boolean addSight(SightsDto dto) {
		String sql = " INSERT INTO SIGHTS(TITLE, SEQ, CATEGORY, THEME, FILENAME, ADDRESS, "
				+ " PHONE, HOMEPAGE, CONTENT, ADDSCHEDULE, DEL, READCOUNT) "
				+ " VALUES(?, SEQ_SIGHTS.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, 0, 0, 0) ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		
		System.out.println("addSight sql " + sql);
		
		int count = 0;
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/6 addSight suc");
			
			psmt = conn.prepareStatement(sql);

			psmt.setString(1, dto.getTitle());
			psmt.setInt(2, dto.getCategory());
			psmt.setString(3, dto.getTheme());
			psmt.setString(4, dto.getFilename());
			psmt.setString(5, dto.getAddress());
			psmt.setString(6, dto.getPhone());
			psmt.setString(7, dto.getHomepage());
			psmt.setString(8, dto.getContent());
			
			System.out.println("2/6 addSight suc");
			
			count = psmt.executeUpdate();
			System.out.println("3/6 addSight suc");
				
		} catch (Exception e) {
			System.out.println("addSight fail");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, null);
		}
		return count>0?true:false;
	}



		/**
		 * 게시글 리스트 멀티 삭제 메소드
		 */
	@Override
	public boolean BbsMultiDelete(String[] titles) {
			
			String sql = " DELETE FROM SIGHTS "
					+ " WHERE TITLE=? ";
			
			System.out.println("bbsMulDel sql : " + sql);
			
			Connection conn = null;
			PreparedStatement psmt = null;
		
			int count[] = new int[titles.length];
			
			try {
				conn = DBConnection.getConnection();
				conn.setAutoCommit(false);	// 자동 commit 실행XX
				
				psmt = conn.prepareStatement(sql);
				
				for(int i = 0; i < titles.length; i++) {
					psmt.setString(1, titles[i]);
					psmt.addBatch();	// 돌리면 새로운 값을 집어넣으면서 계속 동작하라
				}
				count = psmt.executeBatch();	// 모든 동작을 한꺼번에 처리한다
				conn.commit();
				
			} catch (Exception e) {
				e.printStackTrace();
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			} finally {
				try {
					conn.setAutoCommit(true);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				DBClose.close(conn, psmt, null);
			}
			
			boolean isS = true;
			
			for(int i = 0; i < count.length; i++) {
				if(count[i] != -2) {		// -2 == 정상종료
					isS = false;
					break;		
				}
			}
			return isS;
		}
	

	/**
	 * 게시글 수정 메소드
	 */
	public boolean bbsUpdate(int seq, SightsDto dto) {
		String sql = " UPDATE SIGHTS "
				+ " SET TITLE=?, CATEGORY=?, THEME=?, ADDRESS=?, "
				+ "PHONE=?, HOMEPAGE=?, CONTENT=? ";
		
		if(!dto.getFilename().equals("")) {
			sql += " , FILENAME=? ";
		}
		
		sql += " WHERE SEQ=? ";
		
		
		System.out.println("update sql" + sql);
		
		Connection conn = null;
		PreparedStatement psmt = null;
		
		int count = 0;
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/6 bbsUpdate suc");
				psmt = conn.prepareStatement(sql);
				
				if(!dto.getFilename().equals("")) {
					psmt.setString(1, dto.getTitle());
					psmt.setInt(2, dto.getCategory());
					psmt.setString(3, dto.getTheme());
					psmt.setString(4, dto.getAddress());
					psmt.setString(5, dto.getPhone());
					psmt.setString(6, dto.getHomepage());
					psmt.setString(7, dto.getContent());
					psmt.setString(8, dto.getFilename());
					psmt.setInt(9, seq);
				}else {
					psmt.setString(1, dto.getTitle());
					psmt.setInt(2, dto.getCategory());
					psmt.setString(3, dto.getTheme());
					psmt.setString(4, dto.getAddress());
					psmt.setString(5, dto.getPhone());
					psmt.setString(6, dto.getHomepage());
					psmt.setString(7, dto.getContent());
					psmt.setInt(8, seq);
				}
				
		

			System.out.println("2/6 bbsUpdate suc");
			
			count = psmt.executeUpdate();
			System.out.println("3/6 bbsUpdate suc");
			
		} catch (Exception e) {
			System.out.println("bbsUpdate fail");
			e.printStackTrace();
		}finally {
			DBClose.close(conn, psmt, null);
		}
		return count>0?true:false;
	}
}	

	
