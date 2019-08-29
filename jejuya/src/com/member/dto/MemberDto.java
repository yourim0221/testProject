package com.member.dto;

import java.io.Serializable;

public class MemberDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5961651102043287480L;
	
	
	private String id;
	private int seq;
	private String pw;
	private String name;
	private String email;
	private String birth;
	private int del;
	private int isadmin; // 사용자/관리자

	public MemberDto() {	}
	//	dto = new MemberDto(id, seq, pw, name, email, birth);
	//			 MemberDto(id, pw, name, email, birth)
	
	/** 회원가입용도로 사용하기 위한 생성자
	 * @param id
	 * @param pw
	 * @param name
	 * @param email
	 * @param birth
	 */
	public MemberDto(String id, String pw, String name, String email, String birth) {
		super();
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.email = email;
		this.birth = birth;
	}
	
	/** 6 args 생성자
	 * @param id
	 * @param seq
	 * @param pw
	 * @param name
	 * @param email
	 * @param birth
	 */
	public MemberDto( String id, int seq, String pw, String name, String email, String birth ) {
		this.id = id;
		this.seq = seq;
		this.pw = pw;
		this.name = name;
		this.email = email;
		this.birth = birth;
	}	
	
	/** all args constructor
	 * @param id
	 * @param seq
	 * @param pw
	 * @param name
	 * @param email
	 * @param birth
	 * @param del
	 * @param isadmin
	 */
	public MemberDto(String id, int seq, String pw, String name, String email, String birth, int del, int isadmin) {
		this.id = id;
		this.seq = seq;
		this.pw = pw;
		this.name = name;
		this.email = email;
		this.birth = birth;
		this.del = del;
		this.isadmin = isadmin;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public int getDel() {
		return del;
	}

	public void setDel(int del) {
		this.del = del;
	}

	public int getIsadmin() {
		return isadmin;
	}

	public void setIsadmin(int isadmin) {
		this.isadmin = isadmin;
	}
	
	@Override
	public String toString() {
		return "MemberDto [id=" + id + ", seq=" + seq + ", pw=" + pw + ", name=" + name + ", email=" + email
				+ ", birth=" + birth + ", del=" + del + ", isadmin=" + isadmin + "]";
	}	
	
	
}