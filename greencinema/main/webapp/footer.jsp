<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"%>
<%@ page import="java.sql.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

	<footer class="site-footer">
		<div class="container site-footer__inner">
			<div>
				<div class="site-footer__brand">
					GREEN<span style="color: #03c75a">CINEMA</span>
				</div>
				<p class="footer-copy">
					그린시네마 · 조별과제 포트폴리오 UI 시안<br>영화, 극장, 상영시간, 요금 및 예매 정보는 모두 예시
					데이터입니다.
				</p>
			</div>
			<div class="site-footer__links">
				<a href="index.jsp?section=movie/movie-list.jsp">영화</a>
				<a href="index.jsp?section=reserve/reserve.jsp">예매</a>
				<a href="NoticeProc.do">공지사항</a>
				<a href="index.jsp?section=inquiry/inquiry.jsp">1:1문의</a>
				<a href="FaqProc.do">FAQ</a>
			</div>
		</div>
	</footer>