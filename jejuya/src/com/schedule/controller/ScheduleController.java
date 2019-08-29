package com.schedule.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.member.dto.MemberDto;
import com.schedule.dto.ScheduleDto;
import com.schedule.dto.ScheduledetailDto;
import com.schedule.service.ScheduleService;
import com.schedule.service.impl.ScheduleServiceImpl;
import com.sights.dto.SightsDto;
import com.sun.org.apache.bcel.internal.generic.LUSHR;
import com.sun.xml.internal.bind.v2.runtime.Location;

import common.util.pageDto;

@WebServlet("/ScheduleController")
public class ScheduleController extends HttpServlet{

	
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	

		
		String command=req.getParameter("command");
	
		ScheduleService service = ScheduleServiceImpl.getInstance();
		
		if (command.equals("tourism")) {

			List<SightsDto> list0 = service.getSightslist(0);// 관광지
			List<SightsDto> list1 = service.getSightslist(1);// 음식점
			List<SightsDto> list2 = service.getSightslist(2);// 숙소

			req.setAttribute("list0", list0);// 관광지 리스트 보내기
			req.setAttribute("list1", list1);// 음식점 리스트 보내기
			req.setAttribute("list2", list2);// 숙소 리스트 보내기

			int count = service.pagenum(list0.size());
			req.setAttribute("count", count);
			req.getRequestDispatcher("/views/schedule/scheduleadd.jsp").forward(req, resp);

		} else if (command.equals("tourismAjaxTblTriblist") ) {
			resp.setContentType("text/html; charset=utf-8");
			String pg=req.getParameter("pg");
			int category=Integer.parseInt(req.getParameter("category"));
			
			System.out.println("pg:"+pg+"category"+category);
			
			List<SightsDto> list0=service.getSightslist(category);
		
		
			pageDto page=service.page(list0.size(), Integer.parseInt(pg));
			
			List<SightsDto> list00 = service.getPajingtowrismlist(category, page.getStartnum(), page.getLastnum());
			
			
			System.out.println(list00.get(0));
			PrintWriter pw = resp.getWriter();
			
			
			String htmlTags = "";

			for (int i = 0 ; i < list00.size(); i++) {
				SightsDto dto = list00.get(i);
				 System.out.println("tourismAjaxTblTriblist:"+dto.toString());
				htmlTags += "<table id='triplist' border='2' >";
				htmlTags += "<col width='50'><col width='250'>";
				htmlTags += "<tr><th><input type='checkbox' name='addck[]' value="+dto.getTitle()+"></th>";
				htmlTags += "<th ><font size='2'id='coment'>"+dto.getTitle()+"</font></th></tr>";
				
			
			}
			 htmlTags +="<tr><th colspan='2'>";
			for(int i=1;i<=page.getPagignum();i++) {
				 htmlTags+="<button type='button' class='btn btn-light' onclick='pagingBtnClick("+i+")'>"+i+"</button>";
			}
			htmlTags  +="</tr></th>";
			
			htmlTags +="<tr><th colspan='2'>"
					+"<button type='button' class='btn btn-outline-secondary' onclick='add()' id= 'addbtn' >추가</button>"
					+ "</th></tr>";
			
			htmlTags  +="</table>";
			
			   
		//	System.out.println("htmlTags"+htmlTags);
			//System.out.println("페이지"+page.getPagignum());
			
			//System.out.println(htmlTags);
			pw.print(htmlTags);
			pw.flush();
			
		}else if(command.equals("addtrip")) {
			resp.setContentType("text/html; charset=utf-8");
			
			
			String title=req.getParameter("title"); //여행제목
			String sdate=req.getParameter("firstday"); //여행시작날
			String edate=req.getParameter("lastday"); //여행마지막날
			int member = Integer.parseInt(req.getParameter("memberselec")+"");//여행멤버
			
			String companion=req.getParameter("withselec"); //여행일행
			String section=req.getParameter("sectionselec");//여행구분 
		    int open=Integer.parseInt(req.getParameter("open")+"");//공개여부
			int totaldays=Integer.parseInt(req.getParameter("tripdate")+"");
			
			System.out.println("여행제목"+title);
			System.out.println("여행시작"+sdate);
			System.out.println("여행마지막날"+edate);
			System.out.println("여행멤버"+member);
			System.out.println("여행일행"+companion);
			System.out.println("여행구분"+section);
			System.out.println("공개여부"+open);
			System.out.println("총날짜 수 "+totaldays);
			
			String currId = "guest";
			if( req.getSession().getAttribute("currUser") != null ) {
				currId = ( (MemberDto)req.getSession().getAttribute("currUser") ).getId();
			}
			
			int currSeq = 0;
			ScheduleDto dto=new ScheduleDto(currId,title,sdate,edate,totaldays,member,companion,section,open);
			currSeq = service.getaddTrip(dto);
			
			
			System.out.println("current seq : " + currSeq);
			PrintWriter pw = resp.getWriter();
			pw.print(currSeq);
			pw.flush();
			System.out.println("addtripajax완성");
			
		}else if(command.equals("addtimelinebox")) {
			int parentseq=Integer.parseInt(req.getParameter("returnofcontroller"));
			System.out.println("addtimelinebox");
			System.out.println("부모시퀀스"+parentseq);
						
			//타임라인일정
			String totaldata=req.getParameter("totaldata");
			System.out.println("totaldata "+totaldata );
			
			//가져온 title를 가져와서 스케쥴를 카운트 해준다.
			String timetitle = req.getParameter("timetitle");
			String arr[] = timetitle.split("_");
			for(String addtitle : arr) {
				service.addSchedule(addtitle);
			}
			
			//타임라인 일정을 _로 자르기
			String data[]=totaldata.split("_");
			
			//일정을 담을 리스트
			List<ScheduledetailDto> list=new ArrayList<ScheduledetailDto>();
		   
			
			for(int i=0;i<data.length;i++) {
			//	System.out.println("data["+i+"]"+data[i]);
				String schedule[]=data[i].split(":");
				 ScheduledetailDto dto=new ScheduledetailDto(schedule[0],schedule[1],schedule[2],parentseq);
				list.add(dto);				
			}
			
			/*
			 * for(int i=0;i<list.size();i++) { System.out.println(list.get(i)); }
			 */
			
	
			boolean b=service.getadddetailSchedule(list);
			
			PrintWriter pw = resp.getWriter();
			
			if(b) {
				pw.println(true);
				
			}else {
				pw.println(false);	
			}
			
			pw.flush();
			pw.close();
			
		}
		
