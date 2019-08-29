package com.admin.service.impl;

import java.util.List;

import com.admin.dao.NoticeDao;
import com.admin.dao.impl.NoticeDaoImpl;
import com.admin.dto.NoticeDto;
import com.admin.dto.NoticePagingDto;
import com.admin.service.NoticeService;

public class NoticeServiceImpl implements NoticeService {

	public static NoticeServiceImpl single = null;
	
	private NoticeDao dao = NoticeDaoImpl.getInstance();
		
	private NoticeServiceImpl() {}
	
	public static NoticeServiceImpl getInstance() {
		if( single == null ) {
			single = new NoticeServiceImpl();
		}
		return single;
	}

	@Override
	public boolean insertNewNotice(NoticeDto dto) {
		// TODO Auto-generated method stub
		return dao.insertNewNotice(dto);
	}

	@Override
	public boolean updateNotice(NoticeDto dto) {
		// TODO Auto-generated method stub
		return dao.updateNotice(dto);
	}
	
	@Override
	public boolean deleteNotice(int seq) {
		// TODO Auto-generated method stub
		return dao.deleteNotice(seq);
	}

	@Override
	public boolean deleteMultipleNotice(String[] seqs) {
		// TODO Auto-generated method stub
		return dao.deleteMultipleNotice(seqs);
	}
	
	@Override
	public List<NoticeDto> getAllNoticeList(NoticePagingDto pagingDto) {
		// TODO Auto-generated method stub
		return dao.getAllNoticeList(pagingDto);
	}

	@Override
	public int getNoticeDBSize() {
		// TODO Auto-generated method stub
		return dao.getNoticeDBSize();
	}

	@Override
	public NoticeDto getOneNoticeDetail(int seq) {
		// TODO Auto-generated method stub
		return dao.getOneNoticeDetail(seq);
	}
	
}
