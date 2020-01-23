package com.kh.sjproject.notice.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.sjproject.common.ExceptionForward;
import com.kh.sjproject.member.model.vo.Member;
import com.kh.sjproject.notice.model.service.NoticeService;
import com.kh.sjproject.notice.model.vo.Notice;


// 클라이언트의 요청 중 /notice 디렉토리 하위로 요청 시
// 해당 서블릿(컨트롤러)로 집중을 시킴
// /notice/*  
//  request -ㄴ
//  request  -> sevlet(Controller) -> 서비스 -> DAO   <<= 프론트 컨트롤러 패턴!!
// request  -ㄱ


@WebServlet("/notice/*")
public class NoticeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public NoticeController() {
        super();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Front Controller
		// - 클라이언트의 요청을 한 곳으로 집중시켜서
		//	 개발 및 유지보수의 효율성을 증가시키는 패턴
		
		// - 요청에 따른 servlet을 따로 생성하지 않고
		// 오직 하나의 servlet에서만 제어함
		
		String uri = request.getRequestURI();
		//System.out.println("uri : " + uri);
		
		String contextPath =request.getContextPath();
		//System.out.println("contextPath : " + contextPath);
		
		String command = uri.substring((contextPath+"/notice").length());
						// 지휘를 하기 위한 주소값 넣기 자;킨
		
		String path = null;
		RequestDispatcher view = null;
		String msg = null;
				
		NoticeService noticeService = new NoticeService();
		
		// 요청 주소가 "/list" (공지사항 목록조회)인 경우
		if(command.equals("/list")){
			
			try {
				List<Notice>list= noticeService.selectList();
				
				request.setAttribute("list", list);
				path="/WEB-INF/views/notice/noticeList.jsp";
				
			}catch(Exception e) {
				
				path="/WEB-INF/views/notice/errorPage.jsp";
				request.setAttribute("errorMsg","공지사항 목록 조회과정에서 오류가 발생했습니다.");
				e.printStackTrace();
			}finally {
				view = request.getRequestDispatcher(path);
				view.forward(request,response);
			}
			
		}
		
		// 공지사항 상세조회 Contoller
		else if(command.equals("/detail")) {
			int no =Integer.parseInt(request.getParameter("no"));
					//int로 변환  		// 문자열 
			try {
					Notice notice = noticeService.selectNotice(no);
					
					if(notice !=null) { // 상세조회 성공 시
						path = "/WEB-INF/views/notice/noticeDetail.jsp";
						request.setAttribute("notice", notice);
						view= request.getRequestDispatcher(path);
						view.forward(request, response);
						
				}else { //상세조회 실패 시
					request.getSession().setAttribute("msg", "공지사항 상세 조회 실패");
					response.sendRedirect("list");  //공지사항 목록으로~
				}
			}catch(Exception e) {
				ExceptionForward.errorPage(request, response, "공지사항 상세 조회", e);
			}
			
		}
		
		// 공지사항 등록 화면 Controller
		else if(command.equals("/insertForm")) {
			path = "/WEB-INF/views/notice/noticeInsert.jsp";
			view= request.getRequestDispatcher(path);
			view.forward(request, response);
		}
		
		// 공지사항 등록 Controller
		else if(command.equals("/insert")) {
			
			// 글제목, 글 내용, 작성자, 
			
			// 작성자 얻어오기
			HttpSession session = request.getSession();
			Member loginMember = (Member)session.getAttribute("loginMember");
			String noticeWriter = loginMember.getMemberId();
			
			String noticeTitle = request.getParameter("title");
			String noticeContent = request.getParameter("content");
			
			Notice notice = new Notice(noticeTitle,noticeContent,noticeWriter);
			
			try {
				
				// 1) 입력된 notice를 DB에 저장
				int result = noticeService.insertNotice(notice);				
										
				// 2) DB 저장 결과에 따라 트랜잭션 처리
				// 완
				
				
				// 3) DB 저장 성공 시 : "공지사항이 등록되었습니다"
				//     -> 작성한 공지글 상세조회 페이지로 redirect 
				
				if(result >0) {
					msg = "공지사항이 등록되었습니다.";
					path = "detail?no=" + result;
							//상세 조회 주소 noticeList.jsp
				}
				
				
				// 4) DB 저장 실패 시 : "공지사항 등록 실패"
				// 	--> 공지사항 목록 조회로 redirect
				else {
					msg = "공지사항 등록 실패";
					path = "list";
				}
				
				session.setAttribute("msg", msg);
				response.sendRedirect(path);
				
				
			}catch(Exception e) {
				ExceptionForward.errorPage(request, response, "공지사항 등록", e);
			}
		}
		
		
		// 공지사항 수정화면 Controller
		else if(command.equals("/updateForm") ) {
		
			// DB에서 기존에 올렸떤 제목,내용을 불러올 것
				// DB에서 해당 글을 조회하면 화면으로 전달
			
			//글번호 부터
			
			int no = Integer.parseInt(request.getParameter("no"));
			
			try {
				
				Notice notice = noticeService.updateForm(no);
				
				if(notice != null) {
					path = "/WEB-INF/views/notice/noticeUpdate.jsp";
					request.setAttribute("notice", notice);
					view= request.getRequestDispatcher(path);
					view.forward(request, response);
				}else {
					request.getSession().setAttribute("msg", "공지사항 수정화면 출력 실패");
				}
				
			}catch (Exception e) {
				ExceptionForward.errorPage(request, response, "공지사항 수정화면으로 전환하는", e);
			}
		}
		
		
		// 공지사항 수정 Controller
		else if(command.equals("/update")) {
			
			int noticeNo = Integer.parseInt(request.getParameter("no"));
			String noticeTitle = request.getParameter("title");
			String noticeContent = request.getParameter("content");
			
			Notice notice = new Notice(noticeNo, noticeTitle, noticeContent);
			
			
			try {
				int result = noticeService.updateNotice(notice);
				
				if(result>0) msg="공지사항이 수정되었습니다.";
				else         msg="공지사항 수정 실패";
				
				request.getSession().setAttribute("msg", msg);
				response.sendRedirect("detail?no="+noticeNo);
				
				
			}catch(Exception e) {
				ExceptionForward.errorPage(request, response, "공지사항 수정", e);
			}
		}
			// 공지사항 삭제 Controller
			else if(command.equals("/delete")) {
				int no = Integer.parseInt(request.getParameter("no"));
				
				try {
					int result = noticeService.deleteNotice(no);
					
					if(result >0) {
						msg = "공지사항이 삭제되었습니다.";
						path="list";
					}
					else    {    
						msg = "공지사항 삭제 실패";
						path = "detail?npo"+no;
								
					}
					request.getSession().setAttribute("msg", msg);
					response.sendRedirect(path);
				}catch(Exception e) {
					ExceptionForward.errorPage(request, response, "공지사항 삭제", e);
				}
			}
			
		// 공지사항 검색용 Controller 
			else if(command.equals("/search")) {
				String searchKey = request.getParameter("searchKey");
				String searchValue = request.getParameter("searchValue");
			
			try {
				List<Notice> list = noticeService.searchNotice(searchKey, searchValue);
				// 검색결과 담아옴
				
				path ="/WEB-INF/views/notice/noticeList.jsp";
				request.setAttribute("list", list);
				view = request.getRequestDispatcher(path);
				view.forward(request, response);
				
			}catch(Exception e){
				ExceptionForward.errorPage(request, response, "공지사항 검색", e);
			}
			
			
			
			}
		
		
		
		
		
		
		}
		
		
		
	

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
