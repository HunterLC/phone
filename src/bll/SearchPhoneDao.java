package bll;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dal.MyDbHelper;
import model.Phone;

public class SearchPhoneDao {
	public static SearchPhoneDao getInstance() {
		return new SearchPhoneDao();
	}
	
	/**
	 * 查询手机目
	 * @param condition
	 * @return
	 */
	public List<Phone> QueryAll(String condition){
		List<Phone> list = new ArrayList<Phone>();
		String sql = null;
		ResultSet rs = null;
		//没有条件
		if(condition == null||condition.equals("")) {
			sql = "select * from Phones";
			rs = MyDbHelper.executeQuery(sql);
		}
		//有条件
		else {
			sql = "select * from Phones where"+condition;
			rs = MyDbHelper.executeQuery(sql);
		}
		try {
			while(rs.next()) {
				Phone item = new Phone();
				item.setID(rs.getInt("ID"));
				item.setPhoneName(rs.getString("phoneName"));
				item.setNote(rs.getString("note"));
				item.setPhoneNumber(rs.getString("phoneNumber"));
				item.setBandType(rs.getString("bandType"));
				item.setResolution(rs.getString("resolutionName"));
				item.setInventory(rs.getInt("inventory"));
				item.setPrice(rs.getFloat("price"));
				item.setProduceTime(rs.getString("produceTime"));
				item.setOsType(rs.getString("osType"));
				item.setManufacturers(rs.getString("manufacturersName"));
				item.setEditor(rs.getString("editorName"));
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
	 * 按照手机名查id
	 * @param name
	 * @return
	 */
	public int QueryIDByPhoneName(String name) {
		String sql = "select ID from Phones where phoneName = ?";
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
	 * 查询手机本数目
	 * @return
	 */
	public int QueryCount(){
		String sql = "select * from Phones";
		return MyDbHelper.getCount(sql);
	}
	
	/**
	 *按照id查询手机名
	 * @param ID
	 * @return
	 */
	public String QueryPhoneNameByID(int ID) {
		String sql = "select phoneName from Phones where ID = ?";
		Object[] parameters = {ID};
		ResultSet rs = MyDbHelper.executeQuery(sql,parameters);
		try {
			while(rs.next()) {
				return rs.getString("phoneName");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 按照id查手机号
	 * @param ID
	 * @return
	 */
	public String QueryPhoneNumberByID(int ID) {
		String sql = "select phoneNumber from Phones where ID = ?";
		Object[] parameters = {ID};
		ResultSet rs = MyDbHelper.executeQuery(sql,parameters);
		try {
			while(rs.next()) {
				return rs.getString("phoneNumber");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 手机名是否存在
	 * @param phoneName
	 * @return
	 */
	public boolean phoneNameIsExist(String phoneName){
		String sql = "select * from Phones where phoneName = ?";
		Object[] parameters = {phoneName};
		if( MyDbHelper.getCount(sql, parameters) > 0)
			return true;
		return false;
	}
	
	/**
	 * 手机型号是否存在
	 * @param phoneNumber
	 * @return
	 */
	public boolean phoneNumberIsExist(String phoneNumber){
		String sql = "select * from Phones where phoneNumber = ?";
		Object[] parameters = {phoneNumber};
		if( MyDbHelper.getCount(sql, parameters) > 0)
			return true;
		return false;
	}
}
