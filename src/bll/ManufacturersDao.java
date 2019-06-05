package bll;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dal.MyDbHelper;
import model.Manufacturers;

public class ManufacturersDao {
	
	public static ManufacturersDao getInstance() {
		return new ManufacturersDao();
	}
	
	/**
	 * 查询所有的生产厂家信息
	 * @return
	 */
	public List<Manufacturers> QueryAll() {
		List<Manufacturers> list = new ArrayList<Manufacturers>();
		String sql = "select * from Manufacturers order by Num";
		ResultSet rs = MyDbHelper.executeQuery(sql);
		try {
			while(rs.next()) {
				Manufacturers item = new Manufacturers();
				item.setID(rs.getInt("ID"));
				item.setNum(rs.getInt("Num"));
				item.setManufacturersName(rs.getString("manufacturersName"));
				item.setIsUse(rs.getString("isUse"));
				list.add(item);
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Manufacturers> QueryAllUsed() {
		List<Manufacturers> list = new ArrayList<Manufacturers>();
		String sql = "select * from Manufacturers where isUse=? order by Num";
		Object[] parameters = {"是"};
		ResultSet rs = MyDbHelper.executeQuery(sql,parameters);
		try {
			while(rs.next()) {
				Manufacturers item = new Manufacturers();
				item.setID(rs.getInt("ID"));
				item.setNum(rs.getInt("Num"));
				item.setManufacturersName(rs.getString("manufacturersName"));
				item.setIsUse(rs.getString("isUse"));
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
	 * 按照生产厂家名查询生产厂家ID
	 * @param name
	 * @return
	 */
	public int QueryIDByName(String name) {
		String sql = "select ID from Manufacturers where manufacturersName = ?";
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
	
	/**
	 * 按照id查询生产厂家名
	 * @param ID
	 * @return
	 */
	public String QueryNameByID(int ID) {
		String sql = "select manufacturersName from Manufacturers where ID = ?";
		Object[] parameters = {ID};
		ResultSet rs = MyDbHelper.executeQuery(sql,parameters);
		try {
			while(rs.next()) {
				return rs.getString("manufacturersName");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 按照id查询排序号
	 * @param ID
	 * @return
	 */
	public int QueryNumByID(int ID) {
		String sql = "select Num from Manufacturers where ID = ?";
		Object[] parameters = {ID};
		ResultSet rs = MyDbHelper.executeQuery(sql,parameters);
		try {
			while(rs.next()) {
				return rs.getInt("Num");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 查询生产厂家数目
	 * @return
	 */
	public int QueryCount(){
		String sql = "select * from Manufacturers";
		return MyDbHelper.getCount(sql);
	}
	
	/**
	 * 判断生产厂家是否存在
	 * @return
	 */
	public boolean nameIsExist(String manufacturersName){
		String sql = "select * from Manufacturers where manufacturersName = ?";
		Object[] parameters = {manufacturersName};
		if( MyDbHelper.getCount(sql, parameters) > 0)
			return true;
		return false;
	}
	
	/**
	 * 判断排序号是否存在
	 * @param num
	 * @return
	 */
	public boolean numIsExist(int num){
		String sql = "select * from Manufacturers where Num = ?";
		Object[] parameters = {num};
		if( MyDbHelper.getCount(sql, parameters) > 0)
			return true;
		return false;
	}
	
	/**
	 * 根据id修改生产厂家名
	 * @param manufacturersName
	 * @param id
	 * @return
	 */
	public int Update(int num,String manufacturersName,String isUse,int id){
		String sql = "update Manufacturers set Num = ?,manufacturersName = ?,isUse = ? where ID = ?";
		Object[] parameters = {num,manufacturersName,isUse,id};
		if( MyDbHelper.executeNonQuery(sql, parameters) != 0)//执行影响行数不为0
			return 1;
		return 0;
	}
	
	/**
	 * 根据生产厂家名删除
	 * @param manufacturersName
	 * @return
	 */
	public int Delete(String manufacturersName){
		String sql = "delete from Manufacturers where manufacturersName = ?";
		Object[] parameters = {manufacturersName};
		if( MyDbHelper.executeNonQuery(sql, parameters) != 0)//执行影响行数不为0
			return 1;
		return 0;
	}
	
	/**
	 * 添加生产厂家
	 * @param manufacturersName
	 * @return
	 */
	public int Add(int num,String manufacturersName,String isUse){
		String sql = "insert into Manufacturers values(?,?,?)";
		Object[] parameters = {num,manufacturersName,isUse};
		if( MyDbHelper.executeNonQuery(sql, parameters) != 0)//执行影响行数不为0
			return 1;
		return 0;
	}
}