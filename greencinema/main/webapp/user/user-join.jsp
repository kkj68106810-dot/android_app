<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    <main id="main">
      <section class="auth-page">
        <div class="auth-card auth-card--wide">
          <p class="tiny-eyebrow" style="text-align:center">JOIN GREEN CINEMA</p>
          <h1 class="auth-card__title">회원가입</h1>
          <p class="auth-card__subtitle">회원 가입에 필요한 정보를 입력하세요.</p>
          
          <div class="demo-note">
            <span class="demo-note__dot" aria-hidden="true"></span>회원가입
          </div>

          <form action="UserJoinProc.do" method="post">
            <div class="form-grid">
              <div class="form-group">
                <label class="form-label" for="userId">아이디 <span class="form-label__required">*</span></label>
                <input id="userId" class="form-control" name="userId"  placeholder="아이디를 입력하세요.">
              </div>
              <div class="form-group">
                <label class="form-label" for="userName">이름 <span class="form-label__required">*</span></label>
                <input id="userName" class="form-control" name="userName" placeholder="이름">
              </div>
              <div class="form-group">
                <label class="form-label" for="userPw">비밀번호 <span class="form-label__required">*</span></label>
                <input type="password" id="userPw" class="form-control" name="userPw" placeholder="비밀번호">
              </div>
              <div class="form-group">
                <label class="form-label" for="userPwChk">비밀번호 확인 <span class="form-label__required">*</span></label>
                <input type="password" id="userPwChk" class="form-control" name="userPwChk" placeholder="비밀번호 확인">
              </div>
              <div class="form-group">
                <label class="form-label" for="userBirth">생년월일</label>
                <input type="date" id="userBirth" class="form-control" name="userBirth">
              </div>
              <div class="form-group">
                <label class="form-label" for="userPhone">휴대폰번호</label>
                <input type="tel" id="userPhone" class="form-control" name="userPhone" placeholder="010-0000-0000">
              </div>
              <div class="form-group form-group--full">
                <label class="form-label" for="userEmail">이메일</label>
                <input type="email" id="userEmail" class="form-control" name="userEmail" placeholder="example@email.com">
              </div>
            </div>
            <button type="submit" class="btn btn--primary btn--large btn--block">회원가입</button>
          </form>

          <div class="auth-links">
            <a href="index.jsp?section=user/login.jsp">이미 계정이 있나요? 로그인</a>
          </div>
        </div>
      </section>
    </main>    