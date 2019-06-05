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
	
	public static UsersRightsDao getInstance(){  //���һ��ʵ��
		return new UsersRightsDao();
	}
	
	/**
	 * �����û�id��ѯ�Ѿ������Ȩ�����Լ�����ģ��
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
				HashMap<String,String> allocatedRights = new HashMap<>(); //����û�<Ȩ������ģ����>
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
	 * �����û�id��ѯδ�����Ȩ�����Լ�����ģ��
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
				HashMap<String,String> allocatedRights = new HashMap<>(); //����û�<Ȩ������ģ����>
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
	 * �����û�Ȩ��
	 * @param id     �û�id
	 * @param index  ѡ����������
	 * @param table  ����Դ
	 * @return
	 */
	public boolean AddUserRights(int userId,int index[],JTable table){
		
		for(int i = 0; i < index.length; i++){  //���������ݲ������
			int rightId = RightsDao.getInstance().QueryByRightName(table.getValueAt(index[i], 1).toString());  //���ݸ�λ�õ�Ȩ������ȡ��Ӧ��id
			String sql = "insert into UsersRights(userID,rightID) values(?,?)";
			Object[] parameters = {userId,rightId};
			if(MyDbHelper.executeNonQuery(sql, parameters) == 0)
				return false;
		}
		return true;
	}
	
	/**
	 * ɾ���û�Ȩ��
	 * @param userId
	 * @param index
	 * @param table
	 * @return
	 */
	public boolean DeleteUserRights(int userId,int index[],JTable table){
		
		for(int i = 0; i < index.length; i++){  //����������ɾ��
			int rightId = RightsDao.getInstance().QueryByRightName(table.getValueAt(index[i], 1).toString());  //���ݸ�λ�õ�Ȩ������ȡ��Ӧ��id
			String sql = "delete from UsersRights where userID = ? and rightID = ?";
			Object[] parameters = {userId,rightId};
			if(MyDbHelper.executeNonQuery(sql, parameters) == 0)
				return false;
		}
		return true;
	}
}
