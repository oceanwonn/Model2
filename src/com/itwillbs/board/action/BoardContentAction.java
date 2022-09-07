package com.itwillbs.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itwillbs.board.db.BoardDAO;
import com.itwillbs.board.db.BoardDTO;
import com.mysql.cj.protocol.x.SyncFlushDeflaterOutputStream;

public class BoardContentAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, 
					HttpServletResponse response) throws Exception {
		
		System.out.println(" M : BoardContentAction_execute() 호출 ");
		
		// 전달된 정보 저장(bno, pageNum)
		// => 전달되는 파라메터값의 경우
		// 테이블에 저장되는 값이면 형변환 O
		// 테이블에 저장이 안 되는 값이면 형변환 X
		int bno = Integer.parseInt(request.getParameter("bno"));
		String pageNum = request.getParameter("pageNum");
		
		
		
		// BoardDAO 객체 생성
		BoardDAO dao = new BoardDAO();
		
		// 조회수 1 증가하기 - updateReadcount()
		dao.updateReadcount(bno);
		
		System.out.println(" M : 조회수 1 증가 완료 !! ");
		
		// 게시판 글 1개의 정보를 가져와서 출력 
		BoardDTO dto = dao.getBoard(bno);
		
		// Model 객체 정보 출력 X
		// view에서 정보 출력 O
		// => model 객체 있는 정보를 view 이동
		
		// request 영역에 저장
		request.setAttribute("dto", dto); //1번 방식) db에는 없지만 dto에는 있는 값은 이 방식으로
		
		// request.setAttribute("dto2", dao.getBoard(bno));     -> 2번 방식) 일반적으로 많이 쓰는 방식
		
		request.setAttribute("pageNum", pageNum); //출력할 때 사용
		
		
		//출력 view 페이지로 이동
		ActionForward forward = new ActionForward();
		forward.setPath("./board/boardContent.jsp");
		forward.setRedirect(false); 
		// 주소가 뭔지 몰라도 상관 없기 때문에! 영역에 저장한 순간 이동방식은 false로 지정
		// .jsp 라는 주소도 false로 지정 (주소창에 ~jsp라는 주소는 나오면 안 됨)
		
		
		return forward;
	}
	

}
