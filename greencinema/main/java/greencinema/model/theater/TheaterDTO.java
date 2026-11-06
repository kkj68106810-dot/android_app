package greencinema.model.theater;

public class TheaterDTO {
	
	private int theaterId;
	private String largeRegion;
	private String smallRegion;
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
