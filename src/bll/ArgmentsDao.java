package bll;

import java.sql.ResultSet;
import java.sql.SQLException;

import dal.MyDbHelper;
import model.Argments;

public class ArgmentsDao {
	
	private static Argments  argments= new Argments();
	
	public static ArgmentsDao getInstance() throws SQLException{  //获得一个对象实例
		String sql = "select * from Argments";
		ResultSet rs = MyDbHelper.executeQuery(sql);
		while(rs.next()){
			argments.setID(rs.getInt("ID"));
			argments.setNum(rs.getInt("num"));
			argments.setDays(rs.getInt("days"));
			argments.setLock_days(rs.getInt("lock_days"));
		}
		return new ArgmentsDao();
	}
	
	public int getNum(){    //获取允许连续登录失败的次数
		return argments.getNum();
	}
	
	public int getDays(){  //获取允许登录几天前
		return argments.getDays();
	}
	
	public int getLock_days(){
		return argments.getLock_days();
	}
	
	
}
