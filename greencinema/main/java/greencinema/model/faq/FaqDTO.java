package greencinema.model.faq;

public class FaqDTO {
	private int faqId;
	private int faqType;
	private String faqInquery;
	private String faqAnswer;
	public int getFaqId() {
		return faqId;
	}
	public void setFaqId(int faqId) {
		this.faqId = faqId;
	}
	public int getFaqType() {
		return faqType;
	}
	public void setFaqType(int faqType) {
		this.faqType = faqType;
	}
	public String getFaqInquery() {
		return faqInquery;
	}
	public void setFaqInquery(String faqInquery) {
		this.faqInquery = faqInquery;
	}
	public String getFaqAnswer() {
		return faqAnswer;
	}
	public void setFaqAnswer(String faqAnswer) {
		this.faqAnswer = faqAnswer;
	}


}
