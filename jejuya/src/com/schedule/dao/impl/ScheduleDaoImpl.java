package com.schedule.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.schedule.dao.ScheduleDao;
import com.schedule.dto.ScheduleDto;
import com.schedule.dto.ScheduledetailDto;
import com.sights.dto.SightsDto;

import common.db.DBClose;
import common.db.DBConnection;
import common.util.pageDto;

/**
 * @author bit
 *
 */
public class ScheduleDaoImpl implements ScheduleDao {

	private static ScheduleDaoImpl Scheduledao = new ScheduleDaoImpl();

	private ScheduleDaoImpl() {

	}

	public static ScheduleDaoImpl getInstance() {

		return Scheduledao;
	}

	
	
	
	
	public List<ScheduleDto> getsearchSchedulelist(String title) {

		String sql = " SELECT SEQ, ID, TITLE, SDATE, EDATE, TOTALDAYS," + " MEMBER, COMPANION, SECTION, OPEN, WDATE "
				+ " FROM SCHEDULES " + " WHERE TITLE LIKE "+"'%"+title+"%'" + "ORDER BY WDATE  DESC " ;
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;

		List<ScheduleDto> list = new ArrayList<ScheduleDto>();

		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
		
			rs = psmt.executeQuery();

			while (rs.next()) {
				int seq = rs.getInt(1);
				String id_ = rs.getString(2);
				String title_ = rs.getString(3);
				String sdate = rs.getString(4);
				String edate = rs.getString(5);
				String totaldays = rs.getString(6);
				String member = rs.getString(7);
				String companion = rs.getString(8);
				String section = rs.getString(9);
				int open = rs.getInt(10);
				String wdate = rs.getString(11);

				ScheduleDto dto = new ScheduleDto(seq, id_, title_, sdate, edate, Integer.parseInt(totaldays), wdate,
						Integer.parseInt(member), companion, section, open);
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
	
	/**해당 시퀀스를 가진 정보 삭제
	 * @param seq  
	 */
	public boolean deltrip(int seq) {
		String sql=" DELETE  FROM SCHEDULES WHERE SEQ=? ";
		
		
		Connection conn=null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		

		int count =0;
		try {
			conn=DBConnection.getConnection();
			psmt=conn.prepareStatement(sql);
			psmt.setInt(1, seq);
			
			count=psmt.executeUpdate();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBClose.close(conn, psmt, rs);
		}
		return count>0?true:false;
	}

	
	
	
	/**해당 여행 리스트에서 세부 일정 삭제
	 * @param seq
	 */
	public boolean delDetailtrip(int seq) {
		String sql=" DELETE  FROM DETAIL_SCHEDULES WHERE PATENT_SEQ=? ";
		
		
		Connection conn=null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		int count =0;
		
		try {
			conn=DBConnection.getConnection();
			psmt=conn.prepareStatement(sql);
			psmt.setInt(1, seq);
			
			count=psmt.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBClose.close(conn, psmt, rs);
		}
		return count>0?true:false;
	}
	
	/**일정을 수정하는 함수 
	 *
	 */
	public boolean update(ScheduleDto dto, int seq) { 
		String sql=" UPDATE SCHEDULES SET  ID= ?, TITLE=? , SDATE= ?, EDATE = ?, TOTALDAYS = ? , "
				+ " MEMBER = ? , COMPANION= ?, SECTION= ?, OPEN= ? "
				+ " WHERE SEQ= ? ";
		
		int count=0;
		Connection conn=null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
				
		try {
			conn=DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, dto.getId());
			psmt.setString(2, dto.getTitle());
			psmt.setString(3, dto.getSdate());
			psmt.setString(4, dto.getEdate());
			psmt.setInt(5, dto.getTotaldays());
			psmt.setInt(6, dto.getMember());
			psmt.setString(7, dto.getCompanion());
			psmt.setString(8,dto.getSection());
			psmt.setInt(9, dto.getOpen());
			psmt.setInt(10, seq);
			
			count=psmt.executeUpdate();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBClose.close(conn, psmt, rs);
		}
		//return cust;
		return count>0? true:false;
	}
	
	
	
 /** 시퀀스 넘버를 통해 나의 해당 여행 디테일을 보여주는 함수 입니다.
 * @param seq  파라메터로 받은 시퀀스 넘버 (즉 리스트를 클릭하면 시퀀스 넘버가 파라메터로 컨트롤러로 접근)
 * @return
 */
public ScheduleDto getDetail(int seq) {
		String sql = " SELECT SEQ, ID, TITLE, SDATE, EDATE, TOTALDAYS," + " MEMBER, COMPANION, SECTION, OPEN, WDATE "
				+ " FROM SCHEDULES " + " WHERE SEQ=? ";
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;

		
		 ScheduleDto dto= null;
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, seq);
			rs = psmt.executeQuery();

			while(rs.next()) {
				int seq_ = rs.getInt(1);
				String id_ = rs.getString(2);
				String title = rs.getString(3);
				String sdate = rs.getString(4);
				String edate = rs.getString(5);
				String totaldays = rs.getString(6);
				String member = rs.getString(7);
				String companion = rs.getString(8);
				String section = rs.getString(9);
				int open = rs.getInt(10);
				String wdate = rs.getString(11);

			   dto=new ScheduleDto(seq, id_, title, sdate, edate, Integer.parseInt(totaldays), wdate,
						Integer.parseInt(member), companion, section, open);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}

		return dto;
	}



