package greencinema.model.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import greencinema.DBPKG.Util;
import greencinema.model.inquiry.InquiryDTO;
import greencinema.model.reserve.ReserveDTO;

public class UserDAO {

	Connection con = null;
	ResultSet rs = null;
	PreparedStatement pstmt = null;
	
	
	public boolean duplicateChk(String userId) {
		boolean isDuplicate = false;
		try {

			con = Util.getConnection();
			String sql = "SELECT * FROM user where user_id = ?";
			pstmt = con.prepareStatement(sql);
			
			if(rs.next()) {				
				isDuplicate = true;
			}
			
		}catch (Exception e) {
			
		}finally {
			try {
				if(con != null) con.close();
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return isDuplicate;
		
	}
	
	
	public void joinUser(UserDTO udto) {		
		try {
			con = Util.getConnection();
			String sql = "INSERT INTO user VALUES (?, ?, ?, ?, ?, ?, 0)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, udto.getUserId());
			pstmt.setString(2, udto.getUserPw());
			pstmt.setString(3, udto.getUserName());
			pstmt.setString(4, udto.getUserBirth());			
			pstmt.setString(5, udto.getUserEmail());
			pstmt.setString(6, udto.getUserPhone());					
			
			pstmt.executeUpdate();
		}catch (Exception e) {
			
		}finally {
			try {
				if(con != null) con.close();
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
	}
	
	
	public boolean loginCheck(String userId, String userPw) {
		boolean isLogin = false;
		try {
			con = Util.getConnection();
			String sql = "SELECT * FROM user WHERE user_id = ? and user_pw = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setString(2, userPw);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				isLogin = true;
			}
			
		}catch (Exception e) {
			
		}finally {
			try {
				if(con != null) con.close();
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return isLogin;
	}
	
	
	// 예매리스트 정보
	public ArrayList<ReserveDTO> getReserveList (String userId) {
		ArrayList<ReserveDTO> rList = new ArrayList<>();
		try {
			con = Util.getConnection();
			String sql = "SELECT * FROM reserve WHERE user_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userId);		
			rs = pstmt.executeQuery();
						
			while(rs.next()) {
				ReserveDTO rdto = new ReserveDTO();
				rdto.setUserId(rs.getString("user_id"));
				rdto.setAdultCount(rs.getInt("adult_count"));
				rdto.setTeensCount(rs.getInt("teens_count"));
				rdto.setTotalPrice(rs.getInt("total_price"));
				rdto.setUserId(rs.getString("user_id"));
				rdto.setReserveDate(rs.getDate("reserve_date"));
				rdto.setCancelDate(rs.getDate("cancel_date"));
				rList.add(rdto);
			}
			
		}catch (Exception e) {
			
		}finally {
			try {
				if(con != null) con.close();
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		return rList;
	}
	
	// 1:1문의 리스트 추가
	public ArrayList<InquiryDTO> getInquiryList (String userId) {
		ArrayList<InquiryDTO> iList = new ArrayList<>();
		try {
			con = Util.getConnection();
			String sql = "SELECT * FROM reserve WHERE user_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userId);		
			rs = pstmt.executeQuery();
						
			while(rs.next()) {
				InquiryDTO idto = new InquiryDTO();
				idto.setInquiryId(rs.getInt("inquiryId"));
				idto.setInquirySort(rs.getString("inquirySort"));
				idto.setInquiryType(rs.getString("inquiryType"));
				idto.setInquiryTitle(rs.getString("inquiryTitle"));
				idto.setInquiryContent(rs.getString("inquiryContent"));
				idto.setReceptionType(rs.getInt("receptionType"));
				idto.setAnswerStatus(rs.getInt("answerStatus"));
				idto.setAnswerContent(rs.getString("answerContent"));
			}
			
		}catch (Exception e) {
			
		}finally {
			try {
				if(con != null) con.close();
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return iList;
	}
	
	
	public UserDTO getUser(String userId) {
		UserDTO userDto = new UserDTO();
		try {
			con = Util.getConnection();
			String sql = "SELECT * FROM USER WHERE user_id = ?";						
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, userId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
			    userDto.setUserId(rs.getString(1));
			    userDto.setUserPw(rs.getString(2));
			    userDto.setUserName(rs.getString(3));
			    userDto.setUserBirth(rs.getString(4));
			    userDto.setUserEmail(rs.getString(5));
			    userDto.setUserPhone(rs.getString(6));
			}
			
		}catch (Exception e) {
			
		}finally {
			try {
				if(con != null) con.close();
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return userDto;
	}
	
	
}
