package greencinema.model.faq;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import greencinema.DBPKG.Util;
import greencinema.model.inquiry.InquiryDTO;


public class FaqDAO {
	Connection con = null;
	ResultSet rs = null;
	PreparedStatement pstmt = null;
	
	
	// faq 리스트 select
	public ArrayList<FaqDTO> FaqAllList(int StartRow, int pageSize){
		ArrayList<FaqDTO> f = new ArrayList<FaqDTO>();
		 try {
			 con = Util.getConnection();
			 String sql = "select * from faq order by faq_id desc limit ?,?";
			 pstmt = con.prepareStatement(sql);
			 pstmt.setInt(1, StartRow);
			 pstmt.setInt(2, pageSize);
			 rs = pstmt.executeQuery();
			 
			 while(rs.next()) {
				 FaqDTO fdto = new FaqDTO();
				 fdto.setFaqId(rs.getInt(1));
				 fdto.setFaqType(rs.getInt(2));
				 fdto.setFaqInquery(rs.getString(3));
				 fdto.setFaqAnswer(rs.getString(4));
				 
				 f.add(fdto);
				 
			 }
			 
			 
		 }catch(Exception e) {e.printStackTrace();}finally {
			 try {if(rs != null)rs.close();
			 if(pstmt != null)pstmt.close();
			 if(con != null)con.close();

			 }catch(Exception e) {
			 e.printStackTrace();
			 }
			 }
		 return f;
	}
	// faq list count
	public int AllCount() {
		int count=0;
		try {
			con = Util.getConnection();
			String sql = "select count(*) from faq";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt(1);
			}
			
			
		}catch(Exception e) {e.printStackTrace();}finally {
			try {if(rs != null)rs.close();
				if(pstmt != null)pstmt.close();
				if(con != null)con.close();

			}catch(Exception e) {
				e.printStackTrace();
			}
		}

		return count;
		
	}
	//--faq search 메소드
	public int fCount(String type, String content) {
		int count=0;
		try {
			con = Util.getConnection();
			String sql = "select count(*) from faq where "+type+" like '%"+content+"%' ";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt(1);
			}
			
			
		}catch(Exception e) {e.printStackTrace();}finally {
			try {if(rs != null)rs.close();
				if(pstmt != null)pstmt.close();
				if(con != null)con.close();

			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return count;

		
	}
	// search select add
	public ArrayList<FaqDTO> search(String type, String content, int Startpage, int pagesize){
			ArrayList<FaqDTO> f = new ArrayList<FaqDTO>();
		try { 
			con = Util.getConnection();
			String sql = "select * from faq where "+type+" like '%"+content+"%' limit ?,?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Startpage);
			pstmt.setInt(2, pagesize);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				FaqDTO fdto = new FaqDTO();
				fdto.setFaqId(rs.getInt(1));
				fdto.setFaqType(rs.getInt(2));
				fdto.setFaqInquery(rs.getString(3));
				fdto.setFaqAnswer(rs.getString(4));
				
				f.add(fdto);
			}
			
			
		}catch(Exception e) {e.printStackTrace();}finally {
			 try {if(rs != null)rs.close();
			 if(pstmt != null)pstmt.close();
			 if(con != null)con.close();

			 }catch(Exception e) {
			 e.printStackTrace();
			 }
			 }
		
		return f;
	} 
}
