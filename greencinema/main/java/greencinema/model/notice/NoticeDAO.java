package greencinema.model.notice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import greencinema.DBPKG.Util;
import greencinema.model.inquiry.InquiryDTO;

public class NoticeDAO {
	Connection con = null;
	ResultSet rs = null;
	PreparedStatement pstmt = null;

	public ArrayList<NoticeDTO> FaqAllList(int Startpage, int pagesize){
		ArrayList<NoticeDTO> n = new ArrayList<NoticeDTO>();
		 try {
			 con = Util.getConnection();
			 String sql = "select * from notice limit ?,?";
			 pstmt = con.prepareStatement(sql);
			 pstmt.setInt(1, Startpage);
			 pstmt.setInt(2, pagesize);
			 rs = pstmt.executeQuery();
			 
			 while(rs.next()) {
				 NoticeDTO ndto = new NoticeDTO();
				 ndto.setNoticeId(rs.getInt(1));
				 ndto.setNoticeTitle(rs.getString(2));
				 ndto.setNoticeDate(rs.getString(3));
				 ndto.setNoticeHits(rs.getInt(4));
				 ndto.setNoticeText(rs.getString(5));
				 
				 n.add(ndto);
				 
			 }
			 
			 
		 }catch(Exception e) {e.printStackTrace();}finally {
			 try {if(rs != null)rs.close();
			 if(pstmt != null)pstmt.close();
			 if(con != null)con.close();

			 }catch(Exception e) {
			 e.printStackTrace();
			 }
			 }
		 return n;
	}
	// faq list count
	public int NoticeAllCount() {
		int count=0;
		try {
			con = Util.getConnection();
			String sql = "select count(*) from notice";
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
	
	public int nCount(String type, String content) {
		int count=0;
		try {
			con = Util.getConnection();
			String sql = "select count(*) from notice where "+type+" like '%"+content+"%' ";
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
	public ArrayList<NoticeDTO> search(String type, String content, int Startpage, int pagesize){
			ArrayList<NoticeDTO> i = new ArrayList<NoticeDTO>();
		try { 
			con = Util.getConnection();
			String sql = "select * from notice where "+type+" like '%"+content+"%' limit ?,?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Startpage);
			pstmt.setInt(2, pagesize);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				NoticeDTO idto = new NoticeDTO();
				idto.setNoticeId(rs.getInt(1));
				idto.setNoticeTitle(rs.getString(2));
				idto.setNoticeDate(rs.getString(3));
				idto.setNoticeHits(rs.getInt(4));
				idto.setNoticeText(rs.getString(5));

				
				i.add(idto);
			}
			
			
		}catch(Exception e) {e.printStackTrace();}finally {
			 try {if(rs != null)rs.close();
			 if(pstmt != null)pstmt.close();
			 if(con != null)con.close();

			 }catch(Exception e) {
			 e.printStackTrace();
			 }
			 }
		
		return i;
	} 
	
	
}
