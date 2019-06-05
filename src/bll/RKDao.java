package bll;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dal.MyDbHelper;
import model.RK;

public class RKDao {
	public static RKDao getInstance(){  //获得一个实例
		return new RKDao();
	}
	
	public List<RK> QueryAll(String time) {
		List<RK> list = new ArrayList<RK>();
		String sql = "select RK.ID,Rk.inDate,RK.phoneNumber,RK.totalNumber,RK.note,Phone.phoneName,Phone.price from RK,Phone where RK.phoneNumber=Phone.phoneNumber and Rk.inDate= \'"+time+"\'";
		ResultSet rs = MyDbHelper.executeQuery(sql);
		try {
			while(rs.next()) {
				RK item = new RK();
				item.setID(rs.getInt("ID"));
				item.setInDate(rs.getDate("inDate"));
				item.setNote(rs.getString("note"));
				item.setPhoneName(rs.getString("phoneName"));
				item.setPhoneNumber(rs.getString("phoneNumber"));
				item.setPrice(rs.getFloat("price"));
				item.setTotalNumber(rs.getInt("totalNumber"));
				list.add(item);
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public List<RK> QueryStatistic(String time,int searchType) {
		List<RK> list = new ArrayList<RK>();
		System.out.println(time);
		String[] sArray=time.split("-") ;
		System.out.println(sArray[0]);
		System.out.println(sArray[1]);
		System.out.println(sArray[2]);
		String sql = null;
		if(searchType == 1) {
			time = sArray[0];
			System.out.println("查询时间："+time);
			sql = "select RK.ID,Rk.inDate,RK.phoneNumber,RK.totalNumber,RK.note,Phones.phoneName,Phones.price,Phones.bandType from RK,Phones where RK.phoneNumber=Phones.phoneNumber and Rk.inDate like \'%"+time+"%\'";
		}	
		else if(searchType == 2){
			time = sArray[0]+"-"+sArray[1];
			System.out.println("查询时间："+time);
			sql = "select RK.ID,Rk.inDate,RK.phoneNumber,RK.totalNumber,RK.note,Phones.phoneName,Phones.price,Phones.bandType from RK,Phones where RK.phoneNumber=Phones.phoneNumber and Rk.inDate like \'%"+time+"%\'";
		}
		else
			sql = "select RK.ID,Rk.inDate,RK.phoneNumber,RK.totalNumber,RK.note,Phones.phoneName,Phones.price,Phones.bandType from RK,Phones where RK.phoneNumber=Phones.phoneNumber and Rk.inDate= \'"+time+"\'";
		ResultSet rs = MyDbHelper.executeQuery(sql);
		try {
			while(rs.next()) {
				RK item = new RK();
				item.setID(rs.getInt("ID"));
				item.setInDate(rs.getDate("inDate"));
				item.setNote(rs.getString("note"));
				item.setPhoneName(rs.getString("phoneName"));
				item.setPhoneNumber(rs.getString("phoneNumber"));
				item.setPrice(rs.getFloat("price"));
				item.setTotalNumber(rs.getInt("totalNumber"));
				item.setBandTypeName(rs.getString("bandType"));
				item.setCount(QueryCount(item.getPhoneNumber(),item.getInDate().toString(),searchType));
				System.out.println(item.getCount());
				list.add(item);
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public int Add(String inDate,String phoneNumber,String totalNumber,String note){
		String sql = "insert into RK values(?,?,?,?)";
		Object[] parameters = {inDate,phoneNumber,totalNumber,note};
		if( MyDbHelper.executeNonQuery(sql, parameters) != 0)//执行影响行数不为0
			return 1;
		return 0;
	}
	
	public int QueryCount(String phoneNumber,String time,int searchType) {
		String[] sArray=time.split("-") ;
		String sql = null;
		if(searchType == 1) {
			time = sArray[0];
			sql = "select * from RK where phoneNumber=? and inDate like \'%"+time+"%\'";
		}else if(searchType == 2) {
			time = sArray[0]+"-"+sArray[1];
			sql = "select * from RK where phoneNumber=? and inDate like \'%"+time+"%\'";
		}
		else
			sql = "select * from RK where phoneNumber=? and inDate= \'"+time+"\'";
		Object[] parameters = {phoneNumber};
		return MyDbHelper.getCount(sql,parameters);
	}
}
