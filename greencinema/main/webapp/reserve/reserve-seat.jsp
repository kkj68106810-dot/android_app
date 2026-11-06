<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*"%>
<%@ page import="java.sql.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link rel="stylesheet" href="css/reserve.css">

<div class="page-content">
	<div class="container">

		<div class="page-heading">
			<p class="page-heading__eyebrow">TICKET BOOKING</p>
			<h1 class="page-heading__title">인원 · 좌석 선택</h1>
		</div>

		<ol class="booking-steps" aria-label="예매 진행 단계">
			<li class="booking-step is-complete"><span class="step-num">01</span> 영화·극장 선택</li>
			<li class="booking-step is-current"><span class="step-num">02</span> 인원·좌석 선택</li>
			<li class="booking-step"><span class="step-num">03</span> 결제</li>
			<li class="booking-step"><span class="step-num">04</span> 예매 완료</li>
		</ol>

		<div class="booking-layout">
			<div class="booking-main">

				<section class="booking-panel">
					<div class="booking-panel__title">
						<h3 style="margin: 0; font-size: 19px; font-weight: 850;">관람인원 선택</h3>
						<button class="reset-btn" onclick="reset()">&#8635; 초기화</button>
					</div>
					<div class="ticket-types">
						<!-- 성인 영역 -->
						<div class="ticket-counter">
							<div>
								<div class="ticket-row" id="adultwrap">
									<span>성인</span>
									<div class="counter-btns">
										<button id="adultminus" onclick="adultminus()">-</button>
										<button id="adult">0</button>
										<button id="adultplus" onclick="adultplus()">+</button>
									</div>
								</div>
							</div>
						</div>

						<!-- 청소년 영역 -->
						<div class="ticket-counter">
							<div>
								<div class="ticket-row" id="prewrap">
									<span>청소년</span>
									<div class="counter-btns">
										<button id="preminus" onclick="preminus()">-</button>
										<button id="pre">0</button>
										<button id="preplus" onclick="preplus()">+</button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</section>

				<section class="booking-panel">
					<h2 class="booking-panel__title">
						<span class="number-kicker">02</span> 좌석 선택
					</h2>
					<div class="seat-layout">
						<div class="screen-indicator">SCREEN</div>
						<div class="seat-grid" role="group" aria-label="1관 좌석 선택 A열부터 J열까지">
							<span class="seat-choice">
								<div class="seat" id="seatwrap"></div>
							</span>
						</div>
						<div class="seat-legend">
							<div><img src="img/seatStatus3.png" alt="선택" onerror="this.style.display='none'">선택</div>
							<div><img src="img/seatStatus1.png" alt="예매완료" onerror="this.style.display='none'">예매완료</div>
							<div><img src="img/seatStatus2.png" alt="선택불가" onerror="this.style.display='none'">선택불가</div>
							<div><img src="img/seatStatus4.png" alt="일반" onerror="this.style.display='none'">일반</div>
						</div>
					</div>
				</section>

			</div>

			<aside class="booking-summary">
				<div class="summary-kicker">SEAT SELECTION</div>
				<div class="movietitle"><span class="age">19</span>타짜: 벨제붑의 노래</div>
				<div class="dimension">2D</div>
				<div class="movie-info">
					<div class="movie-info-child">
						<div>검단</div>
						<div>컴포트1관</div>
						<div>2026.10.02(금)</div>
						<div class="movie-time">
							21:00~23:19 &nbsp;
							<button class="time-change" style="background: none; border: none; cursor: pointer; font-weight: bold;">v</button>
						</div>
					</div>
					<img src="/images/tazza.png" alt="타짜" onerror="this.style.display='none'">
				</div>
				<div class="choice-container">
					<div class="choicetype">
						<div><img src="img/seatStatus3.png" alt="선택" onerror="this.style.display='none'">선택</div>
						<div><img src="img/seatStatus1.png" alt="예매완료" onerror="this.style.display='none'">예매완료</div>
						<div><img src="img/seatStatus2.png" alt="선택불가" onerror="this.style.display='none'">선택불가</div>
						<div><img src="img/seatStatus4.png" alt="일반" onerror="this.style.display='none'">일반</div>
					</div>
					<div class="choiceseat">
						<div class="choice-child">선택좌석</div>
						<div class="seatchild" id="seatnumber"></div>
					</div>
				</div>
				<div class="personnelinfo" id="personnel"></div>
				<div class="Reservationinfo" id="Reservation"></div>
				<div class="nextback">
					<button class="back" onclick="back()">이전</button>
					<button class="next">다음</button>
				</div>
			</aside>
		</div>

	</div>
