package com.schedule.dto;

public class ScheduledetailDto {

	private String schedate;  //일정의 해당 일 ex)첫번째 날 두번째 날
	private String stime;//시간
	private String dest;//장소
	private int parentseq; //부모 시퀀스

   
	
	public ScheduledetailDto() {
		
	}
	
	public ScheduledetailDto(String schedate, String stime, String dest, int parentseq) {
		super();
		this.schedate = schedate;
		this.stime = stime;
		this.dest = dest;
		this.parentseq = parentseq;
	}

	public String getSchedate() {
		return schedate;
	}
	public void setSchedate(String schedate) {
		this.schedate = schedate;
	}
	public String getStime() {
		return stime;
	}
	public void setStime(String stime) {
		this.stime = stime;
	}
	public String getDest() {
		return dest;
	}
	public void setDest(String dest) {
		this.dest = dest;
	}
	public int getParentseq() {
		return parentseq;
	}
	public void setParentseq(int parentseq) {
		this.parentseq = parentseq;
	}


	@Override
	public String toString() {
		return "ScheduledetailDto [schedate=" + schedate + ", stime=" + stime + ", dest=" + dest + ", parentseq="
				+ parentseq + "]";
	}
	
}
