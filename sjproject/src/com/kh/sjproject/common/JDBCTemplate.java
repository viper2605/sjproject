package com.kh.sjproject.common;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCTemplate {
	
	
	// 한 개의 공용 Connection 객체를 저장할 참조 변수 선언
	// 단, 외부에서 직접 접근할 수 없도록 private으로 선언	
	private static Connection conn = null;
	
	// DB 연결을 위한 Connection 객체 요청 메소드 작성
	public static Connection getConnection() {
		
		// 프로그램 실행 후 한번도 getConnection() 메소드가
		// 호출되지 않았을 때
		// -> Connection 객체 생성
		
			/* 이전 프로젝트에서 Connection 생성 과정
			 * - JDBC 드라이버 로드
			 * - DB 연결을위 한 정보(url, id, pwd)
			 * 이러한 내용들을 직접 작성함. (정적 코딩)
			 * --> 추후 DB정보가 변경되는 경우
			 *     코드 자체를 수정해서 다시 컴파일, 배포해야함.
			 *     --> 유지보수 불편
			 *  
			 * 이를 해결하기 위해 Properties 파일을 사용.
			 * 프로그램 실행 시 동적으로 Properties 파일에서
			 * DB 연결 정보를 읽어오도록 코딩. (동적 코딩)
			 * 
			 * -> driver.properties 파일 작성
			 * */
			
			// 외부에서 DB 연결 정보 읽어올 Properties 객체 생성
			try {
				if(conn == null || conn.isClosed()) {  // 임시방편.. 싱글톤 닫는거 포기함..
				Properties prop = new Properties();
				
				String fileName= JDBCTemplate.class
						.getResource("/com/kh/sjproject/sql/driver.properties")
						.getPath();
				
				prop.load(new FileReader(fileName));     
				// -> IOException 발생 가능성이 있음.
				
				// driver.properties에서 읽어들인 정보를 이용해
				// DB와 연결할 Connection 객체 생성
				
				
				
				//ojdbc6 라이브러리를
				//WebContent/WEB-INF/lib 폴더에 추가
				
				// jdbc 드라이버 로드
				Class.forName(prop.getProperty("driver"));
				
				// Connection 객체 생성
				conn = DriverManager.getConnection(
						prop.getProperty("url"),
						prop.getProperty("user"),
						prop.getProperty("password"));
				
				// Auto Commit 비활성화
				conn.setAutoCommit(false);
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		
		return conn;
	}
	
	
	// DB 연결 관련 자원 반환 메소드 close() 작성
	public static void close(Statement stmt) {
		// PreparedStatement는 Statement의 자식
		// -> 상속 관계 -> 다형성 적용 -> 매개변수로 부모 타입 사용 가능
		try {
			if(stmt != null && !stmt.isClosed()) {
				stmt.close();
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(ResultSet rset) {
		try {
			if(rset != null && !rset.isClosed()) {
				rset.close();
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(Connection conn) {
		try {
			if(conn != null && !conn.isClosed()) {
				conn.close();
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	// 처리 결과에 따른 트랜잭션 처리도 공통적인 업무임.
	// --> static으로 선언하여 코드길이 감소, 재사용성의 증가
	public static void commit(Connection conn) {
		try {
			if(conn != null && !conn.isClosed()) {
				conn.commit();
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void rollback(Connection conn) {
		try {
			if(conn != null && !conn.isClosed()) {
				conn.rollback();
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
}

