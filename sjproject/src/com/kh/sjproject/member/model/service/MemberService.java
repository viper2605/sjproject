package com.kh.sjproject.member.model.service;

// import static com.kh.sjproject.common.JDBCTemplate.getConnection;

import java.sql.Connection;

import com.kh.sjproject.member.model.dao.MemberDAO;
import com.kh.sjproject.member.model.vo.Member;
import static com.kh.sjproject.common.JDBCTemplate.*;

public class MemberService {

	/** 로그인용 Service
	 * @param member
	 * @return loginMember
	 * @throws Exception
	 */
	public Member loginMember(Member member)throws Exception {
		Connection conn = getConnection();
		
		Member loginMember = new MemberDAO().loginMember(conn,member);
			
		return loginMember;
	}

	/** 아이디 중복 확인용 Service
	 * @param id
	 * @return result
	 * @throws Exception
	 */
	public int idDupCheck(String id) throws Exception{
		Connection conn = getConnection();
		
		
		return new MemberDAO().idDupCheck(conn,id);
	}

	/** 회원가입용 서비스
	 * @param member
	 * @return result
	 * @throws Exception
	 */
	public int signUp(Member member)throws Exception {
		
		Connection conn = getConnection();
		
		MemberDAO memberDAO = new MemberDAO();
		int result = memberDAO.signUp(conn,member);
		
		// int result = new MemberDAO().signUp(conn,member); 이것도 가능
		
		if(result >0) { 	
			commit(conn);
		}else { 
			rollback(conn);
		}
		
		return result;
	}

	
	/** 회원 정보 조회용 서비스
	 * @param memberId
	 * @return selectmember  // 조회만 하기 때문에 트랜잭션 필요 없음
	 * @throws Exception
	 */
	public Member selectMember(String memberId)throws Exception {
		Connection conn = getConnection();
		
		
		return new MemberDAO().selectMember(conn,memberId);
	}

	
	
	public int updateMember(Member member)throws Exception {
		Connection conn = getConnection();
		
		
		  MemberDAO memberDAO = new MemberDAO(); 
		  int result = memberDAO.updateMember(conn,member);
		
		
		// int result = new MemberDAO.updateMember(conn,member);
		
		if(result >0) { 	
			commit(conn);
		}else { 
			rollback(conn);
		}
		
		return result;
		
		
	}

	/** 비밀번호 수정용 서비스
	 * @param loginMember
	 * @param newPwd
	 * @return result
	 * @throws Exception
	 */
	public int updatePwd(Member loginMember, String newPwd) throws Exception {
		Connection conn = getConnection();
			
			MemberDAO memberDAO = new MemberDAO();
		
			// 현재 비밀번호 일치 여부 확인
			
			// 비밀번호 일치 확인하려면 쿼리문은?
			// SELECT COUNT(*) FROM MEMBER WHERE MEMBER_ID=? AND MEMBER_PWD=?
			// COUNT 한후 결과가 있으면 1, 없으면 0
			
			int result = memberDAO.checkPwd(conn,loginMember);
												// 이 안에 아이디 비번 정보 있음
			
			if(result>0) { // 현재 비밀번호가 일치할 경우,
				
				// 비밀번호 변경
				loginMember.setMemberPwd(newPwd);
				result = memberDAO.updatePwd(conn, loginMember);
				
				// 트랜잭션 처리. 비먼 변경되었기 대문ㅇ
				
				if(result>0) commit(conn);
				else		rollback(conn);
				
				return result;
				// 여기는 0 ,1 만 반환, 그외에 다른 비번을 넣어 틀릴 경우는?? 아래에.. 
				
			}else {
				return -1;  // 0 과 1 이욍[ 다른 result 값을 넣어 틀렸따느 것을 의미해줌. 
				// 컨트롤러 영역에서 -1이 무엇인지 판단하게 함
			}
			
		
	}

	public int deleteMember(Member loginMember, String memberPwd) throws Exception {
		Connection conn = getConnection();
		
		// 현재 비밀번호 + 현재 아이디 확인 때문에 memberDAO 2개 필요.. 
		
		MemberDAO memberDAO= new MemberDAO();
		
		int result = memberDAO.checkPwd(conn,loginMember);
		
		if(result >0 ) { // 현재 비밀번호 일치
			
			result = memberDAO.deleteMember(conn, loginMember.getMemberId());
			
			if(result >0 ) commit(conn);
			else 			rollback(conn);
			
			return result;
		}else { // 현재 비밀번호 불일치
			return -1;
		}
			
		
	}

	
	
}



