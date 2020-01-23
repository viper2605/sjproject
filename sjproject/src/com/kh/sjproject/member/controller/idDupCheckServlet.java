package com.kh.sjproject.member.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.sjproject.member.model.service.MemberService;

@WebServlet("/member/idDupCheck.do")
public class idDupCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public idDupCheckServlet() {
        super();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// POST 방식 문자 인코딩 변경 이유 -> 한글 때문
		// ID는 영어 + 숫자이기 때문에 별도의 문자인코딩 필요 없음.
		String id = request.getParameter("id");
		
		try {
			int result = new MemberService().idDupCheck(id);
			
			/*
			 * request.setAttribute("result", result); request.setAttribute("id", id); // 같은
			 * 키 값으로 계속 유지, 로그인 계속 유지할 때 사용
			 * 
			 * RequestDispatcher view = request.getRequestDispatcher("idDupForm.do"); //
			 * /member 경로 생략 가능. // 같은 곳에 있기 때문 view.forward(request,response);
			 */
			
			PrintWriter out = response.getWriter();
			if(result > 0) out.append("no");  // 중복되었다는 의미
			else           out.append("yes");
			
			
		}catch (Exception e) {
			
			request.setAttribute("errorMsg", "아이디 중복 확인 과정에서 오류가 발생하였습니다.");
			
			e.printStackTrace();
			
			String Path ="/WEB-INF/views/common/errorPage.jsp";
			RequestDispatcher view = request.getRequestDispatcher(Path);
			view.forward(request,response);
			
			
		}
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
