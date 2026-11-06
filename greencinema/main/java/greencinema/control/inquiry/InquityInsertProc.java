package greencinema.control.inquiry;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import greencinema.model.inquiry.InquiryDAO;
import greencinema.model.inquiry.InquiryDTO;


@WebServlet("/InquityInsertProc.do")
public class InquityInsertProc extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		rePro(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	rePro(request, response);
	}
	protected void rePro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		InquiryDTO idto = new InquiryDTO();
		
		idto.setUserId("user1");
		idto.setInquirySort(request.getParameter("inquirySort"));
		idto.setInquiryType(request.getParameter("inquiryType"));
		idto.setInquiryTitle(request.getParameter("inquiryTitle"));
		idto.setAnswerContent(request.getParameter("answerContent"));//잘못됨
		idto.setReceptionType(Integer.parseInt(request.getParameter("receptionType")));
		
		InquiryDAO idao = new InquiryDAO();
		idao.insert(idto);
		 System.out.println(idao.insert(idto));

	
	}
}
