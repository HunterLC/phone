package bll;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dal.MyDbHelper;
import model.osType;

public class OsTypeDao {
	public static OsTypeDao getInstance() {
		return new OsTypeDao();
	}
	
	public List<osType> QueryAll() {
		List<osType> list = new ArrayList<osType>();
		String sql = "select * from osType";
		ResultSet rs = MyDbHelper.executeQuery(sql);
		try {
			while(rs.next()) {
				osType item = new osType();
				item.setID(rs.getInt("ID"));
				item.setOsType(rs.getString("osType"));
				list.add(item);
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public int QueryIDByName(String osTypeName) {
		String sql = "select ID from osType where osType = ?";
		Object[] parameters = {osTypeName};
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
	
	public String QueryNameByID(int ID) {
		String sql = "select osType from osType where ID = ?";
		Object[] parameters = {ID};
		ResultSet rs = MyDbHelper.executeQuery(sql,parameters);
		try {
			while(rs.next()) {
				return rs.getString("osType");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public int QueryCount(){
		String sql = "select * from osType";
		return MyDbHelper.getCount(sql);
	}
	
	public boolean IsExist(String osTypeName){
		String sql = "select * from osType where osType = ?";
		Object[] parameters = {osTypeName};
		if( MyDbHelper.getCount(sql, parameters) > 0)
			return true;
		return false;
	}
	
	/**
	 * 根据id修改操作系统名称名
	 * @param osType
	 * @param id
	 * @return
	 */
	public int Update(String osType,int id){
		String sql = "update osType set osType = ? where ID = ?";
		Object[] parameters = {osType,id};
		if( MyDbHelper.executeNonQuery(sql, parameters) != 0)//执行影响行数不为0
			return 1;
		return 0;
	}
	
	/**
	 * 根据操作系统名称名删除
	 * @param osType
	 * @return
	 */
	public int Delete(String osType){
		String sql = "delete from osType where osType = ?";
		Object[] parameters = {osType};
		if( MyDbHelper.executeNonQuery(sql, parameters) != 0)//执行影响行数不为0
			return 1;
		return 0;
	}
	
	/**
	 * 添加操作系统名称
	 * @param osType
	 * @return
	 */
	public int Add(String osType){
		String sql = "insert into osType values(?)";
		Object[] parameters = {osType};
		if( MyDbHelper.executeNonQuery(sql, parameters) != 0)//执行影响行数不为0
			return 1;
		return 0;
	}
}
