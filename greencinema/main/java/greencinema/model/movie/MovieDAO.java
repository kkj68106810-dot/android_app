package greencinema.model.movie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import greencinema.DBPKG.Util;

public class MovieDAO {
	Connection con = null;
	ResultSet rs = null;
	PreparedStatement pstmt = null;
	
	
	public ArrayList<MovieDTO> getAllMovieList () {
		ArrayList<MovieDTO> list = new ArrayList<>();
		
		try {
			con = Util.getConnection();
			String sql = "select * from movie";
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				
				System.out.println("movie_title : " + rs.getString("movie_title"));
				MovieDTO mvdto = new MovieDTO();
				mvdto.setMovieId(rs.getInt("movie_id"));
				mvdto.setImgPath(rs.getString("img_path"));
				mvdto.setMovieContent(rs.getString("movie_content"));
				mvdto.setMovieRate(rs.getInt("movie_rate"));
				mvdto.setMovieTitle(rs.getString("movie_title"));
				mvdto.setRunTime(rs.getInt("run_time"));
				
				list.add(mvdto);
			}		
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(con != null) con.close();
				if(con != null) con.close();
				if(con != null) con.close();
			} catch (Exception e) {e.printStackTrace();}
		}
		return list;		
	}

}
