<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import = "java.util.*"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<link rel="stylesheet" href="css/faq.css">
    <section>
	<h1>FAQ</h1>
    <form action="seachFaqProc.do" method="get">
    	<select id="type_i" name="type">
    		<option>선택</option>
    		<option value="faq_inquery"  ${type=='faq_inquery'?'selected':''}>제목로 검색</option>
    		<option value="faq_answer"  ${type=='faq_answer'?'selected':''}>내용으로 검색</option>
    	</select> 
		<input name="content" id="ch" type="text" onchange="change()" placeholder="검색어를 입력해주세요" >
    	<input type="submit" value="검색">
    	<input type="button" value ="전체보기" class="btn-all"
				onclick="location.href='FaqProc.do'">
    </form>
	<table border="1">
		<tr>
			<th>글번호</th>
			<th>질문</th>
		</tr>
				<c:set var="number" value="${number}"/>
			<c:forEach var= "fList" items="${fList}">
					<tr>
						<td>${number }</td>
						<td>${fList.faqInquery }</td>
					</tr>
				<c:set var="number" value="${number-1}"/>
			</c:forEach>
	</table>
	<%-- 페이징 부분 --%>
	<div class ="paging">
	<%-- [이전] 링크 만들지 파악 --%>
		<c:if test="${startpage > pageBlock }">
			<a href="FaqProc.do?pageNum=${startpage-pageBlock}">이전</a>
		</c:if>
	<%-- 페이징 --%>
		<c:forEach var="i" begin="${startpage}" end ="${endpage}">
			<a href="FaqProc.do?pageNum=${i }">${i }</a>
		</c:forEach>	
		
		<%-- [다음] 링크 만들지 파악 --%>
		<c:if test="${endpage < pageCount }">
			<a href ="FaqProc.do?pageNum=${startpage+pageBlock}">다음</a>
		</c:if>
	</div>
	</section>
	