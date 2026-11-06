package greencinema.model.schedule;

import java.util.Date;

public class ScheduleDTO {

	private int scheduleId;
	private int movieId;
	private int screenId;
	private Date scheduleStarttime;
	private Date scheduleEndtime;
	public int getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(int scheduleId) {
		this.scheduleId = scheduleId;
	}
	public int getMovieId() {
		return movieId;
	}
	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}
	public int getScreenId() {
		return screenId;
	}
	public void setScreenId(int screenId) {
		this.screenId = screenId;
	}
	public Date getScheduleStarttime() {
		return scheduleStarttime;
	}
	public void setScheduleStarttime(Date scheduleStarttime) {
		this.scheduleStarttime = scheduleStarttime;
	}
	public Date getScheduleEndtime() {
		return scheduleEndtime;
	}
	public void setScheduleEndtime(Date scheduleEndtime) {
		this.scheduleEndtime = scheduleEndtime;
	}	
	
}
