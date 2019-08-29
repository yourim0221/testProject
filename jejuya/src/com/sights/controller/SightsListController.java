package com.sights.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sights.dto.SightPagingDto;
import com.sights.dto.SightSortCondition;
import com.sights.dto.SightsDto;
import com.sights.service.SightsDetailService;
import com.sights.service.SightsListService;
import com.sights.service.impl.SightsDetailServiceImpl;
import com.sights.service.impl.SightsListServiceImpl;

@WebServlet("/SightsListController")
public class SightsListController extends HttpServlet {

	
	private static final long serialVersionUID = 1L;
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//인코딩 세팅
		req.setCharacterEncoding("UTF-8");
		
		
		String command = req.getParameter("command");
//		System.out.println(command + "왔음");
		
		
		SightsListService service = SightsListServiceImpl.getInstance();
		SightsDetailService sightsDetailService = SightsDetailServiceImpl.getInstance();
		
		// 사진 눌렀을때 command값 SightDetail 사진의 title값으로 Sights테이블 DTO 리턴
		if(command.equals("SightDetail")) {
			String title = req.getParameter("title");

			SightsDto dto = service.getOneSightDetail(title);
			
//			System.out.println(dto.toString());
		}
		
		// 위의 창이나 
		if(command.equals("category")) {
			String strcategory = req.getParameter("category");
//			System.out.println(strcategory);
			int category = Integer.parseInt(strcategory);
			
			List<SightsDto> list = service.getSightslist(category);
			req.setAttribute("sightslist", list);
			
			SightsDto dto = service.getOneCategoryDto(category);
			req.setAttribute("categorydto", dto);
			
			List<String> list1 = service.getThemelist(category);
			req.setAttribute("themelist", list1);
			
			req.getRequestDispatcher("/views/sights/sightlist.jsp").forward(req, resp);
			
			
		}
		
		if(command.equals("theme")) {
//			System.out.println("doget 들어옴");
			
			String theme = req.getParameter("theme");
//			System.out.println(theme);
			
			String strcategory = req.getParameter("category");
//			System.out.println(strcategory);
			int category = Integer.parseInt(strcategory);
			
			
			List<SightsDto> list = service.getSightThemelist(theme);
			req.setAttribute("sightslist", list);
			
			SightsDto dto = service.getOneCategoryDto(category);
			req.setAttribute("categorydto", dto);
			
			List<String> list1 = service.getThemelist(category);
			req.setAttribute("themelist", list1);
			
			req.getRequestDispatcher("/views/sights/sightlist.jsp").forward(req, resp);
		}
		
		if(command.equals("addSchedule")) {
			
			String strcategory = req.getParameter("category");
			int category = Integer.parseInt(strcategory);
			
			String theme = req.getParameter("theme");
			
			List<SightsDto> list = service.getScheduleSortSightlist(category, theme);
			req.setAttribute("sightslist", list);
			
			SightsDto dto = service.getOneCategoryDto(category);
			req.setAttribute("categorydto", dto);
			
			List<String> list1 = service.getThemelist(category);
			req.setAttribute("themelist", list1);
			
			req.getRequestDispatcher("/views/sights/sightlist.jsp").forward(req, resp);
		}
		
		if(command.equals("readcount")) {
			
			String strcategory = req.getParameter("category");
			int category = Integer.parseInt(strcategory);
			
			String theme = req.getParameter("theme");
			
			List<SightsDto> list = service.getReadSortSightlist(category, theme);
			req.setAttribute("sightslist", list);
			
			SightsDto dto = service.getOneCategoryDto(category);
			req.setAttribute("categorydto", dto);
			
			List<String> list1 = service.getThemelist(category);
			req.setAttribute("themelist", list1);
			
			req.getRequestDispatcher("/views/sights/sightlist.jsp").forward(req, resp);
		}
		
		if( command.equals("sortByConditionInSightlist") ) {
			//받아들인 설정값에 따라 관.음.숙을 정렬하고 선별해서 sightlist에 보여주는 컨트롤러 메소드
			
			System.out.println("정렬 시작하겠습니다.");
			
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
						
			SightSortCondition sortCon = new SightSortCondition(category, theme, sortSel, searchStr);
			
			//service에서 list 받아옴
			//List<SightsDto> list = service.getScheduleSortSightlist( sortCon );
			List<SightsDto> list = service.getScheduleSortSightlist( sortCon, new SightPagingDto(pageNum) );
			
			req.setAttribute("sightslist", list);
			
			//category 설정(관,음,숙)
			SightsDto dto = service.getOneCategoryDto(category);
			req.setAttribute("categorydto", dto);
			
			// title로 리뷰 갯수 보내기
			String strtitle = dto.getTitle();
			int reviewcount = sightsDetailService.reviewAllCount(strtitle);
			
			Map<String, Integer> reviewCountMap = new HashMap<String, Integer>();
			for(SightsDto d : list) {
				reviewCountMap.put(d.getTitle(), sightsDetailService.reviewAllCount(d.getTitle()));
			}
			//System.out.println("리뷰카운트 : " +  reviewCountMap.toString() + " 끝");
			
			req.setAttribute("reviewcount", reviewCountMap);
			
			//theme 설정(cafe, food 등)
			List<String> themeList = service.getThemelist(category);
			req.setAttribute("themelist", themeList);
			
			//페이지넘버카운트 
			int pagecount = service.getPageNumCount(sortCon);
			System.out.println("pagecount: " +pagecount);
			req.setAttribute("pagecount", pagecount);
			
			
			//검색 텍스트
			req.setAttribute("searchStr", searchStr);
			
			//리뷰DTO 받기
			
			
			
			req.getRequestDispatcher("/views/sights/sightlist.jsp").forward(req, resp);
		}
		
		
		
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//인코딩 세팅
		req.setCharacterEncoding("UTF-8");
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
