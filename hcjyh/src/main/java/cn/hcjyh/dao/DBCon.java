package cn.hcjyh.dao;

import java.lang.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

public class DBCon {
	public static Connection connectMysqlDB() {
		String url = "jdbc:mysql://localhost:3306/hcgj";
		String user = "root";
		String password = "3263802";
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			System.out.println("驱动类加载失败");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("创建连接失败");
			e.printStackTrace();
		}
		System.out.println(con);
		return con;
		
	}
	
	public void shutdownConnection(Connection con, PreparedStatement ps, ResultSet rs) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				System.out.println("数据库连接关闭异常");
				e.printStackTrace();
			}
		}
		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				System.out.println("预编译关闭异常");
				e.printStackTrace();
			}
		}
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				System.out.println("结果集关闭异常");
				e.printStackTrace();
			}
		}
		
	}
}
