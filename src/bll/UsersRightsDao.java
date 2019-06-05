package bll;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JTable;

import dal.MyDbHelper;

public class UsersRightsDao {
	
	public static UsersRightsDao getInstance(){  //获得一个实例
		return new UsersRightsDao();
	}
	
	/**
	 * 按照用户id查询已经分配的权限名以及所属模块
	 * @param id
	 * @return
	 */
	public List<HashMap<String,String>> AllocatedByUserID(int id){
		List<HashMap<String,String>> result = new ArrayList<HashMap<String,String>>();
		String sql = "select rightID,rightName,Module from UsersRights,Rights where UsersRights.userID = ? and UsersRights.rightID = Rights.id order by rightID";
		Object[] parameters = {id};
		ResultSet rs = MyDbHelper.executeQuery(sql, parameters);
		try {
			while(rs.next()){
				HashMap<String,String> allocatedRights = new HashMap<>(); //存放用户<权限名，模块名>
				allocatedRights.put(rs.getString("rightName"), rs.getString("Module"));
				result.add(allocatedRights);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 按照用户id查询未分配的权限名以及所属模块
	 * @param id
	 * @return
	 */
	public List<HashMap<String,String>> UnallocatedByUserID(int id){
		List<HashMap<String,String>> result = new ArrayList<HashMap<String,String>>();
		String sql = "select rightName,Module from UsersUNRights,Rights where UsersUNRights.userid = ? and UsersUNRights.rightid = Rights.id";
		Object[] parameters = {id};
		ResultSet rs = MyDbHelper.executeQuery(sql, parameters);
		try {
			while(rs.next()){
				HashMap<String,String> allocatedRights = new HashMap<>(); //存放用户<权限名，模块名>
				allocatedRights.put(rs.getString("rightName"), rs.getString("Module"));
				result.add(allocatedRights);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 增加用户权限
	 * @param id     用户id
	 * @param index  选中数据索引
	 * @param table  数据源
	 * @return
	 */
	public boolean AddUserRights(int userId,int index[],JTable table){
		
		for(int i = 0; i < index.length; i++){  //将所有数据插入表中
			int rightId = RightsDao.getInstance().QueryByRightName(table.getValueAt(index[i], 1).toString());  //根据该位置的权限名获取相应的id
			String sql = "insert into UsersRights(userID,rightID) values(?,?)";
			Object[] parameters = {userId,rightId};
			if(MyDbHelper.executeNonQuery(sql, parameters) == 0)
				return false;
		}
		return true;
	}
	
	/**
	 * 删除用户权限
	 * @param userId
	 * @param index
	 * @param table
	 * @return
	 */
	public boolean DeleteUserRights(int userId,int index[],JTable table){
		
		for(int i = 0; i < index.length; i++){  //将所有数据删除
			int rightId = RightsDao.getInstance().QueryByRightName(table.getValueAt(index[i], 1).toString());  //根据该位置的权限名获取相应的id
			String sql = "delete from UsersRights where userID = ? and rightID = ?";
			Object[] parameters = {userId,rightId};
			if(MyDbHelper.executeNonQuery(sql, parameters) == 0)
				return false;
		}
		return true;
	}
}
