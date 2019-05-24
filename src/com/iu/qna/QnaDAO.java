package com.iu.qna;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.iu.page.SearchRow;
import com.iu.upload.UploadDTO;
import com.iu.utill.DBConnector;

public class QnaDAO {
	//getNum
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
	
	public int insert(QnaDTO qnaDTO, Connection con) throws Exception{
		int result = 0;
		String sql = "INSERT INTO QNA VALUES(?, ?, ?, ?, SYSDATE, 0, ?, 0, 0)";
		PreparedStatement st = con.prepareStatement(sql);
		st.setInt(1, qnaDTO.getNum());
		st.setString(2, qnaDTO.getTitle());
		st.setString(3, qnaDTO.getContents());
		st.setString(4, qnaDTO.getWriter());
		st.setInt(5, qnaDTO.getNum());
		result = st.executeUpdate();
		st.close();
		
		return result;
	}
	
	//getCount
	public int getTotalCount(SearchRow searchRow)throws Exception{
		int result=0;
		Connection con = DBConnector.getConnect();
		String sql ="select count(num) from qna where "+searchRow.getSearch().getKind()+" like ?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, "%"+searchRow.getSearch().getSearch()+"%");
		ResultSet rs = st.executeQuery();
		rs.next();
		result = rs.getInt(1);
		DBConnector.disConnect(rs, st, con);
		return result;
	}
	
	
	//list
	public ArrayList<QnaDTO> list(SearchRow searchRow) throws Exception{
		ArrayList<QnaDTO> ar = new ArrayList<QnaDTO>();
		Connection con = DBConnector.getConnect();
		String sql ="select * from "
				+ "(select rownum R, Q.* from "
				+ "(select * from qna where "+searchRow.getSearch().getKind()+" like ? order by ref desc, step asc) Q) "
				+ "where R between ? and ?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, "%"+searchRow.getSearch().getSearch()+"%");
		st.setInt(2, searchRow.getStartRow());
		st.setInt(3, searchRow.getLastRow());
		ResultSet rs = st.executeQuery();
		while(rs.next()) {
			QnaDTO qnaDTO = new QnaDTO();
			qnaDTO.setNum(rs.getInt("num"));
			qnaDTO.setTitle(rs.getString("title"));
			qnaDTO.setContents(rs.getString("contents"));
			qnaDTO.setWriter(rs.getString("writer"));
			qnaDTO.setReg_date(rs.getDate("reg_date"));
			qnaDTO.setHit(rs.getInt("hit"));
			qnaDTO.setRef(rs.getInt("ref"));
			qnaDTO.setStep(rs.getInt("step"));
			qnaDTO.setDepth(rs.getInt("depth"));
			ar.add(qnaDTO);
		}
		DBConnector.disConnect(rs, st, con);
		return ar;
	}

}
