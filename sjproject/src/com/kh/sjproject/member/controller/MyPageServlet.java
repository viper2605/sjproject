package com.kh.sjproject.member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.sjproject.member.model.service.MemberService;
import com.kh.sjproject.member.model.vo.Member;

@WebServlet("/member/mypage.do")
public class MyPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MyPageServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		// 로그인 되어있는 상태, 세션값 가져오기!
		// 세션에 저장되어 있는 ID 가져오기
		
		
		// 세션에 저장되어 있는 ID 가져오기 한줄로!!
		//String memberId= ((Member)(request.getSession()
		//			.getAttribute("loginMember")))
		//			.getMemberId();
		
		HttpSession session =request.getSession();
		Member loginMember = (Member)session.getAttribute("loginMember");
									// 오브젝스 속성을 Member 속성으로 바꿈
		String memberId= loginMember.getMemberId();
		// 내 아이디 자가ㅕ온것
		//다른 사람것 조회하려면 입력받은 id 값으로 조회하기. 위 합수 변형하면 될듯.
		
		
		// 아이디 조회 후 해당 아이디로 조회된 DB 조회해오기
		
		try {
			//DB에서 아이디가 일치하는 회원정보 읽어오기
			// 회원 정보가 많아 하나의 Member에 가져오기
			Member selectMember
				= new MemberService().selectMember(memberId);
			
			request.setAttribute("member", selectMember);
			
			
			// 포워드로 화면 전환만 먼저(처리된 게 이 화면으로 감. 잊지말것 JDBC에서 VIEW로 이동)
			String path = "/WEB-INF/views/member/mypage.jsp";
			RequestDispatcher view = request.getRequestDispatcher(path);
			view.forward(request, response);
			
			
		}catch (Exception e) {
			
			request.setAttribute("errorMsg", "로그인 과정에서 오류가 발생하였습니다.");
			
			e.printStackTrace();
			
			String Path ="/WEB-INF/views/common/errorPage.jsp";
			RequestDispatcher view = request.getRequestDispatcher(Path);
			view.forward(request,response);
		}
		
		
		
	
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
