package com.itwillbs.board.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BoardFrontController extends HttpServlet{

	//URL : http://localhost:8088/Model2/test.bo
	//URI : /Model2/test.bo
	protected void doProcess(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
			System.out.println(" GET,POST방식 모두 호출 - doProcess() 실행 ");
			
			System.out.println("\n 1. 가상주소 계산 - 시작");
			// 1. 가상주소 계산------------------------------------------------
			String requestURI = request.getRequestURI();
			System.out.println(" C : requestURI : "+requestURI);
			
			String ctxPath = request.getContextPath();
			System.out.println(" C : ctxPath : "+ctxPath);
			
			String command = requestURI.substring(ctxPath.length());
			System.out.println(" C : command : "+command);						
			// 1. 가상주소 계산------------------------------------------------
			System.out.println(" 1. 가상주소 계산 - 끝 \n");
			
			
			System.out.println("\n  2. 가상주소 매핑 - 시작");
			// 2. 가상주소 매핑------------------------------------------------
			Action action = null;
			ActionForward forward = null;
			
			if(command.equals("/BoardWrite.bo")){
				// 글쓰기 페이지 보여주기 (DB정보 필요없음)
				System.out.println(" C : /BoardWrite.bo 호출 ");
				System.out.println(" C : DB정보가 필요없음-view페이지로 이동 ");
				
				forward = new ActionForward();
				forward.setPath("./board/writeForm.jsp");
				forward.setRedirect(false);
			}
			else if(command.equals("/BoardWriteAction.bo")){
				System.out.println(" C : /BoardWriteAction.bo 호출 ");
				System.out.println(" C : DB작업 o, 페이지 이동");
				
				// BoardWriteAction() 객체 생성
				//BoardWriteAction bwAction = new BoardWriteAction();
				action = new BoardWriteAction();
				try {
//				    forward = bwAction.execute(request, response);
					forward = action.execute(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}else if(command.equals("/BoardList.bo")){
				 System.out.println(" C : /BoardList.bo 호출 ");
				 System.out.println(" C : DB정보가 필요,페이지 이동x,페이지 출력o");
				 
				 // BoardListAction() 객체 생성
				 //BoardListAction listAction = new BoardListAction();
				 action = new BoardListAction();
				 try {
					 System.out.println(" C : 해당 Model 객체 호출 ");
					//forward = listAction.execute(request, response);
					forward = action.execute(request, response);
				 } catch (Exception e) {
				 	e.printStackTrace();
				 }
			}
			else if(command.equals("/BoardContent.bo")){
				System.out.println(" C : /BoardContent.bo 호출 ");
				System.out.println(" C : DB정보 사용, 출력");
				
				// BoardContentAction 객체
				action = new BoardContentAction();
				
				try {
					forward = action.execute(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
			
			
			
			// 2. 가상주소 매핑------------------------------------------------
			System.out.println("  2. 가상주소 매핑 - 끝 \n");
			
			System.out.println("\n  3. 가상주소 이동 - 시작");
			// 3. 가상주소 이동------------------------------------------------
			if(forward != null){
				// 페이지 이동정보가 있을때
				
				if(forward.isRedirect()){
					// true - sendRedirect() 방식으로 이동
					System.out.println(" C : true-"+forward.getPath()+"이동, sendRedirect() 방식");
					response.sendRedirect(forward.getPath());
					
				}else{
					// false - forward() 방식으로 이동	
					System.out.println(" C : false-"+forward.getPath()+"이동, forward() 방식");
					RequestDispatcher dis 
					   = request.getRequestDispatcher(forward.getPath());
					dis.forward(request, response);					
				}				
				
			}			
			// 3. 가상주소 이동------------------------------------------------		
			System.out.println("\n  3. 가상주소 이동 - 끝");
	}
	////////////////////////////////doProcess////////////////////////////////////
	
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
			System.out.println(" GET방식 호출 - doGet() 실행");
			doProcess(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
			System.out.println(" POST방식 호출 - doPost() 실행 ");
			doProcess(request, response);
	}

}
