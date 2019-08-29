package com.admin.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.admin.dao.NoticeDao;
import com.admin.dto.NoticeDto;
import com.admin.dto.NoticePagingDto;

import common.db.DBClose;
import common.db.DBConnection;

public class NoticeDaoImpl implements NoticeDao {

	public static NoticeDaoImpl single = null;	
	
	private NoticeDaoImpl() {}
	
	public static NoticeDaoImpl getInstance() {
		if( single == null ) {
			single = new NoticeDaoImpl();
		}
		return single;
	}
	
	/**	새로운 공지사항을 DB에 저장하는 메소드
	 *
	 */
	@Override
	public boolean insertNewNotice(NoticeDto dto) {
		boolean isDone = false;
		
		String sql = " INSERT INTO NOTICE_JEJU "
				+ " VALUES(SEQ_NOTICE_JEJU.NEXTVAL, SEQ_NOTICE_JEJU.CURRVAL , ?, ?, ?, ?, ?, ?, 0, 0, SYSDATE) ";
		
		Connection conn = null;
		PreparedStatement psmt = null;

		try {
			
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);

			int i = 1;			
			psmt.setString(i++, dto.getTitle() );
			psmt.setString(i++, dto.getContent1() );
			psmt.setString(i++, ( dto.getContent2() != null )? dto.getContent2() : " "  );
			psmt.setString(i++, ( dto.getContent3() != null )? dto.getContent3() : " "  );
			psmt.setString(i++, ( dto.getFilename() != null )? dto.getFilename() : " "  );
			psmt.setString(i++, dto.getAuthor() );
			
			psmt.executeUpdate();			
			
			isDone = true;

		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBClose.close(conn, psmt, null);
		}
		
