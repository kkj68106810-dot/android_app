<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="greencinema.model.movie.MovieDAO"%>
<%@ page import="greencinema.model.movie.MovieDTO"%>
<%@ page import="greencinema.model.notice.NoticeDAO"%>
<%@ page import="greencinema.model.notice.NoticeDTO"%>
<%@ page import="greencinema.model.inquiry.InquiryDAO"%>
<%@ page import="greencinema.model.inquiry.InquiryDTO"%>
<%@ page import="greencinema.model.faq.FaqDAO"%>
<%@ page import="greencinema.model.faq.FaqDTO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	if (!"admin".equals(session.getAttribute("id"))) {
		response.sendRedirect(request.getContextPath() + "/index.jsp?section=user/login.jsp");
		return;
	}

	ArrayList<MovieDTO> movies = new ArrayList<MovieDTO>();
	ArrayList<NoticeDTO> notices = new ArrayList<NoticeDTO>();
	ArrayList<InquiryDTO> inquiries = new ArrayList<InquiryDTO>();
	ArrayList<FaqDTO> faqs = new ArrayList<FaqDTO>();
	int noticeCount = 0;
	int inquiryCount = 0;
	int waitingCount = 0;
	int faqCount = 0;

	try {
		movies = new MovieDAO().getAllMovieList();
		if (movies == null) movies = new ArrayList<MovieDTO>();
	} catch (Exception e) {
		movies = new ArrayList<MovieDTO>();
	}
	try {
		NoticeDAO noticeDAO = new NoticeDAO();
		noticeCount = noticeDAO.NoticeAllCount();
		notices = noticeDAO.FaqAllList(0, 8);
		if (notices == null) notices = new ArrayList<NoticeDTO>();
	} catch (Exception e) {
		notices = new ArrayList<NoticeDTO>();
	}
	try {
		InquiryDAO inquiryDAO = new InquiryDAO();
		inquiryCount = inquiryDAO.AllCount();
		waitingCount = inquiryDAO.countWaiting();
		inquiries = inquiryDAO.getAllList(0, 12);
		if (inquiries == null) inquiries = new ArrayList<InquiryDTO>();
	} catch (Exception e) {
		inquiries = new ArrayList<InquiryDTO>();
	}
	try {
		FaqDAO faqDAO = new FaqDAO();
		faqCount = faqDAO.AllCount();
		faqs = faqDAO.FaqAllList(0, 8);
		if (faqs == null) faqs = new ArrayList<FaqDTO>();
	} catch (Exception e) {
		faqs = new ArrayList<FaqDTO>();
	}

	request.setAttribute("movies", movies);
	request.setAttribute("notices", notices);
	request.setAttribute("inquiries", inquiries);
	request.setAttribute("faqs", faqs);
	request.setAttribute("movieCount", movies.size());
	request.setAttribute("noticeCount", noticeCount);
	request.setAttribute("inquiryCount", inquiryCount);
	request.setAttribute("waitingCount", waitingCount);
	request.setAttribute("faqCount", faqCount);
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="theme-color" content="#070b09">
<title>하우스 데스크 | 그린시네마</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
</head>
<body class="admin-body">
	<div class="admin-shell">
		<aside class="admin-side">
			<a class="admin-logo" href="${pageContext.request.contextPath}/admin/admin.jsp">GREEN<span>CINEMA</span><small>HOUSE DESK</small></a>
			<p class="admin-side__kicker">CONTROL</p>
			<nav class="admin-nav" aria-label="관리자 메뉴">
				<a class="is-active" href="#overview">대시보드</a>
				<a href="#movies">상영작 <span class="admin-nav__count">${movieCount}</span></a>
				<a href="#notices">공지사항 <span class="admin-nav__count">${noticeCount}</span></a>
				<a href="#inquiries">1:1 문의 <span class="admin-nav__count">${waitingCount}</span></a>
				<a href="#faq">FAQ <span class="admin-nav__count">${faqCount}</span></a>
			</nav>
			<div class="admin-side__foot">
				<a href="${pageContext.request.contextPath}/UserLogout.do">로그아웃</a>
				<a href="${pageContext.request.contextPath}/index.jsp?section=user/login.jsp">다른 계정으로 로그인</a>
			</div>
		</aside>

		<div class="admin-main">
			<header class="admin-top" id="overview">
				<div>
					<p class="tiny-eyebrow">ADMINISTRATOR</p>
					<h1>하우스 데스크</h1>
					<p>상영작, 공지, 문의, FAQ를 한 자리에서 봅니다. 숫자는 MySQL <strong>greencinema</strong> 데이터베이스에서 바로 읽습니다.</p>
				</div>
				<div class="admin-who"><span class="admin-who__dot" aria-hidden="true"></span>admin · 관리자</div>
			</header>

			<div class="admin-marquee" aria-hidden="true">
				<p>NOW SHOWING · GREEN CINEMA · HOUSE LIGHTS DOWN · TICKETS · NOTICES · INQUIRIES · NOW SHOWING · GREEN CINEMA · HOUSE LIGHTS DOWN · TICKETS · NOTICES · INQUIRIES ·&nbsp;</p>
			</div>

			<c:if test="${param.saved == '1'}">
				<p class="admin-toast">문의 답변을 등록했습니다.</p>
			</c:if>
			<c:if test="${param.saved == '0'}">
				<p class="admin-toast admin-toast--bad">답변을 저장하지 못했습니다. 내용이 비어 있거나 DB 연결을 확인해 주세요.</p>
			</c:if>

			<section class="admin-stats" aria-label="운영 현황">
				<article class="admin-stat">
					<div class="admin-stat__label">상영작</div>
					<div class="admin-stat__value admin-stat__value--green">${movieCount}</div>
					<p class="admin-stat__hint">movie 테이블</p>
				</article>
				<article class="admin-stat">
					<div class="admin-stat__label">공지</div>
					<div class="admin-stat__value">${noticeCount}</div>
					<p class="admin-stat__hint">notice 테이블</p>
				</article>
				<article class="admin-stat">
					<div class="admin-stat__label">답변 대기</div>
					<div class="admin-stat__value admin-stat__value--green">${waitingCount}</div>
					<p class="admin-stat__hint">문의 ${inquiryCount}건 중</p>
				</article>
				<article class="admin-stat">
					<div class="admin-stat__label">FAQ</div>
					<div class="admin-stat__value">${faqCount}</div>
					<p class="admin-stat__hint">faq 테이블</p>
				</article>
			</section>

			<section class="admin-section" id="movies">
				<div class="admin-section__head">
					<div>
						<h2>상영작</h2>
						<p>회원 홈의 포스터와 같은 영화 목록입니다. 예매는 관리자 계정에서 열리지 않습니다.</p>
					</div>
				</div>
				<div class="admin-panel">
					<c:choose>
						<c:when test="${empty movies}">
							<p class="admin-empty">불러온 영화가 없습니다. DB가 꺼져 있거나 movie 테이블이 비어 있습니다.</p>
						</c:when>
						<c:otherwise>
							<div class="admin-movies">
								<c:forEach var="movie" items="${movies}">
									<article class="admin-movie">
										<img src="${pageContext.request.contextPath}/${movie.imgPath}" alt="">
										<h3 class="admin-movie__title"><c:out value="${movie.movieTitle}"/></h3>
										<div class="admin-movie__meta">
											<span class="age-badge ${movie.movieRate == 0 ? 'age-badge--all' : movie.movieRate == 12 ? 'age-badge--12' : movie.movieRate == 15 ? 'age-badge--15' : 'age-badge--19'}">
												${movie.movieRate == 0 ? 'ALL' : movie.movieRate}
											</span>
											<span>${movie.runTime}분</span>
										</div>
									</article>
								</c:forEach>
							</div>
						</c:otherwise>
					</c:choose>
				</div>
			</section>

			<section class="admin-section" id="notices">
				<div class="admin-section__head">
					<div>
						<h2>공지사항</h2>
						<p>최근 공지 8건입니다. 회원 화면의 공지 목록과 같은 테이블을 읽습니다.</p>
					</div>
				</div>
				<div class="admin-panel">
					<c:choose>
						<c:when test="${empty notices}">
							<p class="admin-empty">공지가 없습니다.</p>
						</c:when>
						<c:otherwise>
							<div class="admin-table-wrap">
								<table class="admin-table">
									<thead>
										<tr>
											<th>번호</th>
											<th>제목</th>
											<th>게시일</th>
											<th>조회</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="notice" items="${notices}">
											<tr>
												<td class="is-muted">${notice.noticeId}</td>
												<td>
													<c:out value="${notice.noticeTitle}"/>
													<div class="admin-answer"><c:out value="${notice.noticeText}"/></div>
												</td>
												<td class="is-muted"><c:out value="${notice.noticeDate}"/></td>
												<td>${notice.noticeHits}</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</c:otherwise>
					</c:choose>
				</div>
			</section>

			<section class="admin-section" id="inquiries">
				<div class="admin-section__head">
					<div>
						<h2>1:1 문의</h2>
						<p>답변 대기는 이 화면에서 바로 등록합니다. 저장되면 answer_status가 1이 됩니다.</p>
					</div>
				</div>
				<div class="admin-panel">
					<c:choose>
						<c:when test="${empty inquiries}">
							<p class="admin-empty">문의가 없습니다.</p>
						</c:when>
						<c:otherwise>
							<div class="admin-table-wrap">
								<table class="admin-table">
									<thead>
										<tr>
											<th>번호</th>
											<th>회원 / 분류</th>
											<th>내용</th>
											<th>답변</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="inq" items="${inquiries}">
											<tr>
												<td class="is-muted">${inq.inquiryId}</td>
												<td>
													<c:out value="${inq.userId}"/>
													<div class="admin-answer"><c:out value="${inq.inquirySort}"/> · <c:out value="${inq.inquiryType}"/></div>
												</td>
												<td>
													<strong><c:out value="${inq.inquiryTitle}"/></strong>
													<div class="admin-answer"><c:out value="${inq.inquiryContent}"/></div>
												</td>
												<td>
													<c:choose>
														<c:when test="${inq.answerStatus == 1}">
															<span class="status-badge status-badge--success">답변 완료</span>
															<p class="admin-answer"><c:out value="${inq.answerContent}"/></p>
														</c:when>
														<c:otherwise>
															<form class="admin-reply" action="${pageContext.request.contextPath}/AdminAnswerProc.do" method="post">
																<input type="hidden" name="inquiryId" value="${inq.inquiryId}">
																<label class="sr-only" for="answer-${inq.inquiryId}">답변 내용</label>
																<textarea id="answer-${inq.inquiryId}" name="answerContent" required placeholder="회원에게 보낼 답변"></textarea>
																<button class="btn btn--primary btn--small" type="submit">답변 등록</button>
															</form>
														</c:otherwise>
													</c:choose>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</c:otherwise>
					</c:choose>
				</div>
			</section>

			<section class="admin-section" id="faq">
				<div class="admin-section__head">
					<div>
						<h2>FAQ</h2>
						<p>자주 묻는 질문입니다. 회원 FAQ와 같은 데이터를 펼쳐서 확인합니다.</p>
					</div>
				</div>
				<div class="admin-faq">
					<c:choose>
						<c:when test="${empty faqs}">
							<div class="admin-panel"><p class="admin-empty">FAQ가 없습니다.</p></div>
						</c:when>
						<c:otherwise>
							<c:forEach var="faq" items="${faqs}">
								<details>
									<summary><c:out value="${faq.faqInquery}"/></summary>
									<p><c:out value="${faq.faqAnswer}"/></p>
								</details>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</div>
			</section>
		</div>
	</div>
</body>
</html>
