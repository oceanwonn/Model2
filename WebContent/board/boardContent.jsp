<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>boardContent.jsp</h1>
	
	
	<%
		// request.setAttribute("dto", dto);
		// request.setAttribute("pageNum", pageNum);
	
	%>
	
	<table border="1">
	<tr>
		<td>글번호</td>
		<td>${dto.bno }</td>
		<td>조회수</td>
		<td>${dto.readcount }</td>	
	</tr>
	  <tr>
         <td>작성자</td>
         <td>${dto.name }</td>
         <td>작성일</td>
         <td>${dto.date }</td>      
      </tr>
	<tr>
		<td>제목</td>
		<td colspan="3">${dto.subject }</td>	
	</tr>
	
	<tr>
		<td>내용</td>
		<td colspan="3">${dto.content }</td>	
	</tr>
	<tr>
		<td>첨부파일</td>
		<td colspan="3">${dto.file }</td>	
	</tr>
	<tr>
		<td><input type="button" value="수정"></td>
		<td><input type="button" value="삭제"></td>
		<td><input type="button" value="답글"></td>
		<td>
		<input type="button" value="목록" onclick="location.href='./BoardList.bo?pageNum=${pageNum}';">
		</td>
	</tr>
	
	</table>

</body>
</html>