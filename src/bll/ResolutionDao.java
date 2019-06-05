package bll;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dal.MyDbHelper;
import model.Editors;
import model.Resolution;

public class ResolutionDao {
	public static ResolutionDao getInstance() {
		return new ResolutionDao();
	}
	
	public List<Resolution> QueryAll() {
		List<Resolution> list = new ArrayList<Resolution>();
		String sql = "select * from Resolution";
		ResultSet rs = MyDbHelper.executeQuery(sql);
		try {
			while(rs.next()) {
				Resolution item = new Resolution();
				item.setID(rs.getInt("ID"));
				item.setResolutionName(rs.getString("resolutionName"));
				list.add(item);
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public int QueryIDByName(String resolutionName) {
		String sql = "select ID from Resolution where resolutionName = ?";
		Object[] parameters = {resolutionName};
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
		String sql = "select resolutionName from Resolution where ID = ?";
		Object[] parameters = {ID};
		ResultSet rs = MyDbHelper.executeQuery(sql,parameters);
		try {
			while(rs.next()) {
				return rs.getString("resolutionName");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public int QueryCount(){
		String sql = "select * from Resolution";
		return MyDbHelper.getCount(sql);
	}
	
	public boolean IsExist(String resolutionName){
		String sql = "select * from Resolution where resolutionName = ?";
		Object[] parameters = {resolutionName};
		if( MyDbHelper.getCount(sql, parameters) > 0)
			return true;
		return false;
	}
	/**
	 * 根据id修改分辨率名称名
	 * @param resolutionName
	 * @param id
	 * @return
	 */
	public int Update(String resolutionName,int id){
		String sql = "update Resolution set resolutionName = ? where ID = ?";
		Object[] parameters = {resolutionName,id};
		if( MyDbHelper.executeNonQuery(sql, parameters) != 0)//执行影响行数不为0
			return 1;
		return 0;
	}
	
	/**
	 * 根据分辨率名称名删除
	 * @param resolutionName
	 * @return
	 */
	public int Delete(String resolutionName){
		String sql = "delete from Resolution where resolutionName = ?";
		Object[] parameters = {resolutionName};
		if( MyDbHelper.executeNonQuery(sql, parameters) != 0)//执行影响行数不为0
			return 1;
		return 0;
	}
	
	/**
	 * 添加分辨率名称
	 * @param resolutionName
	 * @return
	 */
	public int Add(String resolutionName){
		String sql = "insert into Resolution values(?)";
		Object[] parameters = {resolutionName};
		if( MyDbHelper.executeNonQuery(sql, parameters) != 0)//执行影响行数不为0
			return 1;
		return 0;
	}
}
