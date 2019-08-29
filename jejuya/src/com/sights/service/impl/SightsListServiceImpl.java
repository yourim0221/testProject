package com.sights.service.impl;

import java.util.List;

import com.sights.dao.SightsListDao;
import com.sights.dao.impl.SightsListDaoImpl;
import com.sights.dto.SightPagingDto;
import com.sights.dto.SightSortCondition;
import com.sights.dto.SightsDto;
import com.sights.service.SightsListService;

public class SightsListServiceImpl implements SightsListService{
	
	SightsListDao dao = SightsListDaoImpl.getInstance();
	
	private static SightsListService single = null;
	
	private SightsListServiceImpl() {}
	
	public static SightsListService getInstance() {
		if(single == null) {
			single = new SightsListServiceImpl();
		}
		return single;
	}		

	@Override
	public SightsDto getOneSightDetail(String title) {
		// TODO Auto-generated method stub
		return dao.getOneSightDetail(title);
	}
	
	@Override
	public List<SightsDto> getSightslist(int category) {
		// TODO Auto-generated method stub
		return dao.getSightslist(category);
	}
	
	@Override
	public SightsDto getOneCategoryDto(int category) {
		// TODO Auto-generated method stub
		return dao.getOneCategoryDto(category);
	}
	
	@Override
	public List<String> getThemelist(int category) {
		// TODO Auto-generated method stub
		return dao.getThemelist(category);
	}
	
	@Override
	public List<SightsDto> getSightThemelist(String theme) {
		// TODO Auto-generated method stub
		return dao.getSightThemelist(theme);
	}
	
	@Override
	public List<SightsDto> getReadSortSightlist(int category, String theme) {
		// TODO Auto-generated method stub
		return dao.getReadSortSightlist(category, theme);
	}
	
	@Override
	public List<SightsDto> getScheduleSortSightlist(int category, String theme) {
		// TODO Auto-generated method stub
		return dao.getScheduleSortSightlist(category, theme);
	}

	@Override
	public List<SightsDto> getScheduleSortSightlist(SightSortCondition cond) {
		// TODO Auto-generated method stub
		return dao.getScheduleSortSightlist(cond);
	}

	@Override
	public List<SightsDto> getScheduleSortSightlist(SightSortCondition cond, SightPagingDto pageDto) {
		// TODO Auto-generated method stub
		return dao.getScheduleSortSightlist(cond, pageDto);
	}
		
	@Override
	public int getPageNumCount(SightSortCondition cond) {
		// TODO Auto-generated method stub
		return dao.getPageNumCount(cond);
	}

	@Override
	public List<SightsDto> getPopularSightlist(SightSortCondition cond, SightPagingDto pageDto) {
		// TODO Auto-generated method stub
		return dao.getPopularSightlist(cond, pageDto);
	}	
}
