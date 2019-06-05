package bll;

import java.sql.ResultSet;
import java.sql.SQLException;

import dal.MyDbHelper;

public class InventoryDao {
	
	public static InventoryDao getInstance() {
		return new InventoryDao();
	}
	
	/**
	 * �����ֻ��ͺ�����ѯID
	 * @param phoneNumber
	 * @return
	 */
	public int QueryIDByPhoneNumber(String phoneNumber) {
		String sql = "select ID from Inventory where phoneNumber = ?";
		Object[] parameters = {phoneNumber};
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
	 * ���¿����Ϣ
	 * @param inventory
	 * @param phoneNumber
	 * @return
	 */
	public int Update(int inventory,String phoneNumber){
		String sql = "update Inventory set inventory = ? where phoneNumber = ?";
		Object[] parameters = {inventory,phoneNumber};
		if( MyDbHelper.executeNonQuery(sql, parameters) != 0)//ִ��Ӱ��������Ϊ0
			return 1;
		return 0;
	}
	
	public int Add(String phoneNumber,int inventory){
		String sql = "insert into Inventory values(?,?)";
		Object[] parameters = {phoneNumber,inventory};
		if( MyDbHelper.executeNonQuery(sql, parameters) != 0)//ִ��Ӱ��������Ϊ0
			return 1;
		return 0;
	}
	
	/**
	 * ��ѯ�����
	 * @param phoneNumber
	 * @return
	 */
	public int QueryInventory(String phoneNumber) {
		String sql = "select inventory from Inventory where phoneNumber = ?";
		Object[] parameters = {phoneNumber};
		ResultSet rs = MyDbHelper.executeQuery(sql,parameters);
		try {
			while(rs.next()) {
				return rs.getInt("inventory");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
}
