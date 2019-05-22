package com.iu.utill;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnector {
	//Connection
	//메서드명 : getConnect
	//DB 연결 객체(Connection)를 리턴
	//클래스 메서드
	public static Connection getConnect() throws Exception {
		//1. 4가지 정보
		//2. 드라이버 로딩
		//3. 로그인
		//4. sql문 생성
		//5. 미리 전송
		//6. ? 세팅
		//7. 최종 전송 후 처리
		//8. 연결 끊기
		String user = "user03";
		String password = "user03";
		String url = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
		String driver = "oracle.jdbc.driver.OracleDriver";
		
		Class.forName(driver);
		Connection con = DriverManager.getConnection(url, user, password);
		
		return con;
	}
	
	//연결 끊기
	//메서드명 disConnect 매개변수
	public static void disConnect(Connection con) throws Exception {
		con.close();
	}
	
	public static void disConnect(PreparedStatement st, Connection con) throws Exception {
		st.close();
		con.close();
	}
	
	public static void disConnect(ResultSet rs, PreparedStatement st, Connection con) throws Exception {
		rs.close();
		st.close();
		con.close();
	}
}