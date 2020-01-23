package com.kh.sjproject.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import com.kh.sjproject.wrapper.EncryptWrapper;

@WebFilter(urlPatterns = {"/member/login.do", "/member/signUp.do", "/member/updatePwd.do","/member/deleteMember.do"})
public class EncryptFilter implements Filter {

    public EncryptFilter() {
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		//   1/16일 암호화 
		HttpServletRequest h_request = (HttpServletRequest)request;
						// 아이디 비번,		// 다운캐스팅으로 강제 형변환
						// 여기서 비번만 빼내옴
		
		//Encrypt Wrapper 객체 생성 (클래스) <<-- HttpServletRequestWrapper 상속해서 오버라이딩
		EncryptWrapper encWrapper = new EncryptWrapper(h_request);
		
		chain.doFilter(encWrapper, response);
		
		
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
