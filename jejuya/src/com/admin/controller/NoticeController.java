package com.admin.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.admin.dto.NoticeDto;
import com.admin.dto.NoticePagingDto;
import com.admin.service.NoticeService;
import com.admin.service.impl.NoticeServiceImpl;
import com.member.dto.MemberDto;

@WebServlet("/notice")
public class NoticeController extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("[NoticeController] do get");
		
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		
		if( req.getParameter("command") == null ) {
			req.getRequestDispatcher("/jejuya/main").forward(req, resp);;
		}else {
			
			//세션에 관리자 권한이 없는 경우 메인페이지로 이동
			isAdmin(req, resp);
			
			String command = req.getParameter("command");
			
			NoticeService noticeService = NoticeServiceImpl.getInstance();
			
			if( command.equals("showlist") ) {
				System.out.println("[NoticeController] do get showlist");
				//모든 공지사항 리스트를 볼 수 있는 뷰로 이동
				int currPageNum = nvlParam(req, "pageNum", 1);
				System.out.println("페이지넘버 : " + currPageNum);
				int totalSize = noticeService.getNoticeDBSize();
				NoticePagingDto noticePagingDto = new NoticePagingDto(currPageNum, totalSize);
				
				//세션에 페이징 처리를 거친 게시물 리스트와 페이징 객체를 저장해서 뷰로 이동
				List<NoticeDto> lst = noticeService.getAllNoticeList( noticePagingDto );
				req.getSession().setAttribute("noticeList", lst);
				req.getSession().setAttribute("noticeListPaging", noticePagingDto);
				
				resp.sendRedirect("/jejuya/views/admin/noticeListAdmin.jsp");
				return;
			}else if( command.equals("writeNewNotice") ) {
				//새 공지사항 작성 뷰로 이동
				resp.sendRedirect("/jejuya/views/admin/noticeWriteAdmin.jsp");
				return;
			}else if( command.equals("noticeDetail") ) {
				//공지사항 디테일 뷰로 이동
				int seq = nvlParam(req, "seq", 1);
				
				NoticeDto dto = noticeService.getOneNoticeDetail(seq);
				
				req.getSession().setAttribute("currNoticeDto", dto);
				
				resp.sendRedirect("/jejuya/views/admin/noticeDetailAdmin.jsp");
				return;
			}else if( command.equals("showlistAdmin") ) {
				System.out.println("[NoticeController] do get showlistAdmin");
				//모든 공지사항 리스트를 볼 수 있는 뷰로 이동
				int currPageNum = nvlParam(req, "pageNum", 1);
				System.out.println("페이지넘버 : " + currPageNum);
				int totalSize = noticeService.getNoticeDBSize();
				NoticePagingDto noticePagingDto = new NoticePagingDto(currPageNum, totalSize);
				
				//세션에 페이징 처리를 거친 게시물 리스트와 페이징 객체를 저장해서 뷰로 이동
				List<NoticeDto> lst = noticeService.getAllNoticeList( noticePagingDto );
				req.getSession().setAttribute("noticeList", lst);
				req.getSession().setAttribute("noticeListPaging", noticePagingDto);
				
				resp.sendRedirect("/jejuya/views/admin/noticeListAdmin2.jsp");
				return;
			}
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("[NoticeController] do post");
		
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		
		if( req.getParameter("command") == null ) {
			req.getRequestDispatcher("/jejuya/main").forward(req, resp);
		}else {
			String command = req.getParameter("command");
			
			NoticeService noticeService = NoticeServiceImpl.getInstance();
			
			if( command.equals("insertNewNotice") ) {
				//noticeWriteAdmin.jsp에서 저장 버튼을 클릭했을 때
				
				String inputTitle = nvlParam(req, "inputTitle", null);
				String inputContent = nvlParam(req, "inputContent", null);
								
				if( inputTitle == null ) {
					resp.getWriter().print("<script>alert('제목이 없습니다.'); history.back();</script>");
				}else if( inputContent == null ) {
					resp.getWriter().print("<script>alert('내용이 없습니다.'); history.back();</script>");
				}else if( inputContent.length() > 6000 ){
					resp.getWriter().print("<script>alert('6000바이트까지만 입력 가능합니다.'); history.back();</script>");
				}else {
					//본문이 2000자, 4000자 이상이면 잘라서 DB에 저장
					String cont1 = "";
					String cont2 = "";
					String cont3 = "";
					String author = req.getParameter("author");
					//System.out.println("이거이거 길이가 이거네 : " + inputContent.length());
					cont1 = inputContent;
					if(inputContent.length() > 2000) {
						cont1 = inputContent.substring(0, 1999);
						cont2 = inputContent.substring(2000, inputContent.length() - 1 );						
					}
					if( inputContent.length() > 4000 ) {
						cont3 = inputContent.substring(4000, inputContent.length() - 1 );
					}					
					
					NoticeDto dto = new NoticeDto(inputTitle, author, "", cont1, cont2, cont3);
					boolean isDone = noticeService.insertNewNotice(dto);
					if( isDone ) {
						resp.getWriter().print("<script>alert('저장이 완료되었습니다. '); location.href='/jejuya/notice?command=showlist';</script>");	
					}					
				}
			}else if( command.equals("modNotice") ) {
				//notice 내용 수정
				
				String inputTitle = nvlParam(req, "inputTitle", null);
				String inputContent = nvlParam(req, "inputContent", null);
								
				if( inputTitle == null ) {
					resp.getWriter().print("<script>alert('제목이 없습니다.'); history.back();</script>");
				}else if( inputContent == null ) {
					resp.getWriter().print("<script>alert('내용이 없습니다.'); history.back();</script>");
				}else if( inputContent.length() > 6000 ){
					resp.getWriter().print("<script>alert('6000바이트까지만 입력 가능합니다.'); history.back();</script>");
				}else {
					//본문이 2000자, 4000자 이상이면 잘라서 DB에 저장
					String cont1 = "";
					String cont2 = "";
					String cont3 = "";
					String author = req.getParameter("author");
					
					cont1 = inputContent;
					if(inputContent.length() > 2000) {
						cont1 = inputContent.substring(0, 1999);
						cont2 = inputContent.substring(2000, 3999);
					}
					if( inputContent.length() > 4000 ) {
						cont3 = inputContent.substring(4000, 5999);
					}					
					
					NoticeDto dto = new NoticeDto(inputTitle, author, "", cont1, cont2, cont3);
					
					dto.setOrigin_seq( Integer.parseInt( ((NoticeDto)req.getSession().getAttribute("currNoticeDto")).getSeq() + "" ) );
					
					boolean isDone = noticeService.updateNotice(dto);
					if( isDone ) {
						resp.getWriter().print("<script>alert('수정이 완료되었습니다. '); history.back();</script>");	
					}else {
						resp.getWriter().print("<script>alert('수정작업 중 오류가 발생했습니다. 다시 시도해 주세요. '); history.back();</script>");
					}
				}
			}else if( command.equals("delNotice") ) {
				//notice 내용 삭제
				int seq = ( (NoticeDto)req.getSession().getAttribute("currNoticeDto") ).getSeq();
				
				noticeService.deleteNotice(seq);
								
				resp.sendRedirect(req.getContextPath() + "/notice?command=showlist");
				return;
			}else if( command.equals("showlistajax") ) {
				//메인페이지 공지사항 ajax 페이징
				int selectedIndex = 1;
				if( req.getParameter("selectedIndex") != null ) {
					selectedIndex = Integer.parseInt( req.getParameter("selectedIndex") );
				}
				
				int totalSize = noticeService.getNoticeDBSize();
				NoticePagingDto noticePagingDto = new NoticePagingDto(selectedIndex, totalSize);
				
				//페이징 처리를 거친 게시물 리스트와 페이징 객체를 저장해서 ajax에 리턴
				List<NoticeDto> lst = noticeService.getAllNoticeList( noticePagingDto );
				
				String str = "";
				for(NoticeDto d : lst) {
					System.out.println(d.toString());
					str += "seq:__" + d.getSeq() + "___";
					str += "title:__" + d.getTitle() + "___";
					str += "wdate:__" + d.getWdate() + "____";
				}
				
				resp.getWriter().println(str);
				resp.getWriter().flush();
				resp.getWriter().close();
			}else if( command.equals("noticeDetailAjax") ) {
				//메인화면 모달창 공지사항 디테일 보여주기 위한 부분
				int seq = nvlParam(req, "seq", 1);
				System.out.println("seq : " + seq);
				NoticeDto dto = noticeService.getOneNoticeDetail(seq);
		
				resp.getWriter().println("noticeTitle:" + dto.getTitle());
				resp.getWriter().println("noticeContent:" + dto.getContent1());
				resp.getWriter().flush();
				resp.getWriter().close();
			}else if( command.equals("saveimg") ) {
				
				String url = req.getParameter("dataurl");
				System.out.println("save img!!!!!" + url);
			}else if( command.equals( "showAdminListAjax" )) {
				//관리자페이지에서 리스트를 보여주기 위한 메소드
				int selectedIndex = 1;
				if( req.getParameter("selectedIndex") != null ) {
					selectedIndex = Integer.parseInt( req.getParameter("selectedIndex") );
				}
				
				int totalSize = noticeService.getNoticeDBSize();
				NoticePagingDto noticePagingDto = new NoticePagingDto(selectedIndex, totalSize);
				
				//페이징 처리를 거친 게시물 리스트와 페이징 객체를 저장해서 ajax에 리턴
				List<NoticeDto> lst = noticeService.getAllNoticeList( noticePagingDto );
				
				String str = "";
				for(NoticeDto d : lst) {
					System.out.println(d.toString());
					str += "seq:__" + d.getSeq() + "___";
					str += "title:__" + d.getTitle() + "___";
					str += "author:__" + d.getAuthor() + "___";
					str += "filename:__" + "F" + "___";
					str += "wdate:__" + d.getWdate().toLocaleString().substring(0,12) + "____";
				}
				
				resp.getWriter().println(str);
				resp.getWriter().flush();
				resp.getWriter().close();
			}else if(command.equals("notice_muldel")) {
				//관리자페이지에서 공지 다중 삭제 버튼을 클릭한 경우
				String[] delArr = req.getParameterValues("delck");
				
				boolean isDone = noticeService.deleteMultipleNotice(delArr);
				
				if( isDone == true ) {
					resp.sendRedirect("/jejuya/notice?command=showlistAdmin");
					return;
				}else {
					writeJsCode(resp,"<script>alert('삭제에 실패했습니다. 다시 시도해 주세요.'); history.back();</script>");
				}				
			}
		}
	}
	
	
	/**name파라미터가 있으면 해당 value를 리턴, 없으면 altText리턴
	 * @param req
	 * @param name
	 * @param altText
	 * @return
	 */
	public String nvlParam(HttpServletRequest req, String name, String altText) {
		String str = altText;
		
		if( req.getParameter(name) != null ) {
			str = req.getParameter(name);
		}		
		return str;
	}
	
	/**name파라미터가 있으면 해당 value를 리턴, 없으면 altInt리턴
	 * @param req
	 * @param name
	 * @param altInt
	 * @return
	 */
	public int nvlParam(HttpServletRequest req, String name, int altInt) {
		int num = altInt;
		
		if( req.getParameter(name) != null ) {
			num = Integer.parseInt( req.getParameter(name) );
		}		
		return num;
	}


	/**세션 아이디가 관리자 계정인지 판단
	 * @param req
	 * @param resp
	 * @return
	 * @throws IOException
	 */
	public boolean isAdmin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		if( req.getSession().getAttribute("currUser") != null ) {
			MemberDto dto = (MemberDto)req.getSession().getAttribute("currUser");
			//admin인 경우
			if( dto.getIsadmin() == 3 ){
				return true;
			}else {
				//admin이 아닌 경우
				resp.getWriter().print("<script>alert('관리자 계정으로 로그인해주세요.'); location.href='/jejuya/member';</script>");
				resp.getWriter().flush();
				resp.getWriter().close();
			}
		}else {
			//로그인 페이지로 이동
			resp.getWriter().print("<script>alert('로그인 정보가 없습니다. 관리자 계정으로 로그인해주세요.'); location.href='/jejuya/member';</script>");
			resp.getWriter().flush();
			resp.getWriter().close();
		}
		
		return false;
	}
	
	/**resp의 writer로 jsCode를 쓰고 flush와 close를 수행하는 메소드.
	 * @param resp
	 * @param jsCode
	 */
	public void writeJsCode(HttpServletResponse resp, String jsCode) {
		try {
			resp.getWriter().println(jsCode);
			resp.getWriter().flush();
			resp.getWriter().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}
