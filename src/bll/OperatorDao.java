package bll;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dal.MyDbHelper;
import model.Operator;

public class OperatorDao {
	/**
	 * 获得实例
	 * @return
	 */
	public static OperatorDao getInstance() {
		return new OperatorDao();
	}
	
	/**
	 * 获得当前用户所有的操作条件
	 * @param userID
	 * @return
	 */
	public List<Operator> QueryRecords(int userID) {
		List<Operator> list = new ArrayList<Operator>();
		String sql = "select * from Operator where userID = ?";
		Object[] parameters = {userID};
		ResultSet rs = MyDbHelper.executeQuery(sql,parameters);
		try {
			while(rs.next()) {
				Operator item = new Operator();
				item.setID(rs.getInt("ID"));
				item.setUserID(rs.getInt("userID"));
				item.setLogicalSignal(rs.getString("logicalSignal"));
				item.setSegment(rs.getString("segment"));
				item.setSignal(rs.getString("signal"));
				item.setValueData(rs.getString("valueData"));
				list.add(item);
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 删除选中的条件
	 * @param ID
	 * @return
	 */
	public int Delete(int ID){
		String sql = "delete from Operator where ID = ?";
		Object[] parameters = {ID};
		if( MyDbHelper.executeNonQuery(sql, parameters) != 0)//执行影响行数不为0
			return 1;//成功
		return 0; //失败
	}
	
	/**
	 * 添加新的where条件
	 * @param userID
	 * @param logicalSignal
	 * @param segment
	 * @param signal
	 * @param valueData
	 * @return
	 */
	public int Add(int userID,String logicalSignal,String segment,String signal,String valueData){
		String sql = "insert into Operator values(?,?,?,?,?)";
		Object[] parameters = {userID,logicalSignal,segment,signal,valueData};
		if( MyDbHelper.executeNonQuery(sql, parameters) != 0)//执行影响行数不为0
			return 1;//成功
		return 0;//失败
	}
	
	/**
	 * 查询条件ID
	 * @param userID
	 * @param logicalSignal
	 * @param segment
	 * @param signal
	 * @param valueData
	 * @return
	 */
	public int QueryID(int userID,String logicalSignal,String segment,String signal,String valueData){
		String sql = "select ID from Operator where userID = ? and logicalSignal = ? and segment = ? and signal = ? and valueData = ?";
		Object[] parameters = {userID,logicalSignal,segment,signal,valueData};
		ResultSet rs = MyDbHelper.executeQuery(sql,parameters);
		try {
			while(rs.next()) {
				return rs.getInt("ID");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
}
