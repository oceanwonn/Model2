<%@page import="com.itwillbs.board.db.BoardDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
   <h1>boardList.jsp</h1>
   
   <h2> 🌟 Won's Board 🌟 </h2>

   
   <h3><a href="./BoardWrite.bo"> 📃 글을 작성해보세요 (new) </a></h3>
   
   
   <table border="1">
      <tr>
        <td>번호</td>
        <td>제목</td>
        <td>글쓴이</td>
        <td>조회수</td>
        <td>작성일</td>
        <td>IP</td>
      </tr>
      
    <c:forEach var="dto" items="${requestScope.boardList }">  
	     <tr>
	        <td>${dto.bno }</td>
	        <td>
	        <a href="./BoardContent.bo?bno=${dto.bno }&pageNum=${requestScope.pageNum}">${dto.subject }</a>
	        </td>
	        <td>${dto.name }</td>
	        <td>${dto.readcount }</td>
	        <td>${dto.date }</td>
	        <td>${dto.ip }</td>
	      </tr>
     </c:forEach>
      
   
   </table>
   
        <!-- 하단 페이징 처리 -->
   	 
	    <c:if test="${cnt != 0 }">
     
     	<!-- 이전 : 직전 페이지블럭의 첫번째 페이지 호출 -->
     	<c:if test="${startPage > pageBlock }">
     		<a href="./BoardList.bo?pageNum=${ startPage-pageBlock}">[이전]</a>
     	</c:if>	
     	
     	<!--  1,2,3,4,5,.... -->
        <c:forEach var="i" begin="${startPage }" end="${endPage }" step="1">
       		 <a href="./BoardList.bo?pageNum=${i }">[${i }]</a> 
        </c:forEach>
     
        <c:if test="${endPage < pageCount }">
            <a href="./BoardList.bo?pageNum=${ startPage+pageBlock}">[다음]</a>
        </c:if>
     </c:if>
   
   
   
</body>
</html>