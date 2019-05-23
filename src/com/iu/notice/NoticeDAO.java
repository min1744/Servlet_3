package com.iu.notice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.iu.page.SearchRow;
import com.iu.utill.DBConnector;

public class NoticeDAO {
	public int getTotalCount(SearchRow searchRow) throws Exception {
		int result = 0;
		Connection con = DBConnector.getConnect();
		String sql = "SELECT COUNT(NUM) FROM NOTICE WHERE " + searchRow.getSearch().getKind() + " LIKE ?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, "%"+searchRow.getSearch().getSearch()+"%");
		ResultSet rs = st.executeQuery();
		rs.next();
		result = rs.getInt("COUNT(NUM)");
		DBConnector.disConnect(rs, st, con);
		
		return result;
	}
	
	/*public static void main(String[] args) {
		NoticeDAO noticeDAO = new NoticeDAO();
		for(int i = 0; i < 100; i++) {
			NoticeDTO noticeDTO = new NoticeDTO();
			noticeDTO.setTitle("Subject"+i);
			noticeDTO.setContents("내용"+i);
			noticeDTO.setWriter("관리자");
			try {
				noticeDAO.insert(noticeDTO);
				Thread.sleep(100);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}*/
	
	public ArrayList<NoticeDTO> selectList(SearchRow searchRow)throws Exception{
	      ArrayList<NoticeDTO> ar = new ArrayList<NoticeDTO>();
	      Connection con = DBConnector.getConnect();
	      String sql = " select * from (select rownum r, n.* from (select num, title, writer, reg_date, hit from notice where "+searchRow.getSearch().getKind()+" like ? order by num desc) n) where r between ? and ?";
	      PreparedStatement st = con.prepareStatement(sql);
	      st.setString(1, "%"+searchRow.getSearch().getSearch()+"%");
	      st.setInt(2, searchRow.getStartRow());
	      st.setInt(3, searchRow.getLastRow());
	      ResultSet rs = st.executeQuery();
	      while(rs.next()) {
	         NoticeDTO noticeDTO = new NoticeDTO();
	         noticeDTO.setNum(rs.getInt("num"));
	         noticeDTO.setTitle(rs.getString("title"));
	         noticeDTO.setWriter(rs.getString("writer"));
	         noticeDTO.setReg_date(rs.getDate("reg_date"));
	         noticeDTO.setHit(rs.getInt("hit"));
	         ar.add(noticeDTO);
	      }	
	      DBConnector.disConnect(rs, st, con);
	      return ar;
	   }
	
	public NoticeDTO selectOne(int num) throws Exception {
		NoticeDTO noticeDTO = null;
		Connection con = DBConnector.getConnect();
		String sql = "SELECT * FROM NOTICE WHERE NUM = ?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setInt(1, num);
		ResultSet rs = st.executeQuery();
		if(rs.next()) {
			noticeDTO = new NoticeDTO();
			noticeDTO.setNum(rs.getInt("num"));
			noticeDTO.setTitle(rs.getString("title"));
			noticeDTO.setWriter(rs.getString("writer"));
			noticeDTO.setReg_date(rs.getDate("reg_date"));
			noticeDTO.setHit(rs.getInt("hit"));
			noticeDTO.setContents(rs.getString("contents"));
		}
		DBConnector.disConnect(rs, st, con);
		
		return noticeDTO;
	}
	
	public int getNum() throws Exception{
		int result = 0;
		Connection con = DBConnector.getConnect();
		String sql = "SELECT NOTICE_SEQ.NEXTVAL FROM DUAL";
		PreparedStatement st = con.prepareStatement(sql);
		ResultSet rs = st.executeQuery();
		rs.next();
		result = rs.getInt(1);
		DBConnector.disConnect(rs, st, con);
		
		return result;
	}
	
	public int insert(NoticeDTO noticeDTO, Connection con) throws Exception {
		String sql = "INSERT INTO NOTICE VALUES(?, ?, ?, ?, SYSDATE, 0)";
		PreparedStatement st = con.prepareStatement(sql);
		st.setInt(1, noticeDTO.getNum());
		st.setString(2, noticeDTO.getTitle());
		st.setString(3, noticeDTO.getContents());
		st.setString(4, noticeDTO.getWriter());
		int result = st.executeUpdate();
		st.close();
		
		return result;
	}
	
	public int update(NoticeDTO noticeDTO) throws Exception {
		Connection con = DBConnector.getConnect();
		String sql = "UPDATE NOTICE SET TITLE = ?, CONTENTS = ? WHERE NUM = ?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, noticeDTO.getTitle());
		st.setString(2, noticeDTO.getContents());
		st.setInt(3, noticeDTO.getNum());
		int result = st.executeUpdate();
		
		return result;
	}
	
	public int delete(int num) throws Exception {
		Connection con = DBConnector.getConnect();
		String sql = "DELETE NOTICE WHERE NUM = ?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setInt(1, num);
		int result = st.executeUpdate();
		
		return result;
	}
}