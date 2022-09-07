<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
   <!-- index.jsp -->
   <%
      // MVC 프로젝트를 실행시키는 시작 페이지
      // **  MVC 프로젝틍서는 index.jsp 페이지 외 절대로 .jsp페이지 실행 금지
      // => 주소창에 .jsp 주소가 보이면 MVC패턴 깨짐
      
      // response.sendRedirect("./Test.bo");
   
      // 글쓰기 페이지 이동
      //  response.sendRedirect("./BoardWrite.bo");
      
      // 글 리스트 페이지 이동
       response.sendRedirect("./BoardList.bo");   
      // 상대 주소
      // 두 가지 형태
      
      // 가상 주소 사용 시
      // 1. java (서블릿:컨트롤러) : ./Test.bo
      // 가상 주소 일 때 .의 의미 : 프로젝트명
      
      // 실제 주소 사용 시
      // 2. jsp (View) : ./Test.jsp
      // 실제 주소일 때 .의 의미 : WebContent
   %>
</body>
</html>