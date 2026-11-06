package greencinema.control.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import greencinema.model.inquiry.InquiryDAO;

@WebServlet("/AdminAnswerProc.do")
public class AdminAnswerProc extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");

		HttpSession session = request.getSession(false);
		Object loginId = session == null ? null : session.getAttribute("id");
		String ctx = request.getContextPath();

		if (!"admin".equals(loginId)) {
			response.sendRedirect(ctx + "/index.jsp?section=user/login.jsp");
			return;
		}

		boolean saved = false;
		try {
			int inquiryId = Integer.parseInt(request.getParameter("inquiryId"));
			String answer = request.getParameter("answerContent");
			if (answer != null) {
				answer = answer.trim();
			}
			if (answer != null && !answer.isEmpty()) {
				saved = new InquiryDAO().answer(inquiryId, answer);
			}
		} catch (Exception e) {
			saved = false;
		}

		response.sendRedirect(ctx + "/admin/admin.jsp?saved=" + (saved ? "1" : "0") + "#inquiries");
	}
}
