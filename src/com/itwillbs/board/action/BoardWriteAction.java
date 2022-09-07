package com.itwillbs.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itwillbs.board.db.BoardDAO;
import com.itwillbs.board.db.BoardDTO;

public class BoardWriteAction implements Action{

	// alt shift s + v
	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		System.out.println(" M : BoardWriteAction_execute() 호출");
		// 한글 처리
		request.setCharacterEncoding("UTF-8");
		// 전달 정보 저장(제목, 비밀번호, 이름, 내용)
		
		// BoardDTO 객체 생성
		BoardDTO dto = new BoardDTO();
		
		dto.setName(request.getParameter("name"));
		dto.setPass(request.getParameter("pass"));
		dto.setSubject(request.getParameter("subject"));
		dto.setContent(request.getParameter("content"));
		
		
		// IP 주소 추가
		dto.setIp(request.getRemoteAddr());
		
		System.out.println(" M : "+dto);
		
		// DB에 정보 저장
	    // BoardDAO 객체 생성
	      BoardDAO dao = new BoardDAO();
	      
	   // DB에 글정보를 저장
	    dao.boardWrite(dto);
		
	    // 페이지 이동정보 저장(리턴) -> 여기서 페이지 이동하는거 아님. 
	    ActionForward forward = new ActionForward();
	    forward.setPath("./BoardList.bo");
	    forward.setRedirect(true);
		
		return forward;
	}

	
}
