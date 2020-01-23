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


@WebServlet("/member/updatePwd.do")
public class UpdatePwdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public UpdatePwdServlet() {
        super();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 잘 생각해보기!!
		// 1. POST 방식, 한글 미포함(비번에 한글없음)
		// 즉 문자 인코딩 변환할 필요 없음
		
		// 2. 전달받은 파라미터를 변수에 저장
		//  +  session에서 로그인 아이디 얻어오기 (가져온 후 비번이 누구껀지 조회 해야함)
		
		HttpSession session = request.getSession();
		Member loginMember= (Member)session.getAttribute("loginMember");
		// String memberId = loginMember.getMemberId(); <--
		// 전달받은 파라미터를 효율적으로 서비스단으로 전달하기 위해
		// loginMember를 활용
		// ->> loginMember에 id가 저장되어 있으므로 memberId가 불필요해짐.
		
		
		
		String currentPwd = request.getParameter("currentPwd");
		loginMember.setMemberPwd(currentPwd);
		// loginMember 객체에 currentPwd를 저장하여
		// 서비스로 전달하는 매개변수 개수를 줄임
		// 
		
		String newPwd = request.getParameter("newPwd1");
		
		
		// 3. 비즈니스 로직을 위한 서비스 호출 후, 반환 값 저장
		
		try {
			int result = new MemberService().updatePwd(loginMember,newPwd);
			
			// 비번 변경 화면에서 '변경하기' 누를 때 어느 화면으로 갈지 결정
			// mail
			// mypage  ->> 이 화면으로 가봄
			// changepwd 이 화면 중에 하나 선택해야함. 
			
			String msg = null;
			String path = null; // 어느 경로로 갈지 미리 지정 준비!
			
			if(result > 0 ) {
				msg = "비밀번호가 변경되었습니다.";
				path = "mypage.do";
			}else if(result == 0 ) {
				msg = "비밀번호가 변경에 실패하였습니다.";
				path = "mypage.do";
				// DB 문제이므로 관리자에게~
			}else {
				msg = "현재 비밀번호가 일치하지 않습니다.";
				path = "changePwd.do";
				// 여기에 -1 값이 있어서 여기서 작동하게됨
			}
			
			session.setAttribute("msg", msg);
			response.sendRedirect(path);
			
		}catch (Exception e){
			
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
