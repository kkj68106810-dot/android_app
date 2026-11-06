package greencinema.control.user;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import greencinema.model.user.UserDAO;

@WebServlet("/UserLoginProc.do")
public class UserLoginProc extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}
	
	protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		boolean isSuccess = false;
		
		String userId = request.getParameter("userId");
		String userPw = request.getParameter("userPw");
		
		UserDAO udao = new UserDAO();
		isSuccess = udao.loginCheck(userId, userPw);
		String targetUri = "";
		
		
		if(isSuccess) {
			HttpSession session = request.getSession();
			session.setAttribute("id", userId);
			targetUri = "admin".equals(userId) ? "/admin/admin.jsp" : "/index.jsp";
		} else {
			targetUri = "/index.jsp?section=user/login-err.jsp";
			request.setAttribute("msg", "아이디 혹은 비밀번호가 맞지 않습니다.");
		}
		
		
		RequestDispatcher rd = request.getRequestDispatcher(targetUri);
		rd.forward(request, response);
		
	}

}
