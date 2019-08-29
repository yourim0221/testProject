package com.admin.service;

import java.util.List;

import com.admin.dto.NoticeDto;
import com.admin.dto.NoticePagingDto;

public interface NoticeService {
	
	public boolean insertNewNotice(NoticeDto dto);
	
	public boolean updateNotice(NoticeDto dto);
	
	public boolean deleteNotice(int seq);
	
	public boolean deleteMultipleNotice(String[] seqs);

	public List<NoticeDto> getAllNoticeList(NoticePagingDto pagingDto);
	
	public int getNoticeDBSize();
	
	public NoticeDto getOneNoticeDetail(int seq);	
	
}
