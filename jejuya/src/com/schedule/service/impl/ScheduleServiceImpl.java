package com.schedule.service.impl;

import java.util.List;

import com.schedule.dao.ScheduleDao;
import com.schedule.dao.impl.ScheduleDaoImpl;
import com.schedule.dto.ScheduleDto;
import com.schedule.dto.ScheduledetailDto;
import com.schedule.service.ScheduleService;
import com.sights.dto.SightsDto;
import com.sun.org.apache.regexp.internal.recompile;

import common.util.pageDto;

public class ScheduleServiceImpl implements ScheduleService {

	ScheduleDao dao = ScheduleDaoImpl.getInstance();

	private static ScheduleService single = null;

	private ScheduleServiceImpl() {
	}

	public static ScheduleService getInstance() {
		if (single == null) {
			single = new ScheduleServiceImpl();
		}
		return single;
	}

	public List<SightsDto> getSightslist(int num) {
		return dao.getSightslist(num);
	}

	public List<SightsDto> getPajingtowrismlist(int num, int startnum, int lastnum) {
		return dao.getPajingtowrismlist(num, startnum, lastnum);
	}

	public pageDto page(int total, int pg) {
		return dao.page(total, pg);
	}

	public int pagenum(int total) {
		return dao.pagenum(total);
	}

	public List<SightsDto> getLangkingchart(String theme) {
		return dao.getLangkingchart(theme);
	}

	public int getaddTrip(ScheduleDto dto) {
		return dao.getaddTrip(dto);
	}

	public boolean getadddetailSchedule(List<ScheduledetailDto> list) {
		return dao.getadddetailSchedule(list);
	}

	public List<ScheduleDto> getmySchedulelist(String id) {
		return dao.getmySchedulelist(id);
	}

	public List<SightsDto> getLanking(int category) {
		return dao.getLanking(category);
	}

	public ScheduleDto getDetail(int seq) {
		return dao.getDetail(seq);
	}

	public List<ScheduledetailDto> getsheduleDetail(int seq) {
		return dao.getsheduleDetail(seq);
	}

	public boolean update(ScheduleDto dto, int seq) {
		return dao.update(dto, seq);
	}

	public boolean delDetailtrip(int seq) {
		return dao.delDetailtrip(seq);
	}

	public boolean deltrip(int seq) {
		return dao.deltrip(seq);
	}

	public List<ScheduleDto> getsearchSchedulelist(String title) {
		return dao.getsearchSchedulelist(title);
	}
	
	public void addSchedule(String addtitle) {
		dao.addSchedule(addtitle);
	}
	
}