   /**시퀀스 넘버를 이용하여 해당 유저의 여행 디테일 일정을 알아내는 함수 입니다.
 *
 */
public List<ScheduledetailDto> getsheduleDetail(int seq) {
       
	String sql=" SELECT PATENT_SEQ, SCHEDATE,STIME,DEST FROM DETAIL_SCHEDULES "
       		+ " WHERE PATENT_SEQ=? ";
       
       Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;

		List<ScheduledetailDto> list=new ArrayList<ScheduledetailDto>();
        
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, seq);
			rs = psmt.executeQuery();

		
			while(rs.next()) { 
				int parentseq=rs.getInt(1);
			    String schedate=rs.getString(2);
			    String stime=rs.getString(3);
			    String dest=rs.getString(4);
			    
			    ScheduledetailDto dto=new ScheduledetailDto(schedate,stime,dest,parentseq);
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
	 * addschedule 랭킹을 보기 위한 함수입니다
	 *
	 */
	public List<SightsDto> getLanking(int category) {
		String sql = "SELECT TITLE, READCOUNT,ADDSCHEDULE FROM (SELECT TITLE, READCOUNT,ADDSCHEDULE, ROW_NUMBER() "
				+ " OVER(ORDER BY ADDSCHEDULE DESC )AS RNUM FROM SIGHTS  WHERE CATEGORY=? ) "
				+ " WHERE RNUM BETWEEN 1 AND 5 ";
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;

		List<SightsDto> list = new ArrayList<SightsDto>();

		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, category);
			rs = psmt.executeQuery();
			System.out.println("ddss");
			while (rs.next()) {
				String title = rs.getString(1);
				int readcount = rs.getInt(2);
				int addschedule = rs.getInt(3);

				SightsDto dto = new SightsDto(title, addschedule, readcount);

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
	 * 유저가 현재까지 짠 모든 제주여행의 리스트를 보여주는 함수 입니다
	 *
	 */
	public List<ScheduleDto> getmySchedulelist(String id) {

		String sql = " SELECT SEQ, ID, TITLE, SDATE, EDATE, TOTALDAYS," + " MEMBER, COMPANION, SECTION, OPEN, WDATE "
				+ " FROM SCHEDULES " + " WHERE ID=?  ORDER BY WDATE  DESC " ;
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;

		List<ScheduleDto> list = new ArrayList<ScheduleDto>();

		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			rs = psmt.executeQuery();

			while (rs.next()) {
				int seq = rs.getInt(1);
				String id_ = rs.getString(2);
				String title = rs.getString(3);
				String sdate = rs.getString(4);
				String edate = rs.getString(5);
				String totaldays = rs.getString(6);
				String member = rs.getString(7);
				String companion = rs.getString(8);
				String section = rs.getString(9);
				int open = rs.getInt(10);
				String wdate = rs.getString(11);

				ScheduleDto dto = new ScheduleDto(seq, id_, title, sdate, edate, Integer.parseInt(totaldays), wdate,
						Integer.parseInt(member), companion, section, open);
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
	 * 사이트 즉 관광지,숙소, 호텔 의 리스트를 파라메터로 카테고리 넘버로 입력받아 리스트를 보여지게 함 0관광지 1음식점 2호텔
	 *
	 */
	@Override
	public List<SightsDto> getSightslist(int num) {
		String sql = " SELECT SEQ, TITLE, CATEGORY, THEME, ADDRESS, HOMEPAGE " + " FROM SIGHTS WHERE CATEGORY = ? ";

		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;

		List<SightsDto> list = new ArrayList<SightsDto>();

		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, num);

			rs = psmt.executeQuery();

			while (rs.next()) {
				int i = 1;

				int seq = rs.getInt(i++);
				String title = rs.getString(i++);
				int category = rs.getInt(i++);
				String theme = rs.getString(i++);
				String address = rs.getString(i++);
				String homepage = rs.getString(i++);

				SightsDto dto = new SightsDto(seq, title, category, theme, address, homepage);

				// System.out.println("getSightslist"+dto.toString());

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
	 * 페이징을 하기 위하여 카테고리 번호(관광지,음식점,호텔 구분 하기 위하여)를 입력받고 첫 번호와 끝 번호를 입력받아 페이징 해줌
	 *
	 */
	public List<SightsDto> getPajingtowrismlist(int num, int startnum, int lastnum) {
		String sql = " SELECT SEQ, TITLE, CATEGORY , THEME, ADDRESS, HOMEPAGE FROM "
				+ " (SELECT ROW_NUMBER()OVER(ORDER BY SEQ ASC) AS RNUM , "
				+ " SEQ, TITLE, CATEGORY, THEME, ADDRESS ,HOMEPAGE FROM SIGHTS WHERE CATEGORY= ? ) "
				+ "  WHERE RNUM >= ?  AND RNUM <= ? ";

//		
		System.out.println("4/3");

		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;

		List<SightsDto> list = new ArrayList<SightsDto>();

		try {
			conn = DBConnection.getConnection();
			// System.out.println("4/4");
			psmt = conn.prepareStatement(sql);
//			System.out.println("4/oo4");

			psmt.setInt(1, num);
			psmt.setInt(2, startnum);
			psmt.setInt(3, lastnum);

			rs = psmt.executeQuery();

			// System.out.println("5/4");

			while (rs.next()) {
				// System.out.println("rs ok");
				int i = 1;

				int seq = rs.getInt(i++);
				// System.out.println("rs seq ok");

				String title = rs.getString(i++);
				// System.out.println("rs title ok");

				int category = rs.getInt(i++);
				// System.out.println("rs cat ok");

				String theme = rs.getString(i++);
				// System.out.println("rs theme ok");

				String address = rs.getString(i++);
				String homepage = rs.getString(i++);
				// System.out.println("rs get cols ok");

				SightsDto dto = new SightsDto(seq, title, category, theme, address, homepage);

				// System.out.println("getPajingtowrismlist"+dto.toString());
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
	 * 페이징을 하기 위한 함수 리스트의 전체 사이즈와 현재 페이지를 파라메터로 입력받으면 시작번호와 끝번호를 계산해서 return 해줌
	 *
	 */
	public pageDto page(int total, int pg) {
		int list = 10; // 전체페이지에 10개를 출력
		int startnum = 1;
		int lastnum = 1;
		int pagingnum = 0; // 페이징 갯수

		if (total % list != 0) {
			pagingnum = total / list + 1;
		} else {
			pagingnum = total / list;

		}
		lastnum = pg * list;
		startnum = lastnum - (list - 1);

		pageDto dto = new pageDto(startnum, lastnum, pagingnum);

		System.out.println("daopage" + dto.toString());
		return dto;

	}

	/**
	 * 페이징을 하기 위한 함수
	 *
	 */
	public int pagenum(int total) {
		int count = 0; // 페이지 갯수
		int list = 10; // 한페이지에 나올 갯수
		if (total % list != 0) {
			count = total / list + 1;
		} else {
			count = total / list;
		}

		return count;
	}

	/**
	 * 일정을 짤 때 구분 별 랭킹 차트를 보여주기 위한 함수
	 * 
	 */
	public List<SightsDto> getLangkingchart(String theme) {

		String sql = "SELECT TITLE,SEQ,ADDSCHEDULE ,THEME FROM SIGHTS  "
				+ "WHERE ADDSCHEDULE IS NOT NULL AND THEME=? ORDER BY ADDSCHEDULE DESC ";

		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		List<SightsDto> list = new ArrayList<SightsDto>();
		SightsDto dto = null;
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/6 getLangkingchart");

			psmt = conn.prepareStatement(sql);

			System.out.println("2/6 getLangkingchart");

			psmt.setString(1, "'" + theme + "'");
			rs = psmt.executeQuery();

			System.out.println("3/6 getLangkingchart");

			while (rs.next()) {
				String title = rs.getString(1);
				int seq = rs.getInt(2);
				int addschedule = rs.getInt(3);

				dto = new SightsDto(seq, title, null, theme, null, null);

				list.add(dto);
				System.out.println("4/6 getLangkingchart");
				System.out.println(dto.toString());
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, null);
		}
		return list;
	}

	public boolean getadddetailSchedule(List<ScheduledetailDto> list) {

		ScheduledetailDto dto = new ScheduledetailDto();
		int count = 0;
		for (int i = 0; i < list.size(); i++) {
			dto = list.get(i);

			String sql = " INSERT INTO DETAIL_SCHEDULES (SEQ ,PATENT_SEQ, SCHEDATE,STIME, DEST) "
					+ " VALUES(SEQ_DETAIL_SCHEDULES.NEXTVAL,? ,?,?,?) ";

			Connection conn = null;
			PreparedStatement psmt = null;
			ResultSet rs = null;

			try {
				conn = DBConnection.getConnection();
				psmt = conn.prepareStatement(sql);

				psmt.setInt(1, dto.getParentseq());
				psmt.setString(2, dto.getSchedate());
				psmt.setString(3, dto.getStime());
				psmt.setString(4, dto.getDest());

				psmt.executeUpdate();
				count = count + 1;
				System.out.println("여행디테일");

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				DBClose.close(conn, psmt, null);
			}

		}
		return count == list.size() ? true : false;

	}

	/**
	 * 매개변수로 받은 dto가 갖는 일정정보를 DB에 등록한다. 일정정보는 크게 parent일정과 상세일정으로 나뉜다. 상세일정을 등록하기 위한
	 * 정보를 List 형태로 리턴한다(parent 일정의 seq)
	 */
	public int getaddTrip(ScheduleDto dto) {
		// parent 일정 등록부분

		int result = -1;

		String sql = " INSERT INTO SCHEDULES (SEQ, ID, TITLE, SDATE, EDATE, TOTALDAYS,"
				+ " MEMBER, COMPANION, SECTION, OPEN, WDATE )"
				+ " VALUES( SEQ_SCHEDULES.NEXTVAL, ? ,? ,? ,? ,?, ?,? ,?,? ,SYSDATE) ";

		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;

		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);

			int i = 1;
			psmt.setString(i++, dto.getId());
			psmt.setString(i++, dto.getTitle());
			psmt.setString(i++, dto.getSdate());
			psmt.setString(i++, dto.getEdate());
			psmt.setInt(i++, dto.getTotaldays());
			psmt.setInt(i++, dto.getMember());
			psmt.setString(i++, dto.getCompanion());
			psmt.setString(i++, dto.getSection());
			psmt.setInt(i++, dto.getOpen());
			// insert 쿼리 끝

			psmt.executeUpdate();
			// insert 쿼리 실행

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, null);
		}

		// parent 일정의 seq를 리턴하기위한 부분
		try {

			sql = " SELECT SEQ FROM SCHEDULES WHERE ID=? AND TITLE=? AND SDATE=? ORDER BY SEQ DESC ";
			// 찾을값 : 1. 검색조건에 해당하는 글이 몇개 있는지 = 배열의 length. 2. 각각 등록한 일정의 SEQ = 배열의 요소
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);

			int i = 1;
			psmt.setString(i++, dto.getId());
			psmt.setString(i++, dto.getTitle());
			psmt.setString(i++, dto.getSdate());

			rs = psmt.executeQuery();

			System.out.println("dd");
			while (rs.next()) {
				result = rs.getInt(1);
				System.out.println(result);
			}
			// 23, 24, 25
			// result.size() == 3;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, null);
		}

		return result;

	}
	
	/**DB에 addtitle에 해당하는 addschedule값을 ++
	 *
	 */
	public void addSchedule(String addtitle) {
		String sql = " UPDATE SIGHTS SET ADDSCHEDULE = ADDSCHEDULE+1 "
					+ " WHERE TITLE=? ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		try {
			
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, addtitle);
			rs = psmt.executeQuery();
	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
	}
}
