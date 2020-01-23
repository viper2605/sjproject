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
import com.kh.sjproject.member.model.vo.Member;

import javafx.scene.control.Alert;


@WebServlet("/member/signUp.do")
public class signUp extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
    public signUp() {
        super();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 1 POST 방식 + 한글 데이터 포함 -> 인코딩 설정
		// request.setCharacterEncoding("UTF-8");
		
		// 2. 전달된 파라미터를 변수에 저장 후 Member객체를 생성하여 저장
		
		String memberId = request.getParameter("id");
		String memberPwd = request.getParameter("pwd1");
		String memberName = request.getParameter("name");
		String memberPhone = request.getParameter("phone1")+ "-"+
							request.getParameter("phone2")+ "-" +
							request.getParameter("phone3");
		// DB에 전화번호 1칸만 저장 가능하므로 
		// 전화번호를 '-' 구분자로 하여 하나의 String으로 합침
		
		String memberEmail = request.getParameter("email");
		
		String post = request.getParameter("post");
		String address1 = request.getParameter("address1");
		String address2 = request.getParameter("address2");
		// memberAdreaa = request.getParameter("address1") +
		// 				request.getParameter("address2") ;  도 가능
		
		// 주소를 ',' 를 구분자로 하여 하나의 Stringd으로 합침
		String memberAddress = post +  "," + address1 + ","+address2;
		
		
		 String[] interest = request.getParameterValues("memberInterest");
		// 강사님 -> 관심분야 배열을 ","를 구분자로 하여 하나의 String으로 합침
 		String memberInterest = null;
		if(interest !=null)memberInterest = String.join(",",interest);
		 
		
	//이것도 됨	 String memberInterestArr[] = request.getParameterValues("memberInterest");
		//String memberInterest = String.join(", ",memberInterestArr);
		
		
		// 전달받은 파라미너를 Member 객체에 저장
		Member member = new Member(memberId, memberPwd, memberName, memberPhone, memberEmail, memberAddress, memberInterest);
		
		
		System.out.println("signup : " + member);
		
		// 내가 한거 추청 (아래)
		//   RequestDispatcher view = request.getRequestDispatcher("views/testServlet4End.jsp");
		//   view.forward(request, response);
		   PrintWriter out = response.getWriter();
			
		// 3. 비즈니스 로직을 수행할 DB에 회원정보 저장
		try {
			
			 int result = new MemberService().signUp(member);
			
			// 4. db 까지 다녀온 후
			// result 값에 따라 경고창에 메세지 출력
					
			 String msg = null;
			 if(result >0) msg="가입 성공!";
			 else 		   msg="가입 실패!";
			 
			// 메인창에 어떻게 띄울지 고민~
			 //앞에서 로그인 했던 것을 재활용,  header.jsp~~ 
			 // 로그인 ㅎ실패후 ㅂ메시지 출력후 사용하는 '세션' 재활용
			// header.jsp 여기서도 직접 처리 가능
			 
			 
			 // session의 "msg" 속성에 msg 세팅
			 request.getSession().setAttribute("msg", msg);
			 
			//포워드와 샌드 중 고민. 
			 // 주소창 안바뀌는 샌드 리다이렉트 사용!(
			 // signUp.do로, 요청받은 경로를 받아와 응답처리~
			 response.sendRedirect(request.getContextPath());
			 
			 
			 
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
