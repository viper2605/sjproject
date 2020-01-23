package com.kh.sjproject.notice.model.service;
import static com.kh.sjproject.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;

import static com.kh.sjproject.common.JDBCTemplate.*;

import com.kh.sjproject.notice.model.dao.NoticeDao;
import com.kh.sjproject.notice.model.vo.Notice;

public class NoticeService {

	public List<Notice> selectList() throws Exception{

		Connection conn = getConnection();
		
		List<Notice> list = new NoticeDao().selectList(conn);
		close(conn);
		return list;
	}

	
	
	public Notice selectNotice(int no)throws Exception {
		// 싱글톤패턴 일단 포기. 서버 접속 하는거 관리해줌.
		Connection conn = getConnection();
		
		NoticeDao noticeDao= new NoticeDao();
		
		
		//공지사항 상세조회
		Notice notice = new NoticeDao().selectNotice(conn,no); 
		
		//공지사항 상세조회 성공 시 조회수 증가
		if(notice != null) {
			int result = noticeDao.increaseCount(conn,no);
			
			if(result >0) {
				commit(conn);
				
				// 반환되는 notice는  조회수 증가가 되어있지 않으므로
				// 리턴 시 , 조회수를 +1 시켜줌
				notice.setNoticeCount(notice.getNoticeCount()+1);
				// notice의  값을 가져외 setNoticeCount에 +1을 넣음
			}else {
				rollback(conn);
				notice=null; // 조회수 증가 실패 시 조회되지 않게 만듦.
			}
		}
		close(conn);
		
		return notice;
	}



	/** 공지사항 등록용 Service
	 * @param notice
	 * @return result
	 * @throws Exception
	 */
	public int insertNotice(Notice notice)throws Exception {
		Connection conn = getConnection();
		
		// 2번 해야햠.. 게시물 번호부터 조회해야 하기 때문
		
		NoticeDao noticeDao = new NoticeDao();
		
		// 등록될 공지사항의 글 번호 생성
		// ->> 공지사항 등록 성공 시 해당 글 번호 상세 조회를 위해서 
		// 	+ 파일 업로드 시 현재 등록된 '글 번호'를 '외래키'로 사용하기 위해서 (두 테이블을 Join 하기 위해)
		// 			미리 BOARD 테이블 및 ATTACHMENT 테이블 미리 만듬
		
		int no = noticeDao.selectNextNo(conn);
		
		int result = 0;
		
		if(no >0) { // 다음 번호가 생성이 되었을 경우
			
			// DB 저장 시 개행문자 \r\n 을 <br>로 변경해야 
			// 상세 조회 시 줄바꿈이 유지된다.
			notice.setNoticeContent(
						notice.getNoticeContent().replace("\r\n", "<br>"));
					
			
			
			result = noticeDao.insertNotice(conn, notice, no);
			
			if(result > 0) { // 공지사항 DB 삽입 성공
				commit(conn);
				
				result =no;
				// 다음번호 조회 + 공지사항 등록 성공 시 
				// 등록 완료 후 해당 작성 페이지로 이동하기 위해서 
				// 조회된 번호를 반환시킴
			}else {
				rollback(conn);
			}
		}
		
		close(conn);
		
		
	//내가한거.	NoticeDao noticeDao = new NoticeDao().insertNotice(conn, notice);
		
		
		return result;
	}



	/** 공지사항 수정 화면용 Service
	 * @param no
	 * @return notice
	 * @throws Exception
	 */
	public Notice updateForm(int no) throws Exception{
		
		Connection conn = getConnection();
		
		// 조회하고 싶다면? 
		// 공지사항 상세조회~
		
		Notice notice = new NoticeDao().selectNotice(conn, no);
		
		// DB에 저장된 내용을 textarea에 출력할 경우
		// <br>로 저장되어있는 부분을 \r\n으로 변경해야함.. 
		notice.setNoticeContent(
				notice.getNoticeContent().replace("<br>", "\r\n")
				);
		
		close(conn);
		
		
		return notice;
	}



	/** 
	 * @param notice
	 * @return
	 */
	public int updateNotice(Notice notice) throws Exception {
		Connection conn = getConnection();
		
		// 수정된 내용이 DB에 저장될 경우 개행문자 변경 필요
		
		notice.setNoticeContent(
				notice.getNoticeContent().replace("\r\n", "<br>"));
				
		int result = new NoticeDao().updateNotice(conn, notice);
		
		if(result >0) commit(conn);
		else		  rollback(conn);
		
		
		return result;
	}



	/** 공지사항 삭제용 Service
	 * @param no
	 * @return result
	 * @throws Exception
	 */
	public int deleteNotice(int no) throws Exception{
		Connection conn = getConnection();
		
		int result = new NoticeDao().deleteNotice(conn,no);
		
		if(result>0) commit(conn);
		else         rollback(conn);
		
		close(conn);
		return result;
	}



	/** 
	 * @param searchKey
	 * @param searchValue
	 * @return list
	 */
	public List<Notice> searchNotice(String searchKey, String searchValue)throws Exception {
		Connection conn = getConnection();
		
		String condition = null;
		
		searchValue = "'%' || '" + searchValue + "'  ||'%'";
				// like '%공지%' <<-jdbc에서 그대로 sql 넣으면 문제 발생
				// 작은 따옴표로 문자열 만들기	   || <- 연결 연산자 : + 와 같음
				// ex) '% 공지%'
		 
		switch(searchKey) {
		case "title"   : condition = " NOTICE_TITLE LIKE " + searchValue; break;
		case "content" : condition = " NOTICE_CONTENT LIKE " + searchValue; break;
		case "titcont" : condition = " (NOTICE_TITLE LIKE " + searchValue
									+ " OR NOTICE_CONTENT LIKE" + searchValue + ")"; break;
		}
		
		List<Notice> list = new NoticeDao().searchNotice(conn,condition);
		
		close(conn);
		
		return list;
	}

	

	

}
