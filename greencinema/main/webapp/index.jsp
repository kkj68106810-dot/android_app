<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"%>
<%@ page import="java.sql.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	request.setCharacterEncoding("utf-8");

	if ("admin".equals(session.getAttribute("id"))) {
		response.sendRedirect("admin/admin.jsp");
		return;
	}

	String section = request.getParameter("section");
	if (section == null) {
		request.getRequestDispatcher("MovieListProc.do").forward(request, response);
		return;
	}
	if (section.contains("..") || !section.endsWith(".jsp")) {
		response.sendRedirect("index.jsp");
		return;
	}
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="theme-color" content="#070d0a">
<title>그린시네마에 오신걸 환영합니다.</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body>
	<div class="index-wrap">
		<jsp:include page="header.jsp"></jsp:include>
		<main id="main">
			<jsp:include page="<%= section %>"/>
		</main>
		<jsp:include page="footer.jsp"></jsp:include>
	</div>
</body>
</html>
