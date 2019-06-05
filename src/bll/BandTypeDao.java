package bll;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dal.MyDbHelper;
import model.bandType;

public class BandTypeDao {
	
	public static BandTypeDao getInstance() {
		return new BandTypeDao();
	}
	
	/**
	 * 查询所有的信息
	 * @return
	 */
	public List<bandType> QueryAll() {
		List<bandType> list = new ArrayList<bandType>();
		String sql = "select * from bandType order by Code";
		ResultSet rs = MyDbHelper.executeQuery(sql);
		try {
			while(rs.next()) {
				bandType item = new bandType();
				item.setID(rs.getInt("ID"));
				item.setCode(rs.getString("Code"));
				item.setBandType(rs.getString("bandType"));
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
	 * 按照品牌类型名查询ID
	 * @param name
	 * @return
	 */
	public int QueryIDByName(String name) {
		String sql = "select ID from bandType where bandType = ?";
		Object[] parameters = {name};
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
	
	public String QueryCodeByName(String name) {
		String sql = "select Code from bandType where bandType = ?";
		Object[] parameters = {name};
		ResultSet rs = MyDbHelper.executeQuery(sql,parameters);
		try {
			while(rs.next()) {
				return rs.getString("Code");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 按照id查询品牌类型
	 * @param ID
	 * @return
	 */
	public String QueryNameByID(int ID) {
		String sql = "select bandType from bandType where ID = ?";
		Object[] parameters = {ID};
		ResultSet rs = MyDbHelper.executeQuery(sql,parameters);
		try {
			while(rs.next()) {
				return rs.getString("bandType");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String QueryNameByCode(String code) {
		String sql = "select bandType from bandType where Code = ?";
		Object[] parameters = {code};
		ResultSet rs = MyDbHelper.executeQuery(sql,parameters);
		try {
			while(rs.next()) {
				return rs.getString("bandType");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 按照id查询代码号
	 * @param ID
	 * @return
	 */
	public String QueryCodeByID(int ID) {
		String sql = "select Code from bandType where ID = ?";
		Object[] parameters = {ID};
		ResultSet rs = MyDbHelper.executeQuery(sql,parameters);
		try {
			while(rs.next()) {
				return rs.getString("Code");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 查询品牌类型数目
	 * @return
	 */
	public int QueryCount(){
		String sql = "select * from bandType";
		return MyDbHelper.getCount(sql);
	}
	
	/**
	 * 判断品牌类型名是否存在
	 * @return
	 */
	public boolean nameIsExist(String bjsName){
		String sql = "select * from bandType where bandType = ?";
		Object[] parameters = {bjsName};
		if( MyDbHelper.getCount(sql, parameters) > 0)
			return true;
		return false;
	}
	
	/**
	 * 判断代码号是否存在
	 * @param num
	 * @return
	 */
	public boolean codeIsExist(String code){
		String sql = "select * from bandType where Code = ?";
		Object[] parameters = {code};
		if( MyDbHelper.getCount(sql, parameters) > 0)
			return true;
		return false;
	}
	
	/**
	 * 根据id修改类型名
	 * @param bjsName
	 * @param id
	 * @return
	 */
	public int Update(String code,String bandType,int id){
		String sql = "update bandType set Code = ?,bandType = ? where ID = ?";
		Object[] parameters = {code,bandType,id};
		if( MyDbHelper.executeNonQuery(sql, parameters) != 0)//执行影响行数不为0
			return 1;
		return 0;
	}
	
	/**
	 * 根据类型名删除
	 * @param bjsName
	 * @return
	 */
	public int Delete(String bandType){
		String sql = "delete from bandType where bandType = ?";
		Object[] parameters = {bandType};
		if( MyDbHelper.executeNonQuery(sql, parameters) != 0)//执行影响行数不为0
			return 1;
		return 0;
	}
	
	/**
	 * 添加类型名
	 * @param bjsName
	 * @return
	 */
	public int Add(String code,String bandType){
		String sql = "insert into bandType values(?,?)";
		Object[] parameters = {code,bandType};
		if( MyDbHelper.executeNonQuery(sql, parameters) != 0)//执行影响行数不为0
			return 1;
		return 0;
	}
}