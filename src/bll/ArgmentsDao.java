package bll;

import java.sql.ResultSet;
import java.sql.SQLException;

import dal.MyDbHelper;
import model.Argments;

public class ArgmentsDao {
	
	private static Argments  argments= new Argments();
	
	public static ArgmentsDao getInstance() throws SQLException{  //���һ������ʵ��
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
	
	public int getNum(){    //��ȡ����������¼ʧ�ܵĴ���
		return argments.getNum();
	}
	
	public int getDays(){  //��ȡ�����¼����ǰ
		return argments.getDays();
	}
	
	public int getLock_days(){
		return argments.getLock_days();
	}
	
	
}
