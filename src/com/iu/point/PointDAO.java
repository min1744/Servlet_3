package com.iu.point;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Random;

import com.iu.utill.DBConnector;

public class PointDAO {
	//getTotalCount()
	public int getTotalCount(String kind, String search) throws Exception {
		int result = 0;
		Connection con = DBConnector.getConnect();
		String sql = "SELECT COUNT(NUM) FROM POINT WHERE " + kind + " LIKE ?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, "%"+search+"%");
		ResultSet rs = st.executeQuery();
		rs.next();
		result = rs.getInt(1);
		DBConnector.disConnect(rs, st, con);
		
		return result;
	}
	
	/*public static void main(String[] args){
		PointDAO pointDAO = new PointDAO();
		Random random = new Random();
		for(int i = 0; i < 100; i++) {
			PointDTO pointDTO = new PointDTO();
			pointDTO.setName("name"+i);
			pointDTO.setKor(random.nextInt(101));
			pointDTO.setEng(random.nextInt(101));
			pointDTO.setMath(random.nextInt(101));
			pointDTO.setSum(pointDTO.getKor() + pointDTO.getEng() + pointDTO.getMath());
			pointDTO.setAvg(pointDTO.getSum()/3.0);
			try {
				pointDAO.insert(pointDTO);
				Thread.sleep(300);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}*/
	
	//메서드명 : selectList
	//리턴 : ArrayList
	//매개변수 : X
	//예외는 던지기
	public ArrayList<PointDTO> selectList(String kind, String search, int startRow, int lastRow) throws Exception {
		ArrayList<PointDTO> ar = new ArrayList<PointDTO>();
		PointDTO pointDTO = null;
		
		Connection con = DBConnector.getConnect();
		String sql = "SELECT * FROM (SELECT ROWNUM R, p.* FROM (SELECT * FROM POINT WHERE " + kind + " LIKE ? ORDER BY NUM DESC) P) WHERE R BETWEEN ? AND ?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, "%"+search+"%");
		st.setInt(2, startRow);
		st.setInt(3, lastRow);
		ResultSet rs = st.executeQuery();
		while(rs.next()) {
			pointDTO = new PointDTO();
			pointDTO.setNum(rs.getInt("num"));
			pointDTO.setName(rs.getString("name"));
			pointDTO.setKor(rs.getInt("kor"));
			pointDTO.setEng(rs.getInt("eng"));
			pointDTO.setMath(rs.getInt("math"));
			pointDTO.setSum(rs.getInt("sum"));
			pointDTO.setAvg(rs.getDouble("avg"));
			ar.add(pointDTO);
		}
		//8. 연결 해제
		DBConnector.disConnect(rs, st, con);
		
		return ar;
	}
	
	//메서드명 : selectOne
	//리턴 : PointDTO
	//매개변수 : int
	//예외는 던지기
	public PointDTO selectOne(int num) throws Exception {
		PointDTO pointDTO = null;
		Connection con = DBConnector.getConnect();
		
		String sql = "SELECT * FROM POINT WHERE NUM = ?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setInt(1, num);
		ResultSet rs = st.executeQuery();
		if(rs.next()) {
			pointDTO = new PointDTO();
			pointDTO.setNum(rs.getInt("num"));
			pointDTO.setName(rs.getString("name"));
			pointDTO.setKor(rs.getInt("kor"));
			pointDTO.setEng(rs.getInt("eng"));
			pointDTO.setMath(rs.getInt("math"));
			pointDTO.setSum(rs.getInt("sum"));
			pointDTO.setAvg(rs.getDouble("avg"));
		}
		//8. 연결 해제
		DBConnector.disConnect(rs, st, con);
		
		return pointDTO;
	}
	
	//메서드명 : delete
	//리턴 : int
	//매개변수 : int
	//예외는 던지기
	public int delete(int num) throws Exception {
		Connection con = DBConnector.getConnect();
		
		
		String sql = "DELETE POINT WHERE NUM = ?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setInt(1, num);
		int result = st.executeUpdate();
		
		//8. 연결 해제
		DBConnector.disConnect(st, con);
		
		return result;
	}
	
	//메서드명 : update
	//리턴 : int
	//매개변수 : pointDTO
	//예외는 던지기
	public int update(PointDTO pointDTO) throws Exception {
		Connection con = DBConnector.getConnect();
		//4. SQL문 생성
		String sql = "UPDATE POINT SET NAME = ?, KOR = ?, ENG = ?, MATH = ?, SUM = ?, AVG = ? WHERE NUM = ?";
		
		//5. 미리 전송
		PreparedStatement st = con.prepareStatement(sql);
		
		//6. ? 세팅
		st.setString(1, pointDTO.getName());
		st.setInt(2, pointDTO.getKor());
		st.setInt(3, pointDTO.getEng());
		st.setInt(4, pointDTO.getMath());
		st.setInt(5, pointDTO.getSum());
		st.setDouble(6, pointDTO.getAvg());
		st.setInt(7, pointDTO.getNum());
		//7. 최종 전송 후 처리
		int result = st.executeUpdate();
		
		//8. 연결 해제
		DBConnector.disConnect(st, con);
		
		return result;
	}
	
	//메서드명은 insert
	//리턴은 int
	//매개변수는 PointDTO
	//예외는 던지기
	public int insert(PointDTO pointDTO) throws Exception {
		Connection con = DBConnector.getConnect();
		//4. SQL문 생성
		String sql = "INSERT INTO POINT VALUES(NUM_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?)";
		
		//5. 미리 전송
		PreparedStatement st = con.prepareStatement(sql);
		
		//6. ? 세팅
		st.setString(1, pointDTO.getName());
		st.setInt(2, pointDTO.getKor());
		st.setInt(3, pointDTO.getEng());
		st.setInt(4, pointDTO.getMath());
		st.setInt(5, pointDTO.getSum());
		st.setDouble(6, pointDTO.getAvg());
		//7. 최종 전송 후 처리
		int result = st.executeUpdate();
		
		//8. 연결 해제
		DBConnector.disConnect(st, con);
		
		return result;
	}
}