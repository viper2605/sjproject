package com.kh.sjproject.member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.sjproject.member.model.service.MemberService;
import com.kh.sjproject.member.model.vo.Member;


@WebServlet("/member/updateMember.do")
public class UpdateMemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public UpdateMemberServlet() {
        super();
       
    }

	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 1. mypage.jsp에 method가 POST 처리 방식이므로
		// POST 방식 + 한글 포함 시 문자 인코딩 처리
		
		request.setCharacterEncoding("UTF-8");
		
		// 2. 전달 받은 파라미터를 변수에 저장
		//   + 로그인 ID를 얻어와 Member 객체에 저장 (로그인 했던 아이디 가져오기)
		
		// session에 저장된 ID 얻어오기
		
			// getter , -> loginMember 객체안 -> session 안 -> HttpSession
		HttpSession session = request.getSession();	
		// 세션을 얻어온다
		
		Member loginMember = (Member)session.getAttribute("loginMember");
		// 1차 	Member loginMember = session.getAttribute("loginMember");
		// 2차      Member loginMember = (Member)session.getAttribute("loginMember");
		
		String	memberId = loginMember.getMemberId();
		
		
		
		// 파라미터 변수에 저장
		
		String memberPhone = request.getParameter("phone1")+ "-"+
							request.getParameter("phone2")+ "-"+
							request.getParameter("phone3");
		
		// 선생님 방식 String phone1 = request.getParameter("phone1")
		// 선생님 방식 String phone2 = request.getParameter("phone2")
		// 선생님 방식 String phone3 = request.getParameter("phone3")
		// -> String memberPhone = phone1 + "-" + phone2 + "-" +phone3;
		
		
		
		
		String memberEmail = request.getParameter("email");
		
		String memberAddress = request.getParameter("post")+ ","+
							request.getParameter("address1")+ ","+
							request.getParameter("address2");
		// 선생님 방식 String post = request.getParameter("post")
		// 선생님 방식 String address1 = request.getParameter("address1")
		// 선생님 방식 String address1 = request.getParameter("address2")
		// -> String memberPhone = post + "," + address1 + "," +address2;
		
		// 관심분야 
		
		String[] interest = request.getParameterValues("memberInterest");
		String memberInterest = null;
		if( interest !=null )memberInterest=String.join(",", interest);
		//    대괄호 생략              {                                           }
		
		
		// Member 객체에 저장
		Member member = new Member(memberId, memberPhone, memberEmail, memberAddress, memberInterest);
				
		// 3. 비즈니스 로직을 수행할 서비스 메소드 호출 후, 결과 값을 반환 받기(어렵게 설명한 것을 이해해보기)
		
		
		try {
			
			int result = new MemberService().updateMember(member);
			
			String msg = null;
			if(result > 0 ) msg= "회원 정보가 수정되었습니다.";
			else		msg= "회원 정보 수정에 실패 했습니다.";		
			
			
			
			// forward 샌드 리다이렉트  중 하나 선택
			// 주소를 바꿔주기 위해 sendRedirect 사용
			
			session.setAttribute("msg",msg);
			response.sendRedirect("mypage.do");
			
		/*	request.getSession().setAttribute("msg", msg);
			RequestDispatcher view = request.getRequestDispatcher("mypage.do");
			view.forward(request, response);
			이거 아님!!!!
			*/
			
			
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
