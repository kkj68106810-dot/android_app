<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*"%>
<%@ page import="java.sql.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<link rel="stylesheet" href="css/reserve.css">
<div class="page-content">

	<div class="container">

		<div class="page-heading">
			<p class="page-heading__eyebrow">TICKET BOOKING</p>
			<h1 class="page-heading__title">영화 · 극장 선택</h1>
		</div>

		<ol class="booking-steps" aria-label="예매 진행 단계">
			<li class="booking-step is-current"><span class="step-num">01</span> 영화·극장 선택</li>
			<li class="booking-step"><span class="step-num">02</span> 인원·좌석 선택</li>
			<li class="booking-step"><span class="step-num">03</span> 결제</li>
			<li class="booking-step"><span class="step-num">04</span> 예매 완료</li>
		</ol>

		<!-- 전체 레이아웃: 좌측 메인, 우측 컴팩트 요약 사이드바 -->
		<div class="booking-layout">

			<!-- [좌측 영역] 극장 선택 및 달력/상영시간표 -->
			<div class="booking-main">
				<!-- 01 극장 선택 (지역 대분류 / 소분류 / 영화) -->
				<section class="booking-panel">
					<h2 class="booking-panel__title">
						<span class="number-kicker">01</span> 극장 및 영화 선택
					</h2>
					<div class="theater-selection-layout">
						<!-- 지역 대분류 -->
						<div class="region-major-group">
							<c:forEach items="${rjlist}" var="theater" begin="0">
								<a href="selectMovieReserveProc.do?large_region=${theater.largeRegion }&movieId=${rjdto.movieId}"
								   class="${param.large_region eq theater.largeRegion ? 'is-selected' : ''}">
									${theater.largeRegion } 
								</a>
							</c:forEach>
						</div>
						<!-- 지역 소분류 및 극장 -->
						<div class="region-minor-group">
							<c:if test="${not empty rjlist02}">
								<c:forEach items="${rjlist02}" var="smallTheater" begin="0">
									<a href="selectMovieReserveProc.do?large_region=${param.large_region }&movieId=${rjdto.movieId}&smallRegion=${smallTheater.smallRegion }"
									   class="${param.smallRegion eq smallTheater.smallRegion ? 'is-selected' : ''}">
										${smallTheater.smallRegion } 
									</a>
								</c:forEach>
							</c:if>
						</div>
						<!-- 영화 선택 리스트 -->
						<div class="movie-select-group">
							<c:choose>
								<c:when test="${not empty rjlist04}">
									<c:forEach items="${rjlist04}" var="movieselect">
										<a href="selectMovieReserveProc.do?large_region=${param.large_region}&smallRegion=${param.smallRegion}&movieId=${movieselect.movieId}&selectedDate=${param.selectedDate}&scheduleTime=${param.scheduleTime}"
										   class="${param.movieId eq movieselect.movieId or rjdto.movieId eq movieselect.movieId ? 'is-selected' : ''}">
											<span class="${movieselect.movieRate==12?'yellow':movieselect.movieRate==15?'orange':movieselect.movieRate==19?'red':'green' }">
											<c:choose>
												<c:when test="${movieselect.movieRate==19}">19</c:when>
												<c:when test="${movieselect.movieRate==15}">15</c:when>
												<c:when test="${movieselect.movieRate==12}">12</c:when>
												<c:when test="${movieselect.movieRate==0}">ALL</c:when>
											</c:choose>									
											</span>
											&nbsp;${movieselect.movieTitle }
										</a>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<p style="font-size: 13px; color: #64706b; margin-top: 10px;">극장을 선택하면 해당 극장에서 상영중인 영화가 표시됩니다.</p>
								</c:otherwise>
							</c:choose>
						</div>
					</div>
				</section>

				<!-- 02 영화 및 상영일 선택 (달력 + 상영 시간표 좌우 2분할 배치) -->
				<section class="booking-panel">
					<h2 class="booking-panel__title">
						<span class="number-kicker">02</span> 상영일 및 상영시간 선택
					</h2>
					
					<div class="date-time-flex-container">
						<!-- [좌측] 날짜 선택 (달력) -->
						<div class="date-select-group">
							<div id="date-wrap">
								<button id="previousButton" onclick="previousButton()">&lt;</button>
								<div id="main_date"></div>
								<button id="nextButton" onclick="nextButton()">&gt;</button>
							</div>
							<!-- 달력 테이블 -->
							<div id="displayTable"></div>

							<script>
							let yearMonth = document.getElementById('main_date');
							let displayTable = document.getElementById('displayTable');
							let today = new Date(); 

							let selectedDateStr = "${param.selectedDate}"; 
							let arr = Array.from(Array(6), () => new Array(7));

							function calendar(){
							    let year = today.getFullYear();
							    let month = today.getMonth();
							    let lastDate = new Date(year, month + 1, 0); 
							    let lastDay = lastDate.getDate(); 
							    let startDate = new Date(year, month, 1); 
							    let startWeek = startDate.getDay(); 
							    let row = 0; 

							    for(let i = 0; i < arr.length; i++){
							        for(let j = 0; j < arr[i].length; j++){
							            arr[i][j] = "";
							        }
							    }

							    for(let i = 1; i <= lastDay; i++){
							        arr[row][startWeek] = i; 
							        if(startWeek < 6){ 
							            startWeek++;
							        } else { 
							            row++;
							            startWeek = 0; 
							        }
							    }
							}

							function display(){
							    calendar();
							    let year = today.getFullYear();
							    let month = today.getMonth();

							    let tb = "<table class='cal_table'>";
							    tb += "<thead><tr>";
							    let weeks = ["SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"];
							    for(let w of weeks) {
							        tb += "<th>" + w + "</th>";
							    }
							    tb += "</tr></thead><tbody>";

							    for(let i = 0; i < arr.length; i++){
							        let isEmptyRow = arr[i].every(cell => cell === "");
							        if(isEmptyRow && i >= 4) continue; 

							        tb += "<tr>";
							        for(let j = 0; j < arr[i].length; j++){
							            let dayVal = arr[i][j] === "" ? "" : arr[i][j];
							            if(dayVal !== "") {
							                let mFormatted = (month + 1) < 10 ? "0" + (month + 1) : (month + 1);
							                let dFormatted = dayVal < 10 ? "0" + dayVal : dayVal;
							                let fullDateVal = year + "-" + mFormatted + "-" + dFormatted;
							                
							                let isSelectedClass = (fullDateVal === selectedDateStr) ? " calendar-day is-selected" : " calendar-day";
							                
							                tb += "<td><span class='" + isSelectedClass + "' data-year='" + year + "' data-month='" + (month + 1) + "' data-day='" + dayVal + "'>" + dayVal + "</span></td>";
							            } else {
							                tb += "<td></td>";
							            }
							        }
							        tb += "</tr>";
							    }
							    tb += "</tbody></table>";

							    yearMonth.innerHTML = year + "년 " + (month + 1) + "월";
							    displayTable.innerHTML = tb;
							    addDateClickEvent();
							}

							display();

							function previousButton(){
							    today = new Date(today.getFullYear(), today.getMonth() - 1, 1);
							    display();
							}
							function nextButton(){
							    today = new Date(today.getFullYear(), today.getMonth() + 1, 1);
							    display();
							}

							function addDateClickEvent() {
							    let dayCells = document.querySelectorAll('.calendar-day');
							    dayCells.forEach(cell => {
							        cell.addEventListener('click', function() {
							            let y = this.getAttribute('data-year');
							            let m = this.getAttribute('data-month');
							            let d = this.getAttribute('data-day');

							            let formattedMonth = m < 10 ? "0" + m : m;
							            let formattedDay = d < 10 ? "0" + d : d;

							            selectedDateStr = y + "-" + formattedMonth + "-" + formattedDay; 
							            
							            location.href = "selectMovieReserveProc.do?large_region=${param.large_region}&movieId=${rjdto.movieId}&smallRegion=${param.smallRegion}&selectedDate=" + selectedDateStr + "&scheduleTime=${param.scheduleTime}#schedule-area";
							        });
							    });
							}
						</script>
						</div>

						<!-- [우측] 상영 시간표 영역 -->
						<div id="schedule-area" class="schedule-output-area">
							<h3>상영 시간표</h3>
							<c:choose>
								<c:when test="${not empty rjlist03}">
									<div class="schedule-grid-wrap">
										<c:forEach items="${rjlist03}" var="schedule" begin="0">
											<a href="selectMovieReserveProc.do?large_region=${param.large_region}&movieId=${rjdto.movieId}&smallRegion=${param.smallRegion}&selectedDate=${param.selectedDate}&scheduleTime=${schedule.scheduleStarttime}&screenId=${schedule.screenId}#schedule-area"
											   class="schedule-card ${param.scheduleTime eq schedule.scheduleStarttime ? 'is-selected' : ''}"> 
												<span class="schedule-time-text">${fn:substring(schedule.scheduleStarttime, 11, 16)}</span>
												<span class="schedule-info-text">${schedule.totalSeat}석 / ${schedule.screenId}관</span>
											</a>
										</c:forEach>
									</div>
								</c:when>
								<c:otherwise>
									<p style="font-size: 13px; color: #64706b; margin-top: 10px;">날짜를 선택하시면 해당 상영 시간표가 표시됩니다.</p>
								</c:otherwise>
							</c:choose>
						</div>

					</div>
				</section>
			</div>

			<!-- [우측 영역] 예매 정보 요약 -->
			<aside class="booking-summary">
				<div class="summary-kicker">TICKET BOOKING</div>
				<h2 class="booking-summary__title">예매 정보</h2>
				<div class="summary-movie">
					<c:if test="${not empty rjdto.imgPath }">
						<img src="./${rjdto.imgPath }" alt="선택 영화">
					</c:if>
					<div>
						<strong>${rjdto.movieTitle }</strong>
					</div>
				</div>

				<div class="booking-summary__row">
					<span class="booking-summary__label">극장</span>
					<c:if test="${not empty param.smallRegion }">
						<strong class="booking-summary__value">그린 시네마 ${param.smallRegion }</strong>
					</c:if>
					<c:if test="${empty param.smallRegion}">
						<strong class="booking-summary__value">미선택</strong>
					</c:if>
				</div>

				<div class="booking-summary__row">
					<span class="booking-summary__label">상영일</span> 
					<strong class="booking-summary__value" id="summary-date">
						<c:choose>
							<c:when test="${not empty param.selectedDate}">${param.selectedDate}</c:when>
							<c:otherwise>미선택</c:otherwise>
						</c:choose>
					</strong>
				</div>

				<div class="booking-summary__row">
					<span class="booking-summary__label">상영시간</span>
					<c:choose>
						<c:when test="${not empty param.scheduleTime}">
							<strong class="booking-summary__value">${fn:substring(param.scheduleTime, 11, 16)}</strong>
						</c:when>
						<c:otherwise>
							<strong class="booking-summary__value">미선택</strong>
						</c:otherwise>
					</c:choose>
				</div>

				<div class="booking-summary__total">
					<span>선택 영화</span>
					<c:if test="${not empty rjdto.movieTitle }">
						<span class="booking-summary__amount" style="font-size: 18px;">${rjdto.movieTitle }</span>
					</c:if>
				</div>

				<p class="summary-footnote">모든 항목을 선택한 후 다음 단계로 이동하세요.</p>

				<a class="btn btn--primary btn--block btn--large" id="seatMoveBtn"
					href="index.jsp?section=reserve/reserve-seat.jsp&large_region=${param.large_region }&movieId=${rjdto.movieId}&smallRegion=${param.smallRegion }&selectedDate=${param.selectedDate}&scheduleTime=${param.scheduleTime}">인원·좌석 선택 ↗</a>
			</aside>

		</div>
	</div>
</div>