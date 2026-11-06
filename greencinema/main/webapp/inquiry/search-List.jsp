<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import = "java.util.*"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<link rel="stylesheet" href="css/search-list.css">
    <section>
   <h1>1:1 문의사항</h1>
    <form action="searchInquiryProc.do" method="get">
    	<select id="type_i" name="type">
    		<option>선택</option>
    		<option value="inquiry_type"  ${type=='inquiry_type'?'selected':''}>문의종류로 검색</option>
    		<option value="inquiry_Sort"  ${type=='inquiry_Sort'?'selected':''}>문의분류로 검색</option>
    		<option value="inquiry_title"  ${type=='inquiry_title'?'selected':''}>제목으로 검색</option>
    		<option value="answer_status"  ${type=='answer_status'?'selected':''}>답변상태로 검색</option>
    	</select>
		<input name="content" id="ch" type="text" onchange="change()" placeholder="검색어를 입력해주세요" value="${content }">
    	<input type="submit" value="검색">
    	<input type="button" value ="전체보기" class="btn-all"
				onclick="location.href='InquiryProc.do'">
    </form>
	<table border="1">
		<tr>
			<th>글번호</th>
			<th>문의종류</th>
			<th>문의분류</th>
			<th>제목</th>
			<th>답변상태</th>
		</tr>
			<c:choose>
			<c:when test="${empty sList}">
				<tr>
					<td colspan="5" align="center"><p>검색결과가 없습니다.</p></td>
				</tr>
			</c:when>
			<c:otherwise>
				<c:forEach var="sList" items="${sList}">
					<tr>
						<td>${number }</td>
						<td>${sList.inquiryType }</td>
						<td>${sList.inquirySort }</td>
						<td>${sList.inquiryTitle }</td>
						<td>
						<c:choose >
							<c:when test="${sList.answerStatus eq 1 }">
								<a href="#">답변 완료</a>
							</c:when>
							<c:otherwise>
								<p>답변 대기</p>
							</c:otherwise>
						</c:choose>
						</td>
					</tr>
				<c:set var="number" value="${number-1}"/>
				</c:forEach>
				</c:otherwise>
			</c:choose>
	</table>
	<%-- 페이징 부분 --%>
	<div class ="paging">
	<%-- [이전] 링크 만들지 파악 --%>
		<c:if test="${startpage > pageBlock }">
			<a href="searchInquiryProc.do?pageNum=${startpage-pageBlock}">이전</a>
		</c:if>
	<%-- 페이징 --%>
		<c:forEach var="i" begin="${startpage}" end ="${endpage}">
			<a href="searchInquiryProc.do?pageNum=${i }&content=${content }&type=${type }">${i }</a>
		</c:forEach>	
		
		<%-- [다음] 링크 만들지 파악 --%>
		<c:if test="${endpage < pageCount }">
			<a href ="searchInquiryProc.do?pageNum=${startpage+pageBlock}">다음</a>
		</c:if>
	</div>
	</section>
	