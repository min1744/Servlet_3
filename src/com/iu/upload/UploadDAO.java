package com.iu.upload;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.iu.utill.DBConnector;

public class UploadDAO {
	//insert()
	public int insert(UploadDTO uploadDTO) throws Exception {
		int result = 0;
		Connection con = DBConnector.getConnect();
		String sql = "INSERT INTO UPLOAD VALUES(NUM_SEQ.NEXTVAL, ?, ?, ?)";
		PreparedStatement st = con.prepareStatement(sql);
		st.setInt(1, uploadDTO.getNum());
		st.setString(2, uploadDTO.getOname());
		st.setString(3, uploadDTO.getFname());
		result = st.executeUpdate();
		DBConnector.disConnect(st, con);
		
		return result;
	}
	
	//select
	public UploadDTO selectOne(int num) throws Exception{
		UploadDTO uploadDTO = null;
		Connection con = DBConnector.getConnect();
		String sql = "SELECT * FROM UPLOAD WHERE NUM=?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setInt(1, num);
		ResultSet rs = st.executeQuery();
		if(rs.next()) {
			uploadDTO = new UploadDTO();
			uploadDTO.setPnum(rs.getInt("pnum"));
			uploadDTO.setNum(rs.getInt("num"));
			uploadDTO.setOname(rs.getString("oname"));
			uploadDTO.setFname(rs.getString("fname"));
		}
		DBConnector.disConnect(st, con);
		
		return uploadDTO;
	}
}