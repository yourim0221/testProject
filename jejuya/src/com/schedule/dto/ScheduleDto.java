package com.schedule.dto;

public class ScheduleDto {

	
	int seq;
	String id;
	String title;
	String sdate;
	String edate;
	int totaldays;
	String wdate;
	int member;
	String companion;
	String section;
	int open;
	
	public ScheduleDto() {
		
	}
	
	
	public ScheduleDto(String id, String title, String sdate, String edate, int totaldays, int member, String companion,
			String section, int open) {
		super();
		this.id = id;
		this.title = title;
		this.sdate = sdate;
		this.edate = edate;
		this.totaldays = totaldays;
		this.member = member;
		this.companion = companion;
		this.section = section;
		this.open = open;
	}


	public ScheduleDto(int seq, String id, String title, String sdate, String edate, int totaldays, String wdate,
			int member, String companion, String section, int open) {
		super();
		this.seq = seq;
		this.id = id;
		this.title = title;
		this.sdate = sdate;
		this.edate = edate;
		this.totaldays = totaldays;
		this.wdate = wdate;
		this.member = member;
		this.companion = companion;
		this.section = section;
		this.open = open;
	}

	@Override
	public String toString() {
		return "ScheduleDto [seq=" + seq + ", id=" + id + ", title=" + title + ", sdate=" + sdate + ", edate=" + edate
				+ ", totaldays=" + totaldays + ", wdate=" + wdate + ", member=" + member + ", companion=" + companion
				+ ", section=" + section + ", open=" + open + "]";
	}

	
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSdate() {
		return sdate;
	}
	public void setSdate(String sdate) {
		this.sdate = sdate;
	}
	public String getEdate() {
		return edate;
	}
	public void setEdate(String edate) {
		this.edate = edate;
	}
	public int getTotaldays() {
		return totaldays;
	}
	public void setTotaldays(int totaldays) {
		this.totaldays = totaldays;
	}
	public String getWdate() {
		return wdate;
	}
	public void setWdate(String wdate) {
		this.wdate = wdate;
	}
	public int getMember() {
		return member;
	}
	public void setMember(int member) {
		this.member = member;
	}
	public String getCompanion() {
		return companion;
	}
	public void setCompanion(String companion) {
		this.companion = companion;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public int getOpen() {
		return open;
	}
	public void setOpen(int open) {
		this.open = open;
	}
}
