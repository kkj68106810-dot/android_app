package greencinema.model.reservejoin;

import java.sql.Date;
public class ReservejoinDTO {
	private int reserveId;
	private String userId;
	private int adultCount;
	private int teensCount;
	private int totalPrice;
	private String reserveDate;
	private String cancelDate;
	private int paymentId;
	private int paymentMethod;
	private String paymentDatetime;
	private int seatId;
	private int screenId;
	private String seatNum;
	private int scheduleId;
	private String scheduleStarttime;
	private String scheduleEndtime;		
	private int movieId;
	private String movieTitle;
	private int movieRate;
	private String imgPath;
	private String movieContent;
	private int runTime;
	private int theaterId;
	private String largeRegion;
	private String smallRegion;
	private int theatherId;
	private int totalSeat;
	private int screenNum;

	public int getTheatherId() {
		return theatherId;
	}
	public void setTheatherId(int theatherId) {
		this.theatherId = theatherId;
	}
	public int getTotalSeat() {
		return totalSeat;
	}
	public void setTotalSeat(int totalSeat) {
		this.totalSeat = totalSeat;
	}
	public int getScreenNum() {
		return screenNum;
	}
	public void setScreenNum(int screenNum) {
		this.screenNum = screenNum;
	}
	
	public int getReserveId() {
		return reserveId;
	}
	public void setReserveId(int reserveId) {
		this.reserveId = reserveId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getAdultCount() {
		return adultCount;
	}
	public void setAdultCount(int adultCount) {
		this.adultCount = adultCount;
	}
	public int getTeensCount() {
		return teensCount;
	}
	public void setTeensCount(int teensCount) {
		this.teensCount = teensCount;
	}
	public int getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getReserveDate() {
		return reserveDate;
	}
	public void setReserveDate(String reserveDate) {
		this.reserveDate = reserveDate;
	}
	public String getCancelDate() {
		return cancelDate;
	}
	public void setCancelDate(String cancelDate) {
		this.cancelDate = cancelDate;
	}
	public int getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}
	public int getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(int paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public String getPaymentDatetime() {
		return paymentDatetime;
	}
	public void setPaymentDatetime(String paymentDatetime) {
		this.paymentDatetime = paymentDatetime;
	}
	public int getSeatId() {
		return seatId;
	}
	public void setSeatId(int seatId) {
		this.seatId = seatId;
	}
	public int getScreenId() {
		return screenId;
	}
	public void setScreenId(int screenId) {
		this.screenId = screenId;
	}
	public String getSeatNum() {
		return seatNum;
	}
	public void setSeatNum(String seatNum) {
		this.seatNum = seatNum;
	}
	public int getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(int scheduleId) {
		this.scheduleId = scheduleId;
	}
	public String getScheduleStarttime() {
		return scheduleStarttime;
	}
	public void setScheduleStarttime(String scheduleStarttime) {
		this.scheduleStarttime = scheduleStarttime;
	}
	public String getScheduleEndtime() {
		return scheduleEndtime;
	}
	public void setScheduleEndtime(String scheduleEndtime) {
		this.scheduleEndtime = scheduleEndtime;
	}
	public int getMovieId() {
		return movieId;
	}
	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}
	public String getMovieTitle() {
		return movieTitle;
	}
	public void setMovieTitle(String movieTitle) {
		this.movieTitle = movieTitle;
	}
	public int getMovieRate() {
		return movieRate;
	}
	public void setMovieRate(int movieRate) {
		this.movieRate = movieRate;
	}
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	public String getMovieContent() {
		return movieContent;
	}
	public void setMovieContent(String movieContent) {
		this.movieContent = movieContent;
	}
	public int getRunTime() {
		return runTime;
	}
	public void setRunTime(int runTime) {
		this.runTime = runTime;
	}
	public int getTheaterId() {
		return theaterId;
	}
	public void setTheaterId(int theaterId) {
		this.theaterId = theaterId;
	}
	public String getLargeRegion() {
		return largeRegion;
	}
	public void setLargeRegion(String largeRegion) {
		this.largeRegion = largeRegion;
	}
	public String getSmallRegion() {
		return smallRegion;
	}
	public void setSmallRegion(String smallRegion) {
		this.smallRegion = smallRegion;
	}
	

}
