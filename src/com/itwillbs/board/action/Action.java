package com.itwillbs.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Action {
	// 변수 X, 상수 O
	// 인스턴스 메서드 x, 추상메서드 o
	// => 상속을 통해서 추상메서드를 오버라이딩 사용 (강제성)
	
	// 아래는 다큐먼트 주석
	// => ** 치고 엔터 누르면 자동으로 밑에 생성됨
    /* 추상메서드이며, 반드시 오버라이딩해서 사용해야함.
	실행할 때 request, response 정보를 전달해야지만 호출 가능
	호출이 완료되면 ActionForward(주소, 방식) 정보를 리턴
	
	 *
	 *	@param request
	 *  @param response
	 *	@return
	 *	@throws Exception
	 */
	
	
	

	public ActionForward execute(HttpServletRequest request, 
			                   HttpServletResponse response) throws Exception; 
	// actionforward(페이지 이동정보, 주소 저장 객체) return
}
