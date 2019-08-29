package com.main.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.admin.dto.NoticeDto;
import com.admin.dto.NoticePagingDto;
import com.admin.service.NoticeService;
import com.admin.service.impl.NoticeServiceImpl;
import com.sights.dto.SightPagingDto;
import com.sights.dto.SightSortCondition;
import com.sights.dto.SightsDto;
import com.sights.service.SightsListService;
import com.sights.service.impl.SightsListServiceImpl;


@SuppressWarnings("serial")
@WebServlet("/main")
public class MainController extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("[MainController] do get");
		
		//0, "all", "addschedule"
		SightsListService sightsService = SightsListServiceImpl.getInstance();
		NoticeService noticeService = NoticeServiceImpl.getInstance();
		
		//일정등록 많은 순으로 카테고리 별 (관음숙) 아이템 8개 받아오도록 조건 설정
//		SightSortCondition cond = new SightSortCondition(0, "all", "addschedule");
//		SightPagingDto pagingDto = new SightPagingDto(1);
//		pagingDto.setEndRnum(8);
		
		
		//get방식으로 넘어온 파라미터 recieve
		//String strcategory = req.getParameter("category");
		String strcategory = nvlInCnt(req, "category", "0");
		int category = Integer.parseInt(strcategory);
		
		//String theme = req.getParameter("theme");
		String theme = nvlInCnt(req, "theme", "all");
		System.out.println("theme : " + theme);
		
		//String sortSel = req.getParameter("_sort_sel");
		String sortSel = nvlInCnt(req, "_sort_sel", "all");
		
		//서치 텍스트 
		String searchStr = nvlInCnt(req, "searchStr", "all");
		System.out.println("searchStr:" + searchStr);
		
		//페이지넘버 지정(pageNum)
		String strpageNum = nvlInCnt(req, "pageNum", "1");
		int pageNum = Integer.parseInt( strpageNum );
		SightPagingDto pagingDto = new SightPagingDto();
		pagingDto.setStartRnum(1);
		pagingDto.setEndRnum(15);
		
		SightSortCondition sortCon = new SightSortCondition(category, theme, sortSel, searchStr);
		
		//service에서 list 받아옴 - 모든 카테고리 인기 순
		List<SightsDto> mainListAllCate = sightsService.getScheduleSortSightlist(sortCon,pagingDto );
		
		//사이트맵 리스트
		List<SightsDto> mainSiteMapList = sightsService.getPopularSightlist(sortCon, pagingDto );
				
		req.setAttribute("mainListAllCate", mainListAllCate);
		req.setAttribute("mainSiteMapList", mainSiteMapList);
				
		//공지사항 attr 추가
		int totalNoticeSize = noticeService.getNoticeDBSize();
		NoticePagingDto noticePagingDto = new NoticePagingDto(1, totalNoticeSize);
		List<NoticeDto> mainNoticeList = noticeService.getAllNoticeList(noticePagingDto);
		
		req.setAttribute("mainNoticeList", mainNoticeList);
		req.setAttribute("noticePagingDto", noticePagingDto);
		//System.out.println("공지사항 추가 했습니다!");
				
		//메인뷰로 이동
		req.getRequestDispatcher("/views/main.jsp").forward(req, resp);
		
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("[MainController] do post");
	}
	
	/**파라미터와 대체텍스트를 입력받고, 파라미터가 널이면 대체텍스틀 리턴 아니라면 겟파라미터
	 * @param req
	 * @param param
	 * @param altStr
	 * @return
	 */
	public String nvlInCnt(HttpServletRequest req, String param, String altStr) {
		return ( req.getParameter( param ) == null )? altStr : req.getParameter( param ) ;
	}
}
