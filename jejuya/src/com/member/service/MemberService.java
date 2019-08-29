package com.member.service;

import java.util.List;

import com.member.dto.MemberDto;

public interface MemberService {

	public boolean addMember(MemberDto dto);
	
	public MemberDto getOneMember(String id);
	
	public boolean isExistingId(String id);
	
	public List<MemberDto> getMemberList();
	
	public MemberDto getMemberDetail(String id);
	
	public MemberDto getOneMemberByName(String inputName);

	public int updatePw(String id, String newPw);
}
