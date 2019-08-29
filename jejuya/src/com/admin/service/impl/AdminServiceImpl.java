package com.admin.service.impl;

import java.util.List;

import com.admin.dao.AdminDao;
import com.admin.dao.impl.AdminDaoImpl;
import com.admin.service.AdminService;
import com.member.dto.MemberDto;
import com.sights.dto.SightsDto;

public class AdminServiceImpl implements AdminService {

	
	AdminDao dao = AdminDaoImpl.getInstance();
	
	private static AdminService single = null;
	
	public AdminServiceImpl() {
	}
	
	public static AdminService getInstance() {
		if(single == null) {
			single = new AdminServiceImpl();
		}
		return single;
	}

	/* 회원 */
	
	@Override
	public MemberDto getMember(int seq) {
		return dao.getMember(seq);
	}

	@Override
	public boolean MemberMultiDelete(String ids[]) {
		return dao.MemberMultiDelete(ids);
	}

	@Override
	public List<MemberDto> getSerchMember(String sel, String searchW) {
		return dao.getSearchMember(sel, searchW);
	}

	@Override
	public int getAllMember(String sel, String searchW) {
		return dao.getAllMember(sel, searchW);
	}

	@Override
	public List<MemberDto> getMemberListPaging(int page, String sel, String searchW) {
		return dao.getMemberListPaging(page, sel, searchW);
	}

	
	/* 게시판 */
	

	
	@Override
	public List<SightsDto> getBbsList(int page, String sel, String searchW) {
		return dao.getBbsList(page, sel, searchW);
	}

	@Override
	public SightsDto getBbs(int seq) {
		return dao.getBbs(seq);
	}

	@Override
	public int getAllBbs(String category, String sel, String searchW) {
		return dao.getAllBbs(category, sel, searchW);
	}


	@Override
	public List<SightsDto> getBbsListPaging(String category, int page, String sel, String searchW) {
		return dao.getBbsListPaging(category, page, sel, searchW);
	}

	@Override
	public boolean addSight(SightsDto dto) {

		return dao.addSight(dto);
	}

	
	@Override
	public boolean BbsMultiDelete(String[] titles) {
		return dao.BbsMultiDelete(titles);
	}

	@Override
	public boolean bbsUpdate(int seq, SightsDto dto) {
		return dao.bbsUpdate(seq, dto);
	}
	
	
	
	
	
}
