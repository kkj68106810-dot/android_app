<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


  <!-- ================= MAIN CONTENT ================= -->
  <main id="main">
    <div class="page-content">
      <div class="container">

        <!-- 페이지 헤딩 -->
        <div class="page-heading">
          <p class="page-heading__eyebrow">MY PAGE</p>
          <h1 class="page-heading__title">마이페이지</h1>
          <p class="page-heading__description">예매 내역과 문의 내역을 관리하는 화면입니다.</p>
        </div>

        <!-- 마이페이지 레이아웃 -->
        <div class="mypage-layout">
          <!-- 좌측 사이드 메뉴 -->
          <nav class="side-nav" aria-label="마이페이지 메뉴">
            <a class="is-active" href="#reservations">예매내역</a>
            <a href="#my-inquiries">1:1문의내역</a>
            <a href="inquiry.html">문의 작성</a>
          </nav>

          <!-- 우측 본문 영역 -->
          <div>
            <!-- 예매 내역 섹션 -->
            <section id="reservations">
              <div class="section-header">
                <h2 class="section-title">예매 내역</h2>
                <span class="chip chip--green">1건</span>
              </div>

              <div class="reservation-list">
                <article class="reservation-card">
                  <img class="reservation-card__poster" src="assets/posters/greencinema_poster_01.png" alt="다시, 봄 포스터">
                  <div>
                    <h3 class="reservation-card__title">다시, 봄</h3>
                    <p class="reservation-card__detail">그린시네마 홍대 · 1관</p>
                    <p class="reservation-card__detail">2026.10.29 (목) 18:10</p>
                    <p class="reservation-card__detail">성인 1명 · C5</p>
                    <p class="reservation-card__detail">14,000원 · 신용카드 결제(예시)</p>
                  </div>
                  <div class="reservation-card__actions">
                    <span class="status-badge status-badge--success">예매 완료</span>
                    <span class="form-help">취소 UI 준비 중</span>
                  </div>
                </article>
              </div>
            </section>

            <!-- 1:1 문의내역 섹션 -->
            <section id="my-inquiries" class="stack-section">
              <div class="section-header">
                <h2 class="section-title">1:1 문의내역</h2>
                <a href="inquiry.html" class="btn btn--outline btn--small">문의 작성</a>
              </div>

              <div class="table-scroll">
                <table class="data-table">
                  <thead>
                    <tr>
                      <th>문의번호</th>
                      <th>분류</th>
                      <th>제목</th>
                      <th>답변상태</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr>
                      <td>101</td>
                      <td>예매 / 결제</td>
                      <td>예매 취소 문의 (예시)</td>
                      <td><span class="status-badge status-badge--pending">답변 대기</span></td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </section>
          </div>
        </div>

      </div>
    </div>
  </main>

  <!-- ================= FOOTER ================= -->