</div>

<script>
    let seatwrap = document.getElementById('seatwrap');
    let seatnumber = document.getElementById('seatnumber');
    let adult = document.getElementById('adult');
    let pre = document.getElementById('pre');
    
    let adultcount = 0;
    let precount = 0;
    let maxcount = 0;

    let arry = Array.from(Array(10), () => new Array(10).fill(0));
    let arryeng = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'];
    let seatresult = new Array(8).fill('-');

    function render() {
        let totalPeople = adultcount + precount;

        // 알파벳을 위가 아니라 좌측 열에 배치하도록 테이블 구조 변경
        let result = '<table class="seattable">';
        for (let i = 0; i < arry.length; i++) { // i: 행 (0~9)
            result += '<tr>';
            // 좌측 행 레이블 (A~J) 추가
            result += '<td class="seat-row-label">' + arryeng[i] + '</td>';
            
            for (let j = 0; j < arry[i].length; j++) { // j: 열 (0~9)
                let seatNumVal = j + 1;
                let seatName = arryeng[i] + seatNumVal;
                let isActive = seatresult.includes(seatName) ? ' active' : '';
                
                result += '<td><div class="seatnum' + isActive + '" onclick="choicerender(' + i + ', ' + j + ')">' + seatNumVal + '</div></td>';
            }
            result += '</tr>';
        }
        result += '</table>';

        seatwrap.innerHTML = result;
    }

    function seatrender() {
        let result = '<ul>';
        let count = 0;
        for (let i = 0; i < seatresult.length; i++) {
            if (seatresult[i] !== '-') {
                result += '<li>' + seatresult[i] + '</li>';
                count++;
            }
        }
        if (count === 0) {
            result += '<li style="color: #888; list-style: none; font-size: 13px;">선택된 좌석이 없습니다.</li>';
        }
        result += '</ul>';
        seatnumber.innerHTML = result;
    }

    function choicerender(index, index02) {
        let totalPeople = adultcount + precount;
        if (totalPeople === 0) {
            alert("관람 인원을 먼저 선택해주세요.");
            return;
        }
        let seatName = arryeng[index] + (index02 + 1);
        let existingIndex = seatresult.indexOf(seatName);
        if (existingIndex !== -1) {
            seatresult[existingIndex] = '-';
            maxcount--;
            render();
            seatrender();
            return;
        }
        if (maxcount >= totalPeople) {
            alert("선택할 수 있는 인원수를 초과했습니다.");
            return;
        }
        let targetIndex = seatresult.indexOf('-');
        if (targetIndex !== -1) {
            seatresult[targetIndex] = seatName;
            maxcount++;
            render();
            seatrender();
        }
    }

    function adultplus() {
        if (adultcount + precount < 8) {
            adultcount++;
            adult.innerText = adultcount;
            render();
        } else {
            alert("8명까지만 선택 가능합니다.");
        }
    }

    function adultminus() {
        if (adultcount > 0) {
            adultcount--;
            adult.innerText = adultcount;
            checkAndResetOverflow();
            render();
        }
    }

    function preplus() {
        if (adultcount + precount < 8) {
            precount++;
            pre.innerText = precount;
            render();
        } else {
            alert("8명까지만 선택 가능합니다.");
        }
    }

    function preminus() {
        if (precount > 0) {
            precount--;
            pre.innerText = precount;
            checkAndResetOverflow();
            render();
        }
    }

    function checkAndResetOverflow() {
        let totalPeople = adultcount + precount;
        if (maxcount > totalPeople) {
            for (let i = totalPeople; i < seatresult.length; i++) {
                seatresult[i] = '-';
            }
            maxcount = totalPeople;
            seatrender();
        }
    }

    function reset() {
        adultcount = 0;
        precount = 0;
        maxcount = 0;
        adult.innerText = 0;
        pre.innerText = 0;
        seatresult = new Array(8).fill('-');
        render();
        seatrender();
    }
	
    function back(){
    	
    	if(confirm("이전 페이지로 이동하시겠습니까?")){
    	history.go(-1);
    	}
    }
    render();
    seatrender();
</script>