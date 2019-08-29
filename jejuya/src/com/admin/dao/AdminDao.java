package com.admin.dao;

import java.util.List;

import com.member.dto.MemberDto;
import com.sights.dto.SightsDto;

public interface AdminDao {
	
	/* 회원 */
	
	public MemberDto getMember(int seq);
	
	public boolean MemberMultiDelete(String[] ids);
	
	public List<MemberDto> getSearchMember(String sel, String searchW);

	public int getAllMember(String sel, String searchW);
	
	public List<MemberDto> getMemberListPaging(int page, String sel, String searchW);
	
	
	/* 게시판  */
	
	public SightsDto getBbs(int seq);
	
	public List<SightsDto> getBbsList(int page, String sel, String searchW);
	
	public int getAllBbs(String category, String sel, String searchW);
	
	public List<SightsDto> getBbsListPaging(String category, int page, String sel, String searchW);
	
	public boolean addSight(SightsDto dto);
	
	public boolean BbsMultiDelete(String[] titles);
	
	public boolean bbsUpdate(int seq, SightsDto dto);

}