		else if(command.equals("addlist")) {
			//여행일정 리스트 확인
			String currId = "guest";
			if( req.getSession().getAttribute("currUser") != null ) {
				currId = ( (MemberDto)req.getSession().getAttribute("currUser") ).getId();
			}
			
			List<ScheduleDto> list=service.getmySchedulelist(currId);
			System.out.println("!!!!!!!!!!!!!!list size : " + list.size() + " currID" + currId);
			String page_=req.getParameter("pajing");
			if(page_==null) {
				page_=1+"";
			}
			System.out.println("page"+page_);
			
			req.setAttribute("page", page_);			
			req.setAttribute("list", list);
			
			req.getRequestDispatcher("/views/schedule/scheduleaddlist.jsp?page="+page_).forward(req, resp);
		}else if(command.equals("rank")) {
			resp.setContentType("text/html; charset=utf-8");
			System.out.println("rank controller접근 완료");
			 String category=req.getParameter("category");
			 System.out.println("카테고리"+category);
			
			 List<SightsDto> list=service.getLanking(Integer.parseInt(category));

			 String jsonlike = "관광지명___인기순____";
			 for( SightsDto dto : list ) {
				 jsonlike += dto.getTitle() + "___" + dto.getAddSchedule() + "____";
			 }
			     
			     
			     System.out.println("json"+jsonlike);
		

				 PrintWriter pw=resp.getWriter();
			 pw.print(jsonlike);
			 req.setAttribute("data",jsonlike);
			 pw.flush();
			 pw.close();
			
			 
			 
		}else if(command.equals("detail")) {
			int seq=Integer.parseInt(req.getParameter("seq"));
			System.out.println("시퀀스"+seq);
			ScheduleDto dto=service.getDetail(seq);  //여행 일정 시간 동행자 등을 구하는 함수 호출
			List<ScheduledetailDto> list=service.getsheduleDetail(seq);//해당 목적지 시간 을 구하는 함수 호출

			for(int i=0;i<list.size();i++) {
				System.out.println("list"+i+":"+list.get(i));
			}
			
			
			List<SightsDto> list0 = service.getSightslist(0);// 관광지
			List<SightsDto> list1 = service.getSightslist(1);// 음식점
			List<SightsDto> list2 = service.getSightslist(2);// 숙소

			req.setAttribute("list0", list0);// 관광지 리스트 보내기
			req.setAttribute("list1", list1);// 음식점 리스트 보내기
			req.setAttribute("list2", list2);// 숙소 리스트 보내기
            req.setAttribute("seq",seq);
			
			int count = service.pagenum(list0.size());
			req.setAttribute("count", count);
			
			req.setAttribute("dto", dto);
			req.setAttribute("list", list);
			req.getRequestDispatcher("/views/schedule/scheduledetail.jsp").forward(req, resp);
		}else if(command.equals("update")) {
			
			String title = req.getParameter("title");
		//	System.out.println("title : " + title);
        
			/*
			 * System.out.println("넘어온 파라미터를 보여줄게"); Enumeration<String> nm =
			 * req.getParameterNames(); while(nm.hasMoreElements()) {
			 * System.out.println(nm.nextElement().toString()); }
			 */
			
			resp.setContentType("text/html; charset=utf-8");
			
			String seq=req.getParameter("seq");
			
			
			String sdate=req.getParameter("firstday"); //여행시작날 String
			String edate=req.getParameter("lastday"); //여행마지막날 int member =
			int member=Integer.parseInt(req.getParameter("memberselec")+"");//여행멤버
			 String companion=req.getParameter("withselec"); //여행일행 String
			 String section=req.getParameter("sectionselec");//여행구분 int
			 int open=Integer.parseInt(req.getParameter("open")+"");//공개여부 int
			 int totaldays=Integer.parseInt(req.getParameter("tripdate")+"");
			 
			System.out.println("seq"+seq);
			
			  System.out.println("여행제목"+title); System.out.println("여행시작"+sdate);
			 System.out.println("여행마지막날"+edate); System.out.println("여행멤버"+member);
			 System.out.println("여행일행"+companion); System.out.println("여행구분"+section);
			 System.out.println("공개여부"+open); System.out.println("총날짜 수 "+totaldays);
			 PrintWriter pw = resp.getWriter();
			
			//user 수정 필요
			String currId = "guest";
			if( req.getSession().getAttribute("currUser") != null ) {
				currId = ( (MemberDto)req.getSession().getAttribute("currUser") ).getId();
			}
			 
			ScheduleDto dto=new ScheduleDto(currId,title,sdate,edate,totaldays,member,companion,section,open);
			boolean b=service.update(dto, Integer.parseInt(seq));
			
			if(b) {	
				pw.println("수정완료");
			}else {
				pw.println("수정실패");
			}
			pw.flush();
			pw.close();
		}
		
		
		
