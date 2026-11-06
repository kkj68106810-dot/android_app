<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

  <!-- ================= MAIN CONTENT ================= -->
  <main id="main">
    <section class="auth-page">
      <div class="auth-card">
        <p class="tiny-eyebrow" style="text-align:center">WELCOME BACK</p>
        <h1 class="auth-card__title">로그인</h1>
        <p class="auth-card__subtitle">그린시네마에 오신 것을 환영합니다.</p>

        <!-- 로그인 폼 -->
        <form action="UserLoginProc.do" method="post">
          <div class="form-group">
            <label class="form-label" for="userId">아이디</label>
            <input class="form-control" id="userId" name="userId" placeholder="아이디를 입력하세요" >
          </div>

          <div class="form-group">
            <label class="form-label" for="userPw">비밀번호</label>
            <input class="form-control" id="userPw" name="userPw" type="password" placeholder="비밀번호를 입력하세요">
          </div>


          <button class="btn btn--primary btn--block btn--large" style="margin-top:20px" type="submit">
            로그인
          </button>
        </form>

        <div class="auth-links">        
          <a href="index.jsp?section=user/user-join.jsp">회원가입</a>
        </div>
      </div>
    </section>
  </main>
  <!-- ================= FOOTER ================= -->