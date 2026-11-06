package greencinema.model.reservejoin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import greencinema.DBPKG.Util;
import greencinema.model.movie.MovieDTO;
import greencinema.model.theater.TheaterDTO;

public class ReservejoinDAO {
	Connection con = null;
	ResultSet rs = null;
	PreparedStatement pstmt = null;

	// 하나의 영화 Select 하는 메소드
	public ReservejoinDTO getOneMovieList(String id) {
		ReservejoinDTO rjdto = new ReservejoinDTO();
		try {
			con = Util.getConnection();
			String sql = "select * from movie where movie_id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				rjdto.setMovieId(rs.getInt("movie_id"));
				rjdto.setImgPath(rs.getString("img_path"));
				rjdto.setMovieContent(rs.getString("movie_content"));
				rjdto.setMovieRate(rs.getInt("movie_rate"));
				rjdto.setMovieTitle(rs.getString("movie_title"));
				rjdto.setRunTime(rs.getInt("run_time"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return rjdto; // DTO 객체 반환
	}

	// 극장 테이블 모든 정보 select하는 메소드
	public ArrayList<ReservejoinDTO> getAllTheaterList() {
		ArrayList<ReservejoinDTO> list = new ArrayList<>();
		
		try {
			con = Util.getConnection();
			String sql = "select * from theater";
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {				
				ReservejoinDTO rjdto = new ReservejoinDTO();
				rjdto.setTheaterId(rs.getInt("theater_id"));
				rjdto.setLargeRegion(rs.getString("large_region"));
				rjdto.setSmallRegion(rs.getString("small_region"));								
				list.add(rjdto);
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
	
	// 지역 대분류 select하는 메소드
	public ArrayList<ReservejoinDTO> getAllLargeTheater () {
	    ArrayList<ReservejoinDTO> list = new ArrayList<>();
	    try {
	        con = Util.getConnection();
	        String sql = "select large_region from theater group by large_region";
	        pstmt = con.prepareStatement(sql);
	        
	        rs = pstmt.executeQuery();
	        while(rs.next()) {
	            ReservejoinDTO rjdto = new ReservejoinDTO();
	            rjdto.setLargeRegion(rs.getString("large_region"));
	                            
	            list.add(rjdto);
	        }       
	    } catch(Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if(rs != null) rs.close();
	            if(pstmt != null) pstmt.close();
	            if(con != null) con.close();
	        } catch (Exception e) {e.printStackTrace();}
	    }
	    return list;        
	}
	
	
	// 지역 대분류 선택 후 해당 지역대분류에 해당하는 지역 소분류 select 하는 메소드
	public ArrayList<ReservejoinDTO> getAllSmallTheater (String largeRegion) {
	    ArrayList<ReservejoinDTO> list = new ArrayList<>();
	    try {
	        con = Util.getConnection();
	        String sql = "select small_region from theater where large_region=? group by small_region";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setString(1, largeRegion);
	        
	        rs = pstmt.executeQuery();
	        while(rs.next()) {
	            ReservejoinDTO rjdto = new ReservejoinDTO();
	            rjdto.setSmallRegion(rs.getString("small_region"));
	                            
	            list.add(rjdto);
	        }       
	    } catch(Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if(rs != null) rs.close();
	            if(pstmt != null) pstmt.close();
	            if(con != null) con.close();
	        } catch (Exception e) {e.printStackTrace();}
	    }
	    return list;        
	}
	
	// 영화,지역 대분류,지역소분류, 날짜 선택 후 해당 영화,지역,날짜에 해당하는 상영 스케쥴 select하는 메소드
	
	public ArrayList<ReservejoinDTO> getAllschedule(String movieid, String largeRegion, String smallRegion, String date) {
	    ArrayList<ReservejoinDTO> list = new ArrayList<>();
	    
	    // 필수 값이 하나라도 없으면 빈 리스트 반환
	    if (movieid == null || largeRegion == null || smallRegion == null) {
	        return list;
	    }

	    try {
	        con = Util.getConnection();
	        
	        // 기본 SQL 쿼리 작성
	        String sql = "select * from schedule s "
	                   + "inner join movie m on s.movie_id = m.movie_id "
	                   + "inner join screen sc on s.screen_id = sc.screen_id "
	                   + "inner join theater t on t.theater_id = sc.theater_id "
	                   + "where s.movie_id = ? and t.large_region = ? and t.small_region = ?";
	        
	        // 날짜가 선택되어 있으면 조건 추가
	        boolean hasDate = (date != null && !date.trim().isEmpty());
	        if (hasDate) {
	            sql += " and s.schedule_starttime like ?";
	        }
	        
	        pstmt = con.prepareStatement(sql);
	        pstmt.setString(1, movieid);
	        pstmt.setString(2, largeRegion);
	        pstmt.setString(3, smallRegion);
	        
	        if (hasDate) {
	            pstmt.setString(4, "%" + date + "%");
	        }
	        
	        rs = pstmt.executeQuery();
	        System.out.println("실행된 SQL: " + sql);
	        
	        while(rs.next()) {
	            ReservejoinDTO rjdto = new ReservejoinDTO();
	            rjdto.setScheduleStarttime(rs.getString("schedule_starttime"));                         
	            rjdto.setTotalSeat(rs.getInt("total_seat"));                       
	            rjdto.setScreenId(rs.getInt("screen_id"));                         
	            list.add(rjdto);
	        }
	    } catch(Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if(rs != null) rs.close();
	            if(pstmt != null) pstmt.close();
	            if(con != null) con.close();
	        } catch (Exception e) {e.printStackTrace();}
	    }
	    return list;        
	}
	
	
	// 지역 대분류, 지역 소분류 선택 후 해당 지역에 상영중인 영화만 select하는 메소드
	
	public ArrayList<ReservejoinDTO> getSelectMovie(String largeRegion, String smallRegion) {
	    ArrayList<ReservejoinDTO> list = new ArrayList<>();
	    
	    if (largeRegion == null || smallRegion == null) {
	        return list;
	    }
	    try {
	        con = Util.getConnection();
	        
	        String sql = "select distinct m.movie_id, m.movie_title,m.movie_rate from schedule s "
	                   + "inner join movie m on s.movie_id = m.movie_id "
	                   + "inner join screen sc on s.screen_id = sc.screen_id "
	                   + "inner join theater t on t.theater_id = sc.theater_id "
	                   + "where t.large_region = ? and t.small_region = ?";
	        
	        pstmt = con.prepareStatement(sql);
	        pstmt.setString(1, largeRegion);
	        pstmt.setString(2, smallRegion);
	        
	        rs = pstmt.executeQuery();
	        while(rs.next()) {
	            ReservejoinDTO rjdto = new ReservejoinDTO();
	            rjdto.setMovieId(rs.getInt("movie_id"));
	            rjdto.setMovieTitle(rs.getString("movie_title"));  
	            rjdto.setMovieRate(rs.getInt("movie_rate"));
	            list.add(rjdto);
	        }
	    } catch(Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if(rs != null) rs.close();
	            if(pstmt != null) pstmt.close();
	            if(con != null) con.close();
	        } catch (Exception e) {e.printStackTrace();}
	    }
	    return list;        
	}
	
}
