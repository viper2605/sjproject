package com.kh.sjproject.wrapper;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class EncryptWrapper extends HttpServletRequestWrapper{

	
	// 상속받은 HttpServletRequestWrapper 클래스는 기본 생성자가 없음
	// 반드시 명시적으로 HttpServletRequest를 매개변수로 하는 생성자 작성이 필수
	public EncryptWrapper(HttpServletRequest request) {
		super(request);
		
	}

	
	// ServletRequest의 getParameter() 오버라이딩
	@Override
	public String getParameter(String key) {
						
		// 요청 데이터의 key에 대응되는 value를 저장할 변수 선언				
		String value="";
		
		if(key != null && 
				(key.equals("memberPwd") || key.equals("pwd")  
						||  key.equals("newPwd1")  || key.equals("currentPwd"))) {
				// 암호화 진행-> SHA-512 해시(Hash) 함수 암호화
				// 해새함수란?     (2의 9제곱)
				// 임의의 길이의 데이터를 고정한 길이의 데이터로 매핑하느 함수
			
			value = getSha512(super.getParameter(key));
									//.	||       (암호) ->super로 넘김
				// input   ----->>>>>  value					   		
				
		}else {
			value = super.getParameter(key);
			// 이건 암호화 없이 그대로 전달
		}
			// 인풋 태그중에서 겟파라미터에 들어와 
		
		return value;
	}
	
	
	
	/** SHA-512 해시 함수를 사용하여 암호화 하는 메소드
	 * @param pwd
	 * @return EncPwd
	 */
	public static String getSha512(String pwd){
		
		String encPwd=null;
		
		MessageDigest md= null;
		//	--> 지정된 알고리즘에 따라 햄수 함수를 진행하는 크래스
		
		try {
			// MessageDigest.getInstance("알고리즘명"
			// 지정된 알고리즘을 사용해 MessageDigest 객체를 ㅏㅏ
			md = MessageDigest.getInstance("SHA-512");
			
		}catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	
		// 암호화를 진행하기 위해서는
		// 전달받은 문자열(비밀번호)를 바이트 배열로 변환해야함
		// 이 이유로 암호는 영문 특수 숫자만 가능함(한글.2바이트 먹어서 어려움)
		byte[] bytes = pwd.getBytes(Charset.forName("UTF-8"));
		
		// md 객체에 pwd 바이트배열을 전달하여 갱신작업
		// -> 실제 암호화(해시 함수 적용) 진행
		
		md.update(bytes);
		
		encPwd = Base64.getEncoder().encodeToString(md.digest());
		
		
		System.out.println("암호화 전 : " + pwd);
		System.out.println("암호화 후 : " + encPwd);
		
		return encPwd;
	}
	
	
	// HttpServletRequest의 getParameter() 오버라이딩
		// 요청할 때 인풋 태그에 네임 속성을 가진 값을 가져옴                                                                                                               
	
	
	
	
	
}
