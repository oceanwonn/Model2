package com.itwillbs.board.action;

public class ActionForward {
	// 페이지 이동을 위한 정보를 저장하는 객체
	
	private String path; // 이동할 주소
	private boolean isRedirect; // 이동할 방식
	
	// true - sendRedirect()방식으로 이동
	// false - forward() 방식으로 이동
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	// is가 붙어있으면 get/set 메서드가 is~로 나옴
	public boolean isRedirect() {
		return isRedirect;
	}
	public void setRedirect(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}
	
	
}
