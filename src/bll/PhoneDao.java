package bll;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dal.MyDbHelper;
import model.Phone;

public class PhoneDao {
	public static PhoneDao getInstance() {
		return new PhoneDao();
	}
	
	public List<Phone> QueryAll(String searchText,String sortType){
		//Book类不同于Phone之处为Phone对应数据库原始数据，而Book对应表格展示数据
		List<Phone> list = new ArrayList<Phone>();
		String sql = null;
		ResultSet rs = null;
		if(searchText == null||searchText.equals("")) {
			sql = "select Phone.ID,phoneName,note,Phone.phoneNumber,bandType,resolutionName,Inventory.inventory,price,produceTime,osType,manufacturersName,editorName"
					+" from Phone,bandType,Manufacturers,OsType,Editors,Resolution,Inventory"
					+" where Phone.bandTypeID=bandType.ID and Phone.resolutionID=Resolution.ID and Phone.osTypeID=OsType.ID and Phone.manufacturersID=Manufacturers.ID and Phone.editorID=Editors.ID and Phone.inventoryID=Inventory.ID"
					+" order by "+sortType;
			rs = MyDbHelper.executeQuery(sql);
		}
			
		else {
			sql = "select Phone.ID,phoneName,note,Phone.phoneNumber,bandType,resolutionName,Inventory.inventory,price,produceTime,osType,manufacturersName,editorName"
					+" from Phone,bandType,Manufacturers,OsType,Editors,Resolution,Inventory"
					+" where Phone.bandTypeID=bandType.ID and Phone.resolutionID=Resolution.ID and Phone.osTypeID=OsType.ID and Phone.manufacturersID=Manufacturers.ID and Phone.editorID=Editors.ID and Phone.inventoryID=Inventory.ID"
					//+" and Phone.ID,phoneName,note,phoneNumber,bandType,resolutionName,inventory,price,produceTime,OsTypeName,Manufacturers,editorName like %" //增加搜索
					+" and (Phone.ID like '%" //增加搜索
					+searchText
					+"%' or"
					+" phoneName like '%"
					+searchText
					+"%' or"
					+" note like '%"
					+searchText
					+"%' or"
					+" Phone.phoneNumber like '%" 
					+searchText
					+"%' or"
					+" bandType like '%"
					+searchText
					+"%' or"
					+" resolutionName like '%"
					+searchText
					+"%' or"
					+" inventory like '%" 
					+searchText
					+"%' or"
					+" price like '%"
					+searchText
					+"%' or"
					+" produceTime like '%"
					+searchText
					+"%' or"
					+" osType like '%" 
					+searchText
					+"%' or"
					+" manufacturersName like '%"
					+searchText
					+"%' or"
					+" editorName like '%"
					+searchText
					+"%'"
					+") order by "+sortType;
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
	
	public int QueryIDByphoneName(String name) {
		String sql = "select ID from Phone where phoneName = ?";
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
	
	public int Update(String phoneName,String note,String phoneNumber,int bandTypeID,int resolutionID,int inventoryID,float price,String produceTime,int osTypeID,int manufacturersID,int editorID,int id){
		String sql = "update Phone set phoneName = ?,note = ?,phoneNumber = ?,bandTypeID = ?,resolutionID = ?,inventoryID = ?,price = ?,produceTime = ?,osTypeID = ?,manufacturersID = ?,editorID = ? where ID = ?";
		Object[] parameters = {phoneName,note,phoneNumber,bandTypeID,resolutionID,inventoryID,price,produceTime,osTypeID,manufacturersID,editorID,id};
		if( MyDbHelper.executeNonQuery(sql, parameters) != 0)//执行影响行数不为0
			return 1;
		return 0;
	}
	

	public int Delete(String phoneName){
		String sql = "delete from Phone where phoneName = ?";
		Object[] parameters = {phoneName};
		if( MyDbHelper.executeNonQuery(sql, parameters) != 0)//执行影响行数不为0
			return 1;
		return 0;
	}
	
	public int Add(String phoneName,String note,String phoneNumber,int bandTypeID,int resolutionID,int inventoryID,float price,String produceTime,int osTypeID,int manufacturersID,int editorID){
		String sql = "insert into Phone values(?,?,?,?,?,?,?,?,?,?,?)";
		Object[] parameters = {phoneName,note,phoneNumber,bandTypeID,resolutionID,inventoryID,price,produceTime,osTypeID,manufacturersID,editorID};
		if( MyDbHelper.executeNonQuery(sql, parameters) != 0)//执行影响行数不为0
			return 1;
		return 0;
	}
	
	public int QueryCount(){
		String sql = "select * from Phone";
		return MyDbHelper.getCount(sql);
	}
	
	public String QueryphoneNameByID(int ID) {
		String sql = "select phoneName from Phone where ID = ?";
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
	
	public String QueryphoneNumberByID(int ID) {
		String sql = "select phoneNumber from Phone where ID = ?";
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
	
	public boolean phoneNameIsExist(String phoneName){
		String sql = "select * from Phone where phoneName = ?";
		Object[] parameters = {phoneName};
		if( MyDbHelper.getCount(sql, parameters) > 0)
			return true;
		return false;
	}
	public boolean phoneNumberIsExist(String phoneNumber){
		String sql = "select * from Phone where phoneNumber = ?";
		Object[] parameters = {phoneNumber};
		if( MyDbHelper.getCount(sql, parameters) > 0)
			return true;
		return false;
	}
}
