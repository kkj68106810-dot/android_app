package greencinema.model.inquiry;

public class InquiryDTO {
	private int inquiryId;
	private String userId;
	private String inquirySort;
	private String inquiryType;
	private String inquiryTitle;
	private String inquiryContent;
	private int receptionType;
	private int answerStatus;
	private String answerContent;
	private String inquiryDate;
	
	
	
	public String getInquiryDate() {
		return inquiryDate;
	}
	public void setInquiryDate(String inquiryDate) {
		this.inquiryDate = inquiryDate;
	}
	public int getInquiryId() {
		return inquiryId;
	}
	public void setInquiryId(int inquiryId) {
		this.inquiryId = inquiryId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getInquirySort() {
		return inquirySort;
	}
	public void setInquirySort(String inquirySort) {
		this.inquirySort = inquirySort;
	}
	public String getInquiryType() {
		return inquiryType;
	}
	public void setInquiryType(String inquiryType) {
		this.inquiryType = inquiryType;
	}
	public String getInquiryTitle() {
		return inquiryTitle;
	}
	public void setInquiryTitle(String inquiryTitle) {
		this.inquiryTitle = inquiryTitle;
	}
	public String getInquiryContent() {
		return inquiryContent;
	}
	public void setInquiryContent(String inquiryContent) {
		this.inquiryContent = inquiryContent;
	}
	public int getReceptionType() {
		return receptionType;
	}
	public void setReceptionType(int receptionType) {
		this.receptionType = receptionType;
	}
	public int getAnswerStatus() {
		return answerStatus;
	}
	public void setAnswerStatus(int answerStatus) {
		this.answerStatus = answerStatus;
	}
	public String getAnswerContent() {
		return answerContent;
	}
	public void setAnswerContent(String answerContent) {
		this.answerContent = answerContent;
	}
	

}
