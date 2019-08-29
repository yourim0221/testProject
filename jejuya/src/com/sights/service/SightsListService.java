package com.sights.service;

import java.util.List;

import com.sights.dto.SightPagingDto;
import com.sights.dto.SightSortCondition;
import com.sights.dto.SightsDto;

public interface SightsListService {
	
	public SightsDto getOneSightDetail(String title);
	
	public List<SightsDto> getSightslist(int category);
	
	public SightsDto getOneCategoryDto(int category);
	
	public List<String> getThemelist(int category);
	
	public List<SightsDto> getSightThemelist(String theme);
	
	public List<SightsDto> getScheduleSortSightlist(int category, String theme);
	
	public List<SightsDto> getReadSortSightlist(int category, String theme);
	
	public List<SightsDto> getScheduleSortSightlist(SightSortCondition cond);
	
	public List<SightsDto> getScheduleSortSightlist(SightSortCondition cond, SightPagingDto pageDto);
	
	public int getPageNumCount(SightSortCondition cond);
	
	public List<SightsDto> getPopularSightlist(SightSortCondition cond, SightPagingDto pageDto);
}
