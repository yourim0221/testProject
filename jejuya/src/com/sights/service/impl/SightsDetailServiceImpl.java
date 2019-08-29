package com.sights.service.impl;

import java.util.List;

import com.sights.dao.SightsDetailDao;
import com.sights.dao.impl.SightsDetailDaoImpl;
import com.sights.dto.Paging;
import com.sights.dto.SightReviewDto;
import com.sights.dto.SightsDto;
import com.sights.service.SightsDetailService;

public class SightsDetailServiceImpl implements SightsDetailService{
	
	SightsDetailDao dao = SightsDetailDaoImpl.getInstance();
	
	private static SightsDetailService single = null;
	
	private SightsDetailServiceImpl() {}
	
	public static SightsDetailService getInstance() {
		if(single == null) {
			single = new SightsDetailServiceImpl();
		}
		return single;
	}		

	@Override
	public SightsDto getOneSightDetail(String title) {
		return dao.getOneSightDetail(title);
	}
	
	@Override
	public boolean addSight(SightsDto dto) {
		return dao.addSight(dto);
	}

	@Override
	public boolean addReview(SightReviewDto dto) {
		return dao.addReview(dto);
	}

	@Override
	public List<SightReviewDto> getReviewList(String title) {
		return dao.getReviewList(title);
	}

	@Override
	public int reviewAllCount(String title) {
		return dao.reviewAllCount(title);
	}

	@Override
	public List<SightReviewDto> pagingReviewList(String title, Paging paging, String scoreSorting) {
		return dao.pagingReviewList(title, paging, scoreSorting);
	}

	@Override
	public boolean reviewDel(int seq) {
		return dao.reviewDel(seq);
	}

	@Override
	public int avgReviewScore(String title) {
		return dao.avgReviewScore(title);
	}

	@Override
	public void readCount(String title) {
		dao.readCount(title);
	}	
}
