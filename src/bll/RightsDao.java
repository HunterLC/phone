package bll;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dal.MyDbHelper;
import model.Rights;

public class RightsDao {

	public static RightsDao getInstance(){  //���һ��ʵ��
		return new RightsDao();
	}
	
	/**
	 * ��ѯ����Ȩ��
	 * @return
	 */
	public List<Rights> QueryAllRights(){
		List<Rights> rightsList = new ArrayList<Rights>();
		String sql = "select * from Rights";
		ResultSet rs = MyDbHelper.executeQuery(sql);  //ִ��
		try {
			while(rs.next()){
				Rights rights = new Rights();
				rights.setId(rs.getInt("id"));
				rights.setRightNO(rs.getInt("rightNO"));
				rights.setRightName(rs.getString("rightName"));
				rights.setModule(rs.getString("Module"));
				rights.setW_name(rs.getString("W_name"));
				rightsList.add(rights);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rightsList;
	}
	
	/**
	 * ����Ȩ�������Ҹ�Ȩ�޵�id
	 * @param name
	 * @return
	 */
	public int QueryByRightName(String name){
		String sql = "select id from Rights where rightName = ?";
		Object[] parameters = {name};
		ResultSet rs = MyDbHelper.executeQuery(sql, parameters);
		try {
			while(rs.next()){
				return rs.getInt("id");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * ����Ȩ������ѯ������
	 * @param name
	 * @return
	 */
	public String QueryW_Name(String name){
		String sql = "select W_name from Rights where rightName = ?";
		Object[] parameters = {name};
		ResultSet rs = MyDbHelper.executeQuery(sql, parameters);
		try {
			while(rs.next()){
				return rs.getString("W_name");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
