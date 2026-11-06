<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<!doctype html>
	<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="그린시네마 포트폴리오 웹사이트 JSP/CSS 시안">
<meta name="theme-color" content="#11151a">
<title>1:1 문의 | 그린시네마</title>
<link rel="stylesheet" href="css/inquiry.css">
</head>
<body>
	<section class="inquiry"> 
		<form action="InquityInsertProc.do ">
		<a class="skip-link" href="#main">본문으로 건너뛰기</a>
		<main id="main">
			<div class="page-content">
				<div class="container">
					<div class="page-heading">
						<p class="page-heading__eyebrow">CUSTOMER SERVICE</p>
						<h1 class="page-heading__title">1:1 문의</h1>
						<p class="page-heading__description">회원 전용 1:1 문의 작성 화면입니다.</p>
					</div>
					<nav class="cs-tabs" aria-label="고객센터 메뉴">
						<a href="index.jsp?section=notice/notice.jsp">공지사항</a>
						<a href="index.jsp?section=faq/faq.jsp">FAQ</a>
						<a href="index.jsp?section=inquiry/inquiry.jsp" aria-current="page">1:1 문의</a>
					</nav>
	
					<section class="inquiry-form">
						<div class="form-grid">
							<div class="form-group">
								<label for="inqSort" class="form-label">문의 분류</label>
								<select id="inqSort" name="inquirySort" class="form-control">
									<option value="영화관">영화관</option>
									<option value="회원정보">회원정보</option>
									<option selected value="예매/결제">예매/결제</option>
									<option value="이벤트">이벤트</option>
								</select>
							</div>
							<div class="form-group">
								<label for="inqType" class="form-label">문의 종류</label>
								<select id="inqType" name="inquiryType" class="form-control">
									<option selected value="문의">문의</option>
									<option value="칭찬">칭찬</option>
									<option value="불만">불만</option>
									<option value="건의">건의</option>
								</select>
							</div>
							<div class="form-group form-group--full">
								<label class="form-label" for="inqTitle">문의 제목</label>
								<input class="form-control" id="inqTitle" name="inquiryTitle" placeholder="제목을 입력하세요">
							</div>
							<div class="form-group form-group--full">
								<label class="form-label" for="inqText">문의 내용</label>
								<textarea id="inqText" class="form-control" name="answerContent" placeholder="문의 내용을 입력하세요"></textarea>
							</div>
							<div class="form-group form-group--full">
								<label class="form-label" for="inqReception">답변 수신 방법</label>
								<select id="inqReception" name="receptionType" class="form-control">
									<option value=1>Email</option>
									<option value=2>SMS</option>
								</select>
							</div>
						</div>
						<div class="form-actions">
							<input type="hidden" name = "id" value="user01"> 
							<a href=InquiryProc.do  class="btn btn--outline">내 문의내역보기</a>
							<input type="submit"  class="btn btn--primary" value ="문의">
						</div>
					</section>
				</div>
			</div>
		</main>
		</form>
	</section>
</body>
	</html>