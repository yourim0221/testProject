package com.member.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.member.dto.MemberDto;
import com.member.service.MemberService;
import com.member.service.impl.MemberServiceImpl;

import common.util.JejuyaMailling;

@WebServlet("/member")
public class MemberController extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		System.out.println("[MemberController] do get");
		
		if( req.getParameter("command") == null ) {
			//if command is null, go to login index page
			//resp.sendRedirect("/jejuya/views/member/loginindex.jsp");
			req.getRequestDispatcher("/views/member/loginindex.jsp").forward(req, resp);
			return;
		}else {
			
			MemberService memberService = MemberServiceImpl.getInstance();
			
			String command = req.getParameter("command");
			
			if( command.equals("goregi") ) {
				//command가 goregi인 경우 회원가입 페이지로 이동
				//resp.sendRedirect("/jejuya/views/member/regi.jsp");
				req.getRequestDispatcher("/views/member/regi.jsp").forward(req, resp);
				return;
			}else if( command.equals("gofind") == true ) {
				System.out.println("[MemberController] do find");
				//ID/PW 찾기 페이지로 이동
				//resp.sendRedirect("/jejuya/views/member/findMember.jsp");
				req.getRequestDispatcher("/views/member/findMember3.jsp").forward(req, resp);
				return;
			}else if( command.equals("getOneId") == true ) {
				//Ajax통신으로 ID중복여부 검사를 위한 메소드, Ajax에 해당 ID가 DB에 있는지 여부 리턴
				System.out.println( "Ajax con : " + req.getParameter("inputId") );
				
				String inputId = req.getParameter("inputId");
				boolean isNotDupId = memberService.isExistingId(inputId);
				System.out.println( "Ajax ID사용가능? : " + isNotDupId );
				
				//Ajax에 중복 여부 리턴
				PrintWriter pw = resp.getWriter();
				pw.print(isNotDupId);
			}else if( command.equals("dologout") == true ) {
				//로그아웃 기능
				if( req.getSession().getAttribute("currUser") != null){
					req.getSession().removeAttribute("currUser");
				}

				//0821추가, 로그아웃 시 이전 작업했던 경로로
				String uri = "/main";
				if( req.getSession().getAttribute("latestURI") != null ) {
					uri = "http://192.168.0.6:8090" + req.getSession().getAttribute("latestURI") + "";
					//System.out.println("[MemberController]latestURI : " + uri);
				}
				
				//String prevUrl = req.getParameter("currUrl");
				//req.getRequestDispatcher(prevUrl).forward(req, resp);
				//req.getRequestDispatcher("/main").forward(req, resp);
				
				//0821추가, 로그아웃 시 이전 작업했던 경로로
				//req.getRequestDispatcher(uri).forward(req, resp);
				resp.sendRedirect(uri);
				return;
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("[MemberController] do post");
		
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		
		MemberService memberService = MemberServiceImpl.getInstance();		
		
		if( req.getParameter("command") == null ) {
			//command커맨드가 널인 경우 로그인인덱스 페이지로 이동	
			resp.sendRedirect("/jejuya/views/member/loginindex.jsp");
			return;
		}else {			
			
			String command = req.getParameter("command");
			
			MemberService service = MemberServiceImpl.getInstance();			
			
			if( command.equals("dologin") ) {
				//command가 dologin인 경우 계정검사 수행
				String id = req.getParameter("id");
				String pw = "";
				
				MemberDto dto = service.getOneMember(id);
				
				if( dto == null ) {
					//id를 DB에서 찾지 못한 경우 로그인인덱스로 돌아감.
					//req.getRequestDispatcher("/views/member/loginindex.jsp").forward(req, resp);
					System.out.println("wrong id");					
					
					resp.sendRedirect("/jejuya/views/member/loginindex.jsp");
					return;
				}else {					
					//view에서 입력받은 pwd값 저장
					pw = req.getParameter("pwd");
					
					//입력받은 pwd값을 hashing algorithm을 통해 변조
					MessageDigest md;
					try {
						md = MessageDigest.getInstance("SHA-256");
						md.update(pw.getBytes());
						pw = byteToHexString(md.digest());
						System.out.println("input PW : " + pw);
					} catch (NoSuchAlgorithmException e) {
						e.printStackTrace();
					}				   
					
					//변조한 입력값과 DB에 저장된 해시문자열을 비교
					if( pw.equals(dto.getPw()) ) {		
						//일치하는 경우 로그인 성공
						req.getSession().setAttribute("user", dto);
						req.getSession().setAttribute("currUser", dto);						
						
						//admin인 경우
						if( dto.getIsadmin() == 3 ){
							resp.sendRedirect("/jejuya/adminControl?command=main");
							return;
						}
						
						resp.sendRedirect("/jejuya/main");
						return;
					}else if( dto.getIsadmin() == 3 ){
						//admin인 경우
						if( req.getParameter("pwd").equals(dto.getPw()) ) {
							req.getSession().setAttribute("user", dto);
							req.getSession().setAttribute("currUser", dto);	
							resp.sendRedirect("/jejuya/adminControl?command=main");
						}						
					}else {
						//일치하지 않으면 실패
						System.out.println("wrong pw");
						resp.sendRedirect("/jejuya/views/member/loginindex.jsp");
						return;
					}
				}
			}else if( command.equals("dologinAjax") ) {
				//Ajax 로그인-로그아웃 관리
				
				
				
			}else if( command.equals("confirmCurrIdAjax") ) {
				//Ajax 로그인-로그아웃 관리
				String currId = "guest";
				if(req.getSession().getAttribute("currUser") != null ) {
					currId = ( (MemberDto)req.getSession().getAttribute("currUser") ).getId();
				}
				
				resp.getWriter().print(currId);
				resp.getWriter().flush();
				resp.getWriter().close();				
				
			}else if( command.equals("doregi") ) {
				//command가 doregi인 경우 회원가입 수행
				String id = req.getParameter("id");
			    String pw = req.getParameter("pwd");
			    String name = req.getParameter("name");
			    String email = req.getParameter("email");
			    String birth = req.getParameter("birth");
			    
			    MemberDto dto = new MemberDto(id, pw, name, email, birth);
			    
			    boolean isDone = service.addMember(dto);
			    if( isDone == true ) {
			    	//addMember 메소드를 통해 회원가입을 성공한 경우 세션에 계정정보를 저장하고 메인페이지로 이동
			    	req.getSession().setAttribute("user", dto);
			    	req.getSession().setAttribute("currUser", dto);
			    	System.out.println("[MemberController] do regi : " + dto.toString());
					resp.sendRedirect("/jejuya/main");
					return;
			    }else {
			    	//addMember 메소드를 통해 회원가입을 실패한 경우 회원가입 창으로 이동
			    	resp.sendRedirect("/jejuya/views/member/regi.jsp");
					return;
			    }
			}else if( command.equals("doconfirmid") ) {
				//findMember.jsp 뷰에서 이름과 이메일을 입력했을 때, 해당하는 정보가 DB에 있는지 여부를 리턴
				
				String inputName = req.getParameter("inputName");
				String inputEmail = req.getParameter("inputEmail");
				
				//service를 통해 이름으로 멤버 검색
				MemberDto dto = memberService.getOneMemberByName(inputName);
				
				//DB검색 결과값을 Ajax 호출한 위치로 리턴할 문자열
				String returnStr = "false";
				
				if( dto != null ) {
					//이름으로 계정정보를 찾은 경우
					if( inputEmail.equals( dto.getEmail() ) ) {
						//유저가 입력한 이메일과 DB에서 찾은 계정의 이메일 정보가 일치하는 경우 true 리턴
						returnStr = "true";
					}					
				}
				
				PrintWriter pw = resp.getWriter();
				pw.print(returnStr);
				pw.flush();
				
			}else if( command.equals("dofindid") ) {
				//findMember.jsp 뷰에서 doconfirmid를 거쳐서 올바른 이름과 이메일을 입력했을 때, 해당하는 이메일에 ID정보를 발송
				
				String userEmail = req.getParameter("findIdInputEmailTxt");
				String inputName = req.getParameter("findIdInputNameTxt");
				
				//service를 통해 이름으로 멤버 검색
				MemberDto dto = memberService.getOneMemberByName(inputName);
				
				//System.out.println("user email : " + userEmail);
				//ID를 포함한 HTML내용을 유저가 입력한 email에 보냄
				JejuyaMailling.sendMail( JejuyaMailling.getFindIdSampleHtmlContent( dto.getId() ), userEmail, 0 );
				
				PrintWriter pw = resp.getWriter();
				pw.print("<script>alert('입력하신 이메일로 아이디 발송이 완료되었습니다.'); history.back();</script>");
				pw.flush();
			}else if( command.equals("doconfirmpw") ) {
				//findMember.jsp 뷰에서 ID와 이메일을 입력했을 때, 해당하는 정보가 DB에 있는지 여부를 리턴
				
				String inputId = req.getParameter("inputId");
				String inputEmail = req.getParameter("inputEmail");
				
				//service를 통해 ID로 멤버 검색
				MemberDto dto = memberService.getOneMember( inputId );
				
				//DB검색 결과값을 Ajax 호출한 위치로 리턴할 문자열
				String returnStr = "false";
				
				if( dto != null ) {
					//이름으로 계정정보를 찾은 경우
					if( inputEmail.equals( dto.getEmail() ) ) {
						//유저가 입력한 이메일과 DB에서 찾은 계정의 이메일 정보가 일치하는 경우 true 리턴
						returnStr = "true";
					}					
				}
				
				PrintWriter pw = resp.getWriter();
				pw.print(returnStr);
				pw.flush();
				
			}else if( command.equals("dofindpw") ) {
				//findMember.jsp 뷰에서 doconfirmpw를 거쳐서 올바른 이름과 이메일을 입력했을 때, 해당하는 이메일에 임시PW정보를 발송
								
				String userId = req.getParameter("findPwInputIdTxt");
				String userEmail = req.getParameter("findPwInputEmailTxt");
				
				
				//service를 통해 ID로 멤버 검색
				MemberDto dto = memberService.getOneMember( userId );
				
				//임시비밀번호 10자리 생성(랜덤값)
				String tempPw = randomValue(10);
				String tempPwForUser = tempPw;
				
				//System.out.println("tempPW : " + tempPw);
				
				//임시 비밀번호 해시알고리즘을 거쳐서 암호화
				MessageDigest md;
				try {
					md = MessageDigest.getInstance("SHA-256");
					md.update(tempPw.getBytes());
					tempPw = byteToHexString(md.digest());
					System.out.println("input PW : " + tempPw);
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}		
								
				//DB에 해당 회원정보 비밀번호를 암호화된 임시비밀번호로 수정 
				memberService.updatePw(userId, tempPw);
				
				//System.out.println("update Done");
				
				//System.out.println("user email : " + userEmail);
				//임시PW를 포함한 HTML내용을 유저가 입력한 email에 보냄
				JejuyaMailling.sendMail( JejuyaMailling.getFindPwSampleHtmlContent(tempPwForUser), userEmail , 1);				
				PrintWriter pw = resp.getWriter();
				pw.print("<script>alert('입력하신 이메일로 임시 비밀번호 발송이 완료되었습니다.'); history.back();</script>");
				pw.flush();
			}else if( command.equals("dologout") == true ) {
				//로그아웃 기능
				if( req.getSession().getAttribute("currUser") != null){
					req.getSession().removeAttribute("currUser");
				}

				//0821추가, 로그아웃 시 이전 작업했던 경로로
				String uri = "/main";
				if( req.getSession().getAttribute("latestURI") != null ) {
					uri = "http://192.168.0.6:8090" + req.getSession().getAttribute("latestURI") + "";
					//System.out.println("[MemberController]latestURI : " + uri);
				}
				
				//String prevUrl = req.getParameter("currUrl");
				//req.getRequestDispatcher(prevUrl).forward(req, resp);
				//req.getRequestDispatcher("/main").forward(req, resp);
				
				//0821추가, 로그아웃 시 이전 작업했던 경로로
				//req.getRequestDispatcher(uri).forward(req, resp);
				resp.sendRedirect(uri);
				return;
			}
		}
	}
	
	/**
	 * 바이트 배열을 HEX 문자열로 변환한다.
	 * @param data
	 * @return
	 */
	public static String byteToHexString(byte[] data) {
		StringBuilder sb = new StringBuilder();
		for(byte b : data) {
			sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
		}
		return sb.toString();
	}
	
	
	/**매개변수로 받은 정수길이 만큼 임시 비밀번호를 생성해서 리턴하는 메소드
	 * 소문자+숫자 혼합 
	 * @param cnt
	 * @return
	 */
	public static String randomValue(int cnt) {

		StringBuffer strPwd = new StringBuffer();

		char str[] = new char[1];

		// 소문자, 숫자형

		Random rnd = new Random();
		for (int i = 0; i < cnt; i++) {
			if (rnd.nextBoolean()) {
				strPwd.append((char) ((int) (rnd.nextInt(26)) + 97));
			} else {
				strPwd.append((rnd.nextInt(10)));
			}
		}

		return strPwd.toString();
	}

}
