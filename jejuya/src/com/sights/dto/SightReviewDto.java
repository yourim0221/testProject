package com.sights.dto;

import java.io.Serializable;
/*
 -- TYPE : Oracle_11
-- NAME : New Oracle
-- DATABASE : xe

CREATE TABLE SIGHT_REVIEW (
	SEQ NUMBER NOT NULL PRIMARY KEY,
	TITLE VARCHAR2(400) NOT NULL,	
	ID VARCHAR2(400) NOT NULL,
	CONTENT VARCHAR2(4000),
	FILENAME VARCHAR2(400),
	WDATE DATE NOT NULL,	
	SCORE NUMBER(4),	
	DEL NUMBER(2)
);
--DROP TABLE SIGHT_REVIEW CASCADE CONSTRAINTS;
--

CREATE SEQUENCE SEQ_SIGHT_REVIEW START WITH 1 MAXVALUE 9999999999 INCREMENT BY 1;
--DROP SEQUENCE SEQ_SIGHT_REVIEW;

SELECT * FROM SIGHT_REVIEW;

--INSERT INTO SIGHT_REVIEW VALUES( SEQ_SIGHT_REVIEW.NEXTVAL, '테스트 제목', 'admin', '테스트 내용', '사진파일이름', 3); 
  
 */

public class SightReviewDto implements Serializable {
	
	private int seq;
	private String title;
	private String id;
	private String content;
	private String filename;
	private String wdate;
	private int score;
	private int del;
	
	public SightReviewDto() {
	}

	public SightReviewDto(int seq, String title, String id, String content, String filename, String wdate, int score,
			int del) {
		super();
		this.seq = seq;
		this.title = title;
		this.id = id;
		this.content = content;
		this.filename = filename;
		this.wdate = wdate;
		this.score = score;
		this.del = del;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getWdate() {
		return wdate;
	}

	public void setWdate(String wdate) {
		this.wdate = wdate;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getDel() {
		return del;
	}

	public void setDel(int del) {
		this.del = del;
	}

	@Override
	public String toString() {
		return "SightReviewDto [seq=" + seq + ", title=" + title + ", id=" + id + ", content=" + content + ", filename="
				+ filename + ", wdate=" + wdate + ", score=" + score + ", del=" + del + "]";
	}
	
	
		
		
	

}
