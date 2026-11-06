package greencinema.control.reserve;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import greencinema.model.reservejoin.ReservejoinDAO;
import greencinema.model.reservejoin.ReservejoinDTO;
import greencinema.model.theater.TheaterDAO;
import greencinema.model.theater.TheaterDTO;


@WebServlet("/selectMovieReserveProc.do")
public class selectMovieReserveProc extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}
	protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		ReservejoinDAO rjdao = new ReservejoinDAO();
		
		
		String movieId = request.getParameter("movieId");
		String largeRegion = request.getParameter("large_region");
		String smallRegion = request.getParameter("smallRegion"); 
		String selectedDate = request.getParameter("selectedDate");
		String movieTitle = request.getParameter("movieTitle");
		
		System.out.println("movieId: " + movieId);
	    System.out.println("largeRegion: " + largeRegion);
	    System.out.println("smallRegion: " + smallRegion);
	    System.out.println("selectedDate: " + selectedDate);
		
		ReservejoinDTO rjdto = rjdao.getOneMovieList(movieId);
		ArrayList<ReservejoinDTO> rjlist = rjdao.getAllLargeTheater();
		ArrayList<ReservejoinDTO> rjlist02 = rjdao.getAllSmallTheater(largeRegion);
		ArrayList<ReservejoinDTO> rjlist03 = rjdao.getAllschedule(movieId, largeRegion, smallRegion, selectedDate);
		ArrayList<ReservejoinDTO> rjlist04 = rjdao.getSelectMovie(largeRegion, smallRegion);
		
		request.setAttribute("rjdto", rjdto);
		request.setAttribute("rjlist", rjlist);
		request.setAttribute("rjlist02", rjlist02);
		request.setAttribute("rjlist03", rjlist03);
		request.setAttribute("rjlist04", rjlist04);

		RequestDispatcher rd = request.getRequestDispatcher("index.jsp?section=reserve/reserve-movie.jsp");
		rd.forward(request, response);
	}

}
