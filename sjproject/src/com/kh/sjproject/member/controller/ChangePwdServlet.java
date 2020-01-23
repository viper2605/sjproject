package com.kh.sjproject.member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ChangePwdServlet
 */
@WebServlet("/member/changePwd.do")
public class ChangePwdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public ChangePwdServlet() {
        super();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String path = "/WEB-INF/views/member/changePwd.jsp";
		RequestDispatcher view = request.getRequestDispatcher(path);
		view.forward(request, response);
		// 주소 노출 및 화면 전환
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
