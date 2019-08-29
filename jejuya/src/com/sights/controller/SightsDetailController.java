package com.sights.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.sights.dto.Paging;
import com.sights.dto.SightReviewDto;
import com.sights.dto.SightsDto;
import com.sights.service.SightsDetailService;
import com.sights.service.impl.SightsDetailServiceImpl;

@WebServlet("/SightsController")
public class SightsDetailController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	SightsDetailService service = SightsDetailServiceImpl.getInstance();

	// 업로드를 위한 함수
	public String processUploadFile(FileItem fileItem, String dir) throws IOException {

		String filename = fileItem.getName(); // 경로 + 파일명이 넘어옴
		long sizeInBytes = fileItem.getSize(); // 파일의 크기

		if (sizeInBytes > 0) { // 정상적인 파일 d:\\tmp\\abc.txt d:/tmp/abc.txt

			int idx = filename.lastIndexOf("\\"); // 뒤에서 \\의 위치를 얻어온다
			if (idx == -1) { // -1은 못찾았을때
				idx = filename.lastIndexOf("/");
			}

			filename = filename.substring(idx + 1); // abc.txt
			File uploadFile = new File(dir, filename); // 파일 새로 생성

			try {
				fileItem.write(uploadFile); // 실제 업로드 부분
			} catch (Exception e) {

			}
		}
		return filename; // 확인용
	}


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("SightsController doGet");
		String command = req.getParameter("command");
		System.out.println(command);
		
		if (command.equals("detailBasic")) {
			//필요한 파라미터 : title			
			System.out.println("detailBasic");
			
			String title = req.getParameter("title");
			System.out.println("sightDetail title: "+ title);
			
			SightsDto dto = service.getOneSightDetail(title);
			// System.out.println(dto.toString());
			
			//조회 수 +1
			service.readCount(title);
			
			// 리뷰평점평균
			int count = service.avgReviewScore(title);
			
			req.setAttribute("dto", dto);
			req.setAttribute("count", count);
												
			req.getRequestDispatcher("/views/sights/detailBasic.jsp").forward(req, resp);
			//resp.sendRedirect( "/views/sights/detailBasic.jsp"); 
			return;
		}
		
		else if(command.equals("conBasic")) {
			System.out.println("conBasic");
			
			String title = req.getParameter("title");
			System.out.println("sightDetail title: "+ title);
			
			SightsDto dto = service.getOneSightDetail(title);
			
			req.setAttribute("dto", dto);
			req.getRequestDispatcher("/views/sights/detailContent.jsp").forward(req, resp);
			return;
		}
		
		else if(command.equals("reviewList")) {
			System.out.println("reviewList");
			
			// title, page
			String title = req.getParameter("title");
			System.out.println("reviewList title: "+ title);
			
			// 평가순 정렬변수			
			String scoreSorting = req.getParameter("sorting");
			if(scoreSorting == null) {
				scoreSorting = "date";					
			}
			else if(scoreSorting.equals("score")) {
				scoreSorting = req.getParameter("sorting");		
			}
			
//			System.out.println("reviewListContr scoreSorting: " + scoreSorting);
			
			int nowpage = 1;
			
			if(req.getParameter("page") != null){
				nowpage = Integer.parseInt(req.getParameter("page"));				
			}

			Paging paging = new Paging();
			paging.setNowpage(nowpage);
			
			int count = service.reviewAllCount(title);
			System.out.println("reviewAllCount count:" + count);			
			paging.setTotalCount(count);
			
			List<SightReviewDto> list = service.pagingReviewList(title, paging, scoreSorting);
			System.out.println("[SightsController] list size : " + list.size());
			
			SightsDto dto = service.getOneSightDetail(title);
			
			req.setAttribute("dto", dto);
			req.setAttribute("list", list);
			req.setAttribute("count", count);
			req.setAttribute("sorting", scoreSorting);
			req.setAttribute("paging", paging);
						
			req.getRequestDispatcher("/views/sights/detailReview.jsp").forward(req, resp);						
		}
			
		else if(command.equals("delReview")) {
			System.out.println("delReview");
			
			String title = req.getAttribute("title") + "";	
			
			int seq = Integer.parseInt(req.getParameter("seq"));
			System.out.println("seq=" + seq);
			
			boolean b = service.reviewDel(seq);	
									
			req.getRequestDispatcher("SightsController?command=reviewList").forward(req, resp);
//			resp.sendRedirect("SightsController?command=reviewList");	
		}
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("SightsController doPost");
		String command = req.getParameter("command");
		System.out.println(command);
		
		if (command.equals("addsights")) {
//			System.out.println("addsights");			

			String fupload = req.getServletContext().getRealPath("/upload");			
			String yourTempDir = fupload;

			int yourMaxRequestSize = 100 * 1024 * 1024; // 전송할때 사이즈 1MByte
			int yourMaxMemorySize = 100 * 1024; // 순간 메모리사용 사이즈 1KByte

			// form field의 데이터를 저장할 변수(multipart)
			String title = "";
			int category = 0;
			String theme = "";

			String roadAddress = "";

			String phone = "";
			String homepage = "";
			String content = "";

			// file name
			String filename = ""; // DB에 넣어줄용

			boolean isMultipart = ServletFileUpload.isMultipartContent(req);

			if (isMultipart) {
				// FileItem을 생성
				DiskFileItemFactory factory = new DiskFileItemFactory();

				factory.setSizeThreshold(yourMaxMemorySize);
				factory.setRepository(new File(yourTempDir));

				ServletFileUpload upload = new ServletFileUpload(factory);
				upload.setSizeMax(yourMaxRequestSize);

				// list저장
				List<FileItem> items;
				try {
					items = upload.parseRequest(req);
					Iterator<FileItem> it = items.iterator();

					while (it.hasNext()) {
						FileItem item = it.next();

						if (item.isFormField()) {
							if (item.getFieldName().equals("title")) {
								title = item.getString("utf-8");
							} else if (item.getFieldName().equals("category")) {
								category = Integer.parseInt(item.getString("utf-8"));
							} else if (item.getFieldName().equals("theme")) {
								theme = item.getString("utf-8");
							} else if (item.getFieldName().equals("roadAddress")) {
								roadAddress = item.getString("utf-8");
							} else if (item.getFieldName().equals("phone")) {
								phone = item.getString("utf-8");
							} else if (item.getFieldName().equals("homepage")) {
								homepage = item.getString("utf-8");
							} else if (item.getFieldName().equals("content")) {
								content = item.getString("utf-8");
							}

//							System.out.println("title:" + title);
//							System.out.println("category:" + category);
//							System.out.println("theme= " + theme);
//							System.out.println(roadAddress + detailAddress);
//							System.out.println(phone);
//							System.out.println(homepage);
//							System.out.println(content);

						} else { // fileload
							if (item.getFieldName().equals("fileload")) {
								filename = processUploadFile(item, fupload);
								System.out.println("fupload : " + fupload);
							} 
						}
					}

				} catch (FileUploadException e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("multipart 아님");
			}
			System.out.println("category:" + category);
			System.out.println("theme= " + theme);
			// DB
			System.out.println(filename);
//			String address = roadAddress + detailAddress;

			boolean b = service.addSight(
					new SightsDto(title, 0, category, theme, filename, roadAddress, phone, homepage, content, 0, 0, 0, 0));
//			req.setAttribute("b", b);
			
			// 관리자 페이지로 이동해야됨!!!!
			req.getRequestDispatcher("/views/sights/sightAdd.jsp").forward(req, resp);
		}		
		
		else if (command.equals("addReview")) {
			System.out.println("dopost addReview");

			String fupload = req.getServletContext().getRealPath("/upload/review");
			String yourTempDir = fupload;

			int yourMaxRequestSize = 100 * 1024 * 1024; // 전송할때 사이즈 1MByte
			int yourMaxMemorySize = 100 * 1024; // 순간 메모리사용 사이즈 1KByte

			// form field의 데이터를 저장할 변수(multipart)
			String title = "";
			String id = "";
			String content = "";
			int score = 0;

			// 여러장의 filename을 리스트로 저장
			List<String> filename = new ArrayList<String>();
						
			boolean isMultipart = ServletFileUpload.isMultipartContent(req);

			if (isMultipart) {
				// FileItem을 생성
				DiskFileItemFactory factory = new DiskFileItemFactory();

				factory.setSizeThreshold(yourMaxMemorySize);
				factory.setRepository(new File(yourTempDir));

				ServletFileUpload upload = new ServletFileUpload(factory);
				upload.setSizeMax(yourMaxRequestSize);

				// list저장
				List<FileItem> items;
				try {
					items = upload.parseRequest(req);
					Iterator<FileItem> it = items.iterator();

					while (it.hasNext()) {
						FileItem item = it.next();

						if (item.isFormField()) {
							if (item.getFieldName().equals("title")) {
								title = item.getString("utf-8");
							} else if (item.getFieldName().equals("id")) {
								id = item.getString("utf-8");
							} else if (item.getFieldName().equals("content")) {
								content = item.getString("utf-8");
							} else if (item.getFieldName().equals("score")) {
								score = Integer.parseInt(item.getString("utf-8"));
							}

//							System.out.println(title);
							System.out.println(id);
//							System.out.println(content);
//							System.out.println(score);

						} else { // fileload
							if (item.getFieldName().equals("fileload1")) {	// fileload2, fileload3...
								filename.add(processUploadFile(item, fupload));
								//					filename = processUploadFile(item, fupload);
								System.out.println("fupload : " + fupload);
							} 
							else if (item.getFieldName().equals("fileload2")) {	// fileload2, fileload3...
								filename.add(processUploadFile(item, fupload));
	
							} 
							else if (item.getFieldName().equals("fileload3")) {	// fileload2, fileload3...
								filename.add(processUploadFile(item, fupload));		
							} 
						}
					}					
				} catch (FileUploadException e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("multipart 아님");
			}

			// DB
			System.out.println("filename=" + filename.toString());
			
			String finalFileName = "";
			for (int i = 0; i < filename.size(); i++) {
				if(filename.get(i).toString().equals("")||filename.get(i) == null) {
					System.out.println("filename null");
					finalFileName += "";
				}
				else {
					finalFileName += filename.get(i).toString()+"/";
				}
			}
			
			boolean b = service.addReview(new SightReviewDto(0, title, id, content, finalFileName, null, score, 0));
			
			System.out.println("finalFileName=" + finalFileName);		
			System.out.println("title=" + title);
			
			req.setAttribute("b", b);
//			resp.sendRedirect("./views/sights/detailReview.jsp");
			
			//req.getRequestDispatcher("SightsController?command=sightDetail&title=우도&page=1").forward(req, resp);
			//req.getRequestDispatcher("/views/sights/sightDetail.jsp").forward(req, resp);
			req.setCharacterEncoding("UTF-8");
			resp.setCharacterEncoding("UTF-8");
			resp.setContentType("text/html;charset=UTF-8");
			
			// doget으로 가야되는데  dopost로 가서 그냥 읽어서 보내줌
			PrintWriter pw = resp.getWriter();
			pw.print("<script type=\"text/javascript\">location.href='/jejuya/SightsController?command=reviewList&title="
					+ title + "&page=1';</script>");
		}
		
		
	}
		

	
	
	
}
	
	

