		return isDone;
	}
	
	/**공지사항을 수정하는 메소드
	 * @param dto
	 * @return
	 */
	@SuppressWarnings("resource")
	public boolean updateNotice(NoticeDto dto) {
		boolean isDone = false;
		
		String sql = " INSERT INTO NOTICE_JEJU "
				+ " VALUES(SEQ_NOTICE_JEJU.NEXTVAL, " + dto.getOrigin_seq() + " , ?, ?, ?, ?, ?, ?, 0, 0, SYSDATE) ";
		
		Connection conn = null;
		PreparedStatement psmt = null;

		try {
			
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);

			int i = 1;			
			psmt.setString(i++, dto.getTitle() );
			psmt.setString(i++, dto.getContent1() );
			psmt.setString(i++, ( dto.getContent2() != null )? dto.getContent2() : " "  );
			psmt.setString(i++, ( dto.getContent3() != null )? dto.getContent3() : " "  );
			psmt.setString(i++, ( dto.getFilename() != null )? dto.getFilename() : " "  );
			psmt.setString(i++, dto.getAuthor() );
			
			psmt.executeUpdate();			
			
			isDone = true;
			
			//insert작업이 완료되었으면 원본 글 del 0으로 수정
			sql = " UPDATE NOTICE_JEJU SET DEL=1 WHERE SEQ=" + dto.getOrigin_seq() + " ";
			psmt = conn.prepareStatement(sql);
			psmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBClose.close(conn, psmt, null);
		}
		
		return isDone;
	}

	/**공지사항을 삭제하는 메소드. del값을 1로 변경한다.
	 *
	 */
	@Override
	public boolean deleteNotice(int seq) {
		boolean isDone = false;
		
		String sql = " UPDATE NOTICE_JEJU SET DEL=1 WHERE SEQ=" + seq + " ";
		
		Connection conn = null;
		PreparedStatement psmt = null;

		try {
			
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);			
			psmt.executeUpdate();
			
			isDone = true;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBClose.close(conn, psmt, null);
		}
		
		return isDone;
	}	
	
	/**여러 개의 공지사항을 삭제하는 메소드. del 값을 1로 변경한다.
	 *
	 */
	@Override
	public boolean deleteMultipleNotice(String[] seqs) {
		boolean isDone = false;
		
		String sql = " ";
		
		Connection conn = null;
		PreparedStatement psmt = null;

		try {
			
			for(String str : seqs) {
				sql = " UPDATE NOTICE_JEJU SET DEL=1 WHERE SEQ=" + str + " ";
				conn = DBConnection.getConnection();
				psmt = conn.prepareStatement(sql);			
				psmt.executeUpdate();
			}			
			
			isDone = true;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBClose.close(conn, psmt, null);
		}
		
		return isDone;
	}

	
	/**NOTICE_JEJU DB에 저장된 전체 글 개수를 int 형태로 리턴하는 메소드
	 * @return
	 */
	public int getNoticeDBSize() {
		int noticeSize = -1;
		
		String sql = " SELECT COUNT(*) FROM NOTICE_JEJU WHERE DEL=0 ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBConnection.getConnection();			
			psmt = conn.prepareStatement(sql);			
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				noticeSize = rs.getInt(1);
			}			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, null);
		}
		
		return noticeSize;
	}
	
	/** paging규칙에 따라 DEL값이 0인 모든 글을 DB에서 가져오는 메소드
	 *
	 */
	public List<NoticeDto> getAllNoticeList(NoticePagingDto pagingDto){
		List<NoticeDto> lst = new ArrayList<NoticeDto>();
		
		String sql = "SELECT RNUM, SEQ, ORIGIN_SEQ, TITLE, CONTENT1, CONTENT2, CONTENT3, FILENAME, AUTHOR, WDATE " + 
					"	FROM ( SELECT ROWNUM AS RNUM, SEQ, ORIGIN_SEQ, TITLE, CONTENT1, CONTENT2, CONTENT3, FILENAME, AUTHOR, WDATE " + 
					"				FROM (SELECT * FROM NOTICE_JEJU WHERE DEL=0 ORDER BY SEQ DESC, WDATE DESC )  " + 
					"			ORDER BY RNUM " + 
					"		)" + 
					" WHERE RNUM BETWEEN " + pagingDto.getStartnum() + " AND " + pagingDto.getLastnum();
		//System.out.println("snum : " + pagingDto.getStartnum() + ",  endnum:" + pagingDto.getLastnum());
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBConnection.getConnection();			
			psmt = conn.prepareStatement(sql);			
			rs = psmt.executeQuery();
			
			NoticeDto dto = null;
			
			while(rs.next()) {
				int seq = rs.getInt("SEQ");
				String title = rs.getString("TITLE");
				String content1 = rs.getString("CONTENT1");
				String content2 = rs.getString("CONTENT2");
				String content3 = rs.getString("CONTENT3");
				String filename = rs.getString("FILENAME");
				String author = rs.getString("AUTHOR");
				Date wdate = rs.getDate("WDATE");
				
				//System.out.println("cont1:" + content1 + " , cont2:" + content2 + " , cont3:" + content3);
				
				//본문 길이에 따라 다른 생성자 사용
				if( content3 != null ) {
					dto = new NoticeDto(seq, title, author, filename, wdate, content1, content2, content3);
				}else if( content2 != null ) {
					dto = new NoticeDto(seq, title, author, filename, wdate, content1, content2);
				}else {
					dto = new NoticeDto(seq, title, author, filename, wdate, content1);
				}
								
				lst.add(dto);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, null);
		}
		
		return lst;
	}

	
	/**seq로 notice1개를 찾아 리턴하는 메소드
	 * @param seq
	 * @return
	 */
	public NoticeDto getOneNoticeDetail(int seq) {
		NoticeDto dto = null;
		
		String sql = "SELECT * FROM NOTICE_JEJU WHERE SEQ=" + seq + " ";
	
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBConnection.getConnection();			
			psmt = conn.prepareStatement(sql);			
			rs = psmt.executeQuery();
				
			while(rs.next()) {
				String title = rs.getString("TITLE");
				String content1 = rs.getString("CONTENT1");
				String content2 = rs.getString("CONTENT2");
				String content3 = rs.getString("CONTENT3");
				String filename = rs.getString("FILENAME");
				String author = rs.getString("AUTHOR");
				Date wdate = rs.getDate("WDATE");
								
				//본문 길이에 따라 다른 생성자 사용
				if( content3 != null ) {
					dto = new NoticeDto(seq, title, author, filename, wdate, content1, content2, content3);
				}else if( content2 != null ) {
					dto = new NoticeDto(seq, title, author, filename, wdate, content1, content2);
				}else {
					dto = new NoticeDto(seq, title, author, filename, wdate, content1);
				}
			}	
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, null);
		}
		
		return dto;
	}

}
