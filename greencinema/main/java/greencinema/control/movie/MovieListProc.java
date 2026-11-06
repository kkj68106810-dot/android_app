package greencinema.control.movie;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import greencinema.model.movie.MovieDAO;
import greencinema.model.movie.MovieDTO;

@WebServlet("/MovieListProc.do")
public class MovieListProc extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}
	
	protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		MovieDAO mvdao = new MovieDAO();
		
		ArrayList<MovieDTO> mdtoList =  mvdao.getAllMovieList();
		System.out.println("DB에서 가져온 영화 개수 : " + mdtoList.size());
		request.setAttribute("mdtoList", mdtoList);
				
		RequestDispatcher rd = request.getRequestDispatcher("/index.jsp?section=section.jsp");		
		rd.forward(request, response);
		
	}


}
