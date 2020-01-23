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


@WebServlet("/member/deleteMember.do")
public class DeleteMemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
    public DeleteMemberServlet() {
        super();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 아이디(session), 비밀번호(request) 얻어오고
		
		// 비즈니스 로직  처리를 위한 메소드 호출 후, 반환 값 저장
		
		
		
		
		HttpSession session= request.getSession();
		Member loginMember =(Member)session.getAttribute("loginMember");
		
		String memberPwd = request.getParameter("currentPwd");
	
		// loginMember 객체에 현재 비밀번호를 저장
		loginMember.setMemberPwd(memberPwd);
		// 아이디 + 비번 가쟈오는거 잘 기억할 것!!
		
		
		try {
			
			int result = new MemberService().deleteMember(loginMember,memberPwd);
			// 비밀번호 X -> "비밀번호 불일치" -> 회원탈퇴 페이지로 이동
			// 비밀번호 O, 실패 -> "탈퇴 실패" -> 마이 페이지로 이동
			// 비밀번호 O, 성공 -> "탈퇴 되었습니다" -> '메인' 페이지로 이동
			String msg=null;
			String path= null;
			
			if(result<0) {
				msg = "비밀번호 불일치";
				path = "secession.do";
			}else if(result ==0) {
				msg = "탈퇴 실패";
				path = "mypage.do";
			}else {
				msg ="탈퇴 성공";
				path = request.getContextPath();
				
			//	session.invalidate(); // 세션 무효화~~ 이거 쓰면 망함..
				// > session 무효화 시 모든 session 데이터가 사라지므로
				// msg 전달 등의 기능이 동작하지 않는 문제 발생
				
				session.removeAttribute("loginMember");
			}
			
			session.setAttribute("msg", msg);
			response.sendRedirect(path);
			
		}catch (Exception e) {
		
			request.setAttribute("errorMsg", "아이디 중복 확인 과정에서 오류가 발생하였습니다.");
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