		else if(command.equals("updatedetail")) {
			
			int seq=Integer.parseInt(req.getParameter("seq"));
			System.out.println("updatedetailcontroll");
			System.out.println("시퀀스"+seq);			
			
			String totaldata=req.getParameter("totaldata");
			String data[]=totaldata.split("_");
			//타임라인일정
			
		 
			service.delDetailtrip(seq);
			System.out.println("삭제");
			
			System.out.println("totaldata "+totaldata );
			List<ScheduledetailDto> list=new ArrayList<ScheduledetailDto>();
			for(int i=0;i<data.length;i++) {
					System.out.println("data["+i+"]"+data[i]);
					String schedule[]=data[i].split(":");
					 ScheduledetailDto dto=new ScheduledetailDto(schedule[0],schedule[1],schedule[2],seq);
					list.add(dto);				
				}
				
			
				  for(int i=0;i<list.size();i++) { System.out.println(list.get(i)); }
				 
				
		
				boolean b=service.getadddetailSchedule(list);
				
				PrintWriter pw = resp.getWriter();
				
				if(b) {
					pw.println(true);
					
				}else {
					pw.println(false);	
				}
		
		}else if(command.equals("del")) {
			System.out.println("삭제");
			String seq=req.getParameter("seq");
			System.out.println("seq:"+seq);
			int sseq=Integer.parseInt(seq);
			
			boolean b =service.deltrip(sseq);// 해당제목 일 수 등을 삭제
			boolean bb=service.delDetailtrip(sseq); //그 일정에 대한 세부 일정 또한 삭제
			List<ScheduleDto> list=service.getmySchedulelist("hwwk78");
			
			req.setAttribute("list", list);
			
			
			if(b) {
			//	System.out.println("정상적으로 삭제 되었습니다");
				
			}else {
			//	System.out.println("정상적으로 삭제 되지않았습니다");
			
			}
			
			 if(bb) {
			//	 System.out.println("정상적으로 삭제 되었습니다");
					//req.getRequestDispatcher("/views/schedule/scheduleaddlist.jsp").forward(req, resp);
				 //req.getRequestDispatcher("/jejuya/ScheduleController?command=addlist&pajing=1").forward(req, resp);
				 resp.sendRedirect("/jejuya/ScheduleController?command=addlist&pajing=1");
				 return;
			 }else {
			//	 System.out.println("정상적으로 삭제 되지않았습니다");
				 //req.getRequestDispatcher("/views/schedule/scheduleaddlist.jsp").forward(req, resp);
				 //req.getRequestDispatcher("/jejuya/ScheduleController?command=addlist&pajing=1").forward(req, resp);
				 resp.sendRedirect("/jejuya/ScheduleController?command=addlist&pajing=1");
				 return;
			 }
			 
			 //req.getRequestDispatcher("/jejuya/ScheduleController?command=addlist&pajing=1").forward(req, resp);
			 
			
		}else if(command.equals("search")) {
			System.out.println("search");
			String title=req.getParameter("title");
			System.out.println("title"+title);
			
			
            List<ScheduleDto> list=service.getsearchSchedulelist(title);
			
			req.setAttribute("list", list);
			req.getRequestDispatcher("/views/schedule/scheduleaddlist.jsp").forward(req, resp);
		}
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("[ScheduleController] do post");
	}

	
	
}
