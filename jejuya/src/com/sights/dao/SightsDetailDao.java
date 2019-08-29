package com.sights.dao;

import java.util.List;

import com.sights.dto.Paging;
import com.sights.dto.SightReviewDto;
import com.sights.dto.SightsDto;

public interface SightsDetailDao {
	
	public SightsDto getOneSightDetail(String title);
	
	public boolean addSight(SightsDto dto);
	
	public boolean addReview(SightReviewDto dto);
	
	public List<SightReviewDto> getReviewList(String title);
	
	public int reviewAllCount(String title);
	
	public List<SightReviewDto> pagingReviewList(String title, Paging paging, String scoreSorting);
	
	public boolean reviewDel(int seq);
	
	public int avgReviewScore(String title);
	
	public void readCount(String title);
}
