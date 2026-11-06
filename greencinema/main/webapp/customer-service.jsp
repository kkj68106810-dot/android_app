<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="그린시네마 포트폴리오 웹사이트 JSP/CSS 시안">
<meta name="theme-color" content="#11151a">
<title>1:1 문의 | 그린시네마</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/style.css">
</head>
<body>
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
				<div class="demo-note">
					<span class="demo-note__dot" aria-hidden="true"></span>문의 전송은 연결되지
					않은 UI 시안입니다. PPT 기준으로 1:1 문의는 회원만 작성할 수 있습니다.
				</div>
				<section class="inquiry-form">
					<div class="form-grid">
						<div class="form-group">
							<label for="inqSort" class="form-label">문의 분류</label><select
								id="inqSort" class="form-control"><option>영화관</option>
								<option>회원정보</option>
								<option selected>예매/결제</option>
								<option>이벤트</option></select>
						</div>
						<div class="form-group">
							<label for="inqType" class="form-label">문의 종류</label><select
								id="inqType" class="form-control"><option selected>문의</option>
								<option>칭찬</option>
								<option>불만</option>
								<option>건의</option></select>
						</div>
						<div class="form-group form-group--full">
							<label class="form-label" for="inqTitle">문의 제목</label><input
								class="form-control" id="inqTitle" placeholder="제목을 입력하세요">
						</div>
						<div class="form-group form-group--full">
							<label class="form-label" for="inqText">문의 내용</label>
							<textarea id="inqText" class="form-control"
								placeholder="문의 내용을 입력하세요"></textarea>
						</div>
						<div class="form-group form-group--full">
							<label class="form-label" for="inqReception">답변 수신 방법</label><select
								id="inqReception" class="form-control"><option>Email</option>
								<option>SMS</option></select>
						</div>
					</div>
					<div class="form-actions">
						<a href="${pageContext.request.contextPath}/mypage.jsp"
							class="btn btn--outline">내 문의내역 보기</a>
						<button class="btn btn--primary" type="button" disabled>문의
							등록 (UI 예시)</button>
					</div>
				</section>
			</div>
		</div>
	</main>
</body>
</html>