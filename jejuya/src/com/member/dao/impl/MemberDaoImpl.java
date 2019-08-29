package com.member.dao.impl;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.member.dao.MemberDao;
import com.member.dto.MemberDto;
import common.db.DBClose;
import common.db.DBConnection;

public class MemberDaoImpl implements MemberDao {
	private static MemberDaoImpl memberDao = null;

	private MemberDaoImpl() {
		DBConnection.initConnect();
	}

	public static MemberDaoImpl getInstance() {
		if( memberDao == null ) {
			memberDao = new MemberDaoImpl();
		}
		return memberDao;
	}

	/**DB에 회원 추가
	 * @param MemberDto dto
	 * @return
	 */
	public boolean addMember(MemberDto dto) {
		String sql = "INSERT INTO MEMBER_JEJU (ID, SEQ, PW, NAME, EMAIL, BIRTH, DEL, ISADMIN)"
				+ " VALUES(	?, SEQ_MEMBER_JEJU.NEXTVAL , "
				+ " ?, ?, ? , ?, 0, 1 ) " ;
		
		
		Connection conn = null;
		PreparedStatement psmt = null;

		int count = 0;

		try {
			//conn = DBConnection.makeConnection();
			
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			System.out.println("1/6 addMember success");

			System.out.println(dto.getPw());
			
			String pw = dto.getPw();
			MessageDigest md = MessageDigest.getInstance("SHA-256");
		    md.update(pw.getBytes());
		    pw = byteToHexString(md.digest());

			
			psmt.setString(1, dto.getId());
			//psmt.setString(2, dto.getpw());
			psmt.setString(2, pw);
			psmt.setString(3, dto.getName());
			psmt.setString(4, dto.getEmail());
			psmt.setString(5, dto.getBirth());

			count = psmt.executeUpdate();
			System.out.println("2/6 addMember success");

		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBClose.close(conn, psmt, null);
			System.out.println("4/6 addMember success");
		}
		System.out.println("END addMember success");

		return count > 0 ? true : false;

	}
	
	/**매개변수로 받은 id를 MEMBER_JEJU DB에서 검색하고, MemberDto로 리턴
	 * @param String inputId
	 * @return
	 */
	public MemberDto getOneMember(String inputId) {
		MemberDto dto = null;
		
		String sql = " SELECT * FROM MEMBER_JEJU WHERE ID='" + inputId + "' ";
		
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement psmt = null;
		
		try {
			conn = DBConnection.getConnection();
			
			psmt = conn.prepareStatement(sql);
			
			rs = psmt.executeQuery();
			
			int del = 0;
						
			while( rs.next() ) {
				int i = 1;
				String id = rs.getString(i++);
				int seq = rs.getInt(i++);
				String pw = rs.getString(i++);
				String name = rs.getString(i++);
				String email = rs.getString(i++);
				String birth = rs.getString(i++);	
				del = rs.getInt(i++);
				int isAdmin = rs.getInt(i++);
				
				if(del == 1) {//삭제된 계정이면 null 리턴
					return null;
				}
				
				dto = new MemberDto(id, seq, pw, name, email, birth, del, isAdmin);
			}					
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return dto;
	}
/*
 * 	public MemberDto login(MemberDto dto) {

		String sql = "SELECT ID, NAME, EMAIL,AUTH" + " FROM MEMBER" + "WHERE ID=? AND pw=?";

		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;

		MemberDto mem = null;

		try {

			conn = DBConnection.makeConnection();
			psmt = conn.prepareStatement(sql);
			System.out.println("1/6 addMember success");

			psmt.setString(1, dto.getId().trim());
			psmt.setString(2, dto.getpw());
			System.out.println("2/6 login success");

			rs = psmt.executeQuery();
			System.out.println("3/6 login success");

			if (rs.next()) {
				String id = rs.getString(1);
				String name = rs.getString(2);
				String email = rs.getString(3);
				int auth = rs.getInt(4);

				mem = new MemberDto(id, name, email, birth, auth);

			}
			System.out.println("4/6 login success");

		} catch (SQLException e) {
			System.out.println("login fail");

		} finally {
			DBClose.close(conn, psmt, rs);

		}
		return mem;
*/

	/**매개변수로 받은 name을 MEMBER_JEJU DB에서 검색하고, MemberDto로 리턴
	 * @param inputName
	 * @return
	 */
	public MemberDto getOneMemberByName(String inputName) {
		MemberDto dto = null;
		
		String sql = " SELECT * FROM MEMBER_JEJU WHERE NAME='" + inputName + "' ";
		System.out.println("이름찾았다!!!" + inputName);
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement psmt = null;
		
		try {
			conn = DBConnection.getConnection();
			
			psmt = conn.prepareStatement(sql);
			
			rs = psmt.executeQuery();
			
			int del = 0;
						
			while( rs.next() ) {
				int i = 1;
				String id = rs.getString(i++);
				int seq = rs.getInt(i++);
				String pw = rs.getString(i++);
				String name = rs.getString(i++);
				String email = rs.getString(i++);
				String birth = rs.getString(i++);	
				del = rs.getInt(i++);
				
				if(del == 1) {//삭제된 계정이면 null 리턴
					return null;
				}
				
				dto = new MemberDto(id, seq, pw, name, email, birth);
			}					
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		System.out.println("끝냈다!!! " + dto.toString());
		return dto;
	}
	
	/** 매개변수로 받은 id가 중복된 ID인지 여부를 리턴하는 메소드
	 * @param String id
	 * @return 중복된 아이디면 false, 사용가능 아이디면 true 리턴
	 */
	public boolean isExistingId(String inputId) {
		boolean isExisting = true;
		
		String sql = " SELECT * FROM MEMBER_JEJU WHERE ID='" + inputId + "' ";
		
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement psmt = null;
		
		try {
			conn = DBConnection.getConnection();
			
			psmt = conn.prepareStatement(sql);
			
			rs = psmt.executeQuery();
			
			int del = 0;
						
			while( rs.next() ) {
				//ID가 있는 경우 처리할 내용 
				del = rs.getInt("DEL");
				
				//rs.next가 수행되면 결과값이 있는것이므로 중복ID = 사용불가 = false
				isExisting = false;						
			}					
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		return isExisting;
	}

	/**
	 * 회원들의 리스트를 전부 가져오는 메소드
	 */
	@Override
	public List<MemberDto> getMemberList() {
		String sql = " SELECT ID, SEQ, PW, NAME, EMAIL, BIRTH "
				+ " FROM MEMBER_JEJU "
				+ " WHERE ISADMIN=1"
				+ " ORDER BY SEQ DESC ";
		
		System.out.println("getMemberList sql : " + sql);
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		List<MemberDto> list = new ArrayList<>();
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/6 getMemberLsit suc");
			
			psmt = conn.prepareStatement(sql);
			System.out.println("2/6 getMemberLsit suc");
			
			rs = psmt.executeQuery();
			System.out.println(list.toString());
			System.out.println("3/6 getMemberLsit suc");
			
			while(rs.next()) {
				String id = rs.getString("ID");
				int seq = rs.getInt("SEQ");
				String pw = rs.getString("PW");
				String name = rs.getString("NAME");
				String email = rs.getString("EMAIL");
				String birth = rs.getString("BIRTH");
				
				MemberDto dto = new MemberDto(id, seq, pw, name, email, birth);
				list.add(dto);
				System.out.println("4/6 getMemberLsit suc");
			}
			
			
		} catch (Exception e) {
			System.out.println("getMemberList fail");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, null);
		}
		
		return list;
	}
	
	/**
	 * 리스트에 뿌려진 회원의 정보를 상세하게 얻어오기 위한 메소드 
	 */
	@Override
	public MemberDto getMemberDetail(String id) {

		String sql = " SELECT * FROM MEMBER_JEJU "
					+ " ID=? ";
		
		System.out.println("getMemberDetail sql : " + sql );
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		MemberDto dto = null;
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/6 getMemberDetail suc");

			psmt = conn.prepareStatement(sql);
			System.out.println("2/6 getMemberDetail suc");

			rs = psmt.executeQuery();
			System.out.println("3/6 getMemberDetail suc");
			
			if(rs.next()) {
				dto = new MemberDto();
				dto.setId(rs.getString("ID"));
				dto.setPw(rs.getString("PW"));
				dto.setName(rs.getString("NAME"));
				dto.setEmail(rs.getString("EMAIL"));
				dto.setBirth(rs.getString("BIRTH"));
				
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		
		return dto;
	}

	

	/**비밀번호를 수정하는 메소드. 대상은 매개변수로 받은 id, 새 비밀번호는 매개변수로 받은 newPw
	 * @param id
	 * @param newPw
	 * @return
	 */
	public int updatePw(String id, String newPw) {
		int updateDone = -1;
		
		String sql = " UPDATE MEMBER_JEJU SET PW=? WHERE ID=? ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		
		try {
			conn = DBConnection.getConnection();			
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, newPw);
			psmt.setString(2, id);
			
			updateDone = psmt.executeUpdate();			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return updateDone;
	}
	
	
	/**
	 * 바이트 배열을 HEX 문자열로 변환한다.
	 * @param byte[] data
	 * @return
	 */
	public static String byteToHexString(byte[] data) {
		StringBuilder sb = new StringBuilder();
		for(byte b : data) {
			sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
		}
		return sb.toString();
	}

}
