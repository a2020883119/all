package cn.hcjyh.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ConnectDao {
	public void updateConnectionMessage() {
		DBCon dao = new DBCon();
		Connection con = dao.connectMysqlDB();
//		PreparedStatement ps = con.prepareStatement(sql);
		
	}
}
