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
	 * ��ѯ���е�����������Ϣ
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
		Object[] parameters = {"��"};
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
	 * ����������������ѯ��������ID
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
	 * ����id��ѯ����������
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
	 * ����id��ѯ�����
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
	 * ��ѯ����������Ŀ
	 * @return
	 */
	public int QueryCount(){
		String sql = "select * from Manufacturers";
		return MyDbHelper.getCount(sql);
	}
	
	/**
	 * �ж����������Ƿ����
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
	 * �ж�������Ƿ����
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
	 * ����id�޸�����������
	 * @param manufacturersName
	 * @param id
	 * @return
	 */
	public int Update(int num,String manufacturersName,String isUse,int id){
		String sql = "update Manufacturers set Num = ?,manufacturersName = ?,isUse = ? where ID = ?";
		Object[] parameters = {num,manufacturersName,isUse,id};
		if( MyDbHelper.executeNonQuery(sql, parameters) != 0)//ִ��Ӱ��������Ϊ0
			return 1;
		return 0;
	}
	
	/**
	 * ��������������ɾ��
	 * @param manufacturersName
	 * @return
	 */
	public int Delete(String manufacturersName){
		String sql = "delete from Manufacturers where manufacturersName = ?";
		Object[] parameters = {manufacturersName};
		if( MyDbHelper.executeNonQuery(sql, parameters) != 0)//ִ��Ӱ��������Ϊ0
			return 1;
		return 0;
	}
	
	/**
	 * �����������
	 * @param manufacturersName
	 * @return
	 */
	public int Add(int num,String manufacturersName,String isUse){
		String sql = "insert into Manufacturers values(?,?,?)";
		Object[] parameters = {num,manufacturersName,isUse};
		if( MyDbHelper.executeNonQuery(sql, parameters) != 0)//ִ��Ӱ��������Ϊ0
			return 1;
		return 0;
	}
}