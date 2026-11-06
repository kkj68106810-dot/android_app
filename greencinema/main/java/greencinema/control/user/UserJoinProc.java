package greencinema.control.user;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import greencinema.model.user.UserDAO;
import greencinema.model.user.UserDTO;

@WebServlet("/UserJoinProc.do")
public class UserJoinProc extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}
	
	protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String userId = request.getParameter("userId");
		String userPw = request.getParameter("userPw");
		String userPwChk = request.getParameter("userPwChk");
		String userName = request.getParameter("userName");
		String userBirth = request.getParameter("userBirth");
		String userEmail = request.getParameter("userEmail");
		String userPhone = request.getParameter("userPhone");
		
		
		UserDAO udao = new UserDAO();
		UserDTO udto = new UserDTO();
		
		boolean isDuplicate = udao.duplicateChk(userId);
		
		// 1. 비밀번호 불일치 검사
		if (userPw == null || !userPw.equals(userPwChk)) {
			request.setAttribute("msg", "암호와 암호확인이 맞지 않습니다.");
			RequestDispatcher rd = request.getRequestDispatcher("/index.jsp?section=user/user-join-passerr.jsp");
			rd.forward(request, response);
			return;
		}
		
		// 2. 아이디 중복 검사
		if (isDuplicate) {
			request.setAttribute("msg", "이미 가입된 유저 아이디 입니다.");
			RequestDispatcher rd = request.getRequestDispatcher("/index.jsp?section=user/user-join-iderr.jsp");
			rd.forward(request, response);
			return;
		}
		
		
		// 3. 정상 회원가입 처리
		udto.setUserId(userId);
		udto.setUserPw(userPw);
		udto.setUserName(userName);
		udto.setUserBirth(userBirth);
		udto.setUserEmail(userEmail);
		udto.setUserPhone(userPhone);
		
		udao.joinUser(udto);		
		
		
		
		response.sendRedirect("/index.jsp");			
		return;


	}

}
