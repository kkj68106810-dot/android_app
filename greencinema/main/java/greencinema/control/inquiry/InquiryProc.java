package greencinema.control.inquiry;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import greencinema.model.inquiry.InquiryDAO;
import greencinema.model.inquiry.InquiryDTO;


@WebServlet("/InquiryProc.do")
public class InquiryProc extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		rePro(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		rePro(request, response);
	}
	protected void rePro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		
		int count = 0; // 전체게시글 수
		int pagesize = 5; // 한 화면에 그릴 페이지 수
		String pageNum = request.getParameter("pageNum");//화면에서 넘겨받을 pageNum값
		if(pageNum == null) { // 페이지 선택 안했을 경우 자동으로 1번 페이지 선택
			pageNum = "1";
		}
		
		int number = 0; 
		int curretnPage = Integer.parseInt(pageNum); // 현재 페이지 위치
		
		InquiryDAO idao = new InquiryDAO();
		count = idao.AllCount(); // 페이지 전체수 입력
		int StartRow = (curretnPage-1)*pagesize;//한 페이지에 출력할 개수
	
		
		ArrayList<InquiryDTO> iList = new ArrayList<InquiryDTO>();
		iList = idao.getAllList(StartRow, pagesize);
		
		
		number = count-StartRow; //페이지당 게시글 출력 번호
		int pageCount = 0; //페이지 개수
		int pageBlock = 3; //한 화면에 출력할 페이지 개수
		int startpage = 1; //한 화면 시작 페이지 수
		int endpage = 0; //마지막 페이지 수
		
		if(count > 0) {
			
			pageCount = count/pagesize + (count % pagesize == 0 ? 0:1);
			
			if(curretnPage % pageBlock != 0) {
				startpage = (curretnPage / pageBlock)*pageBlock+1;
			}else {
				startpage = ((curretnPage/ pageBlock)-1)*pageBlock+1;
			}
			
			endpage = startpage + pageBlock -1;
			
			if(endpage > pageCount) {
				endpage = pageCount;
			}
			
		}
		request.setAttribute("iList", iList);
		
		request.setAttribute("StartRow", StartRow);
		request.setAttribute("pageCount", pageCount);
		request.setAttribute("pagesize", pagesize);
		request.setAttribute("count", count);
		request.setAttribute("number", number);
		request.setAttribute("startpage", startpage);
		request.setAttribute("endpage", endpage);
		request.setAttribute("pageBlock", pageBlock);
		
		
		RequestDispatcher rd = request.getRequestDispatcher("/index.jsp?section=inquiry/inquiry-List.jsp");
		rd.forward(request, response);
		
	}

}