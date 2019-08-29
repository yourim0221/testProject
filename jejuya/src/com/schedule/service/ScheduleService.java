package com.schedule.service;

import java.util.List;

import com.schedule.dto.ScheduleDto;
import com.schedule.dto.ScheduledetailDto;
import com.sights.dto.SightsDto;

import common.util.pageDto;

public interface ScheduleService {

	public List<SightsDto> getSightslist(int num);
	public List<SightsDto> getPajingtowrismlist( int num , int startnum,int lastnum);
	public pageDto page(int total, int pg);
	public int pagenum(int total);
	public  List<SightsDto> getLangkingchart(String theme);
	public int getaddTrip(ScheduleDto dto);
	public boolean getadddetailSchedule(List<ScheduledetailDto> list);
	public List<ScheduleDto> getmySchedulelist(String id);
	public List<SightsDto> getLanking(int category);
	public ScheduleDto getDetail(int seq);
	public List<ScheduledetailDto> getsheduleDetail(int seq);
	public boolean update(ScheduleDto dto, int seq);
	public boolean deltrip(int seq);
	public boolean delDetailtrip(int seq) ;
	public List<ScheduleDto> getsearchSchedulelist(String title);
	public void addSchedule(String addtitle);
}
