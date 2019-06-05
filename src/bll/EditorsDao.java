package bll;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dal.MyDbHelper;
import model.Editors;

public class EditorsDao {
	
	public static EditorsDao getInstance() {
		return new EditorsDao();
	}
	
	/**
	 * ��ѯ���еı༭����Ϣ
	 * @return
	 */
	public List<Editors> QueryAll() {
		List<Editors> list = new ArrayList<Editors>();
		String sql = "select * from Editors order by editorName";
		ResultSet rs = MyDbHelper.executeQuery(sql);
		try {
			while(rs.next()) {
				Editors item = new Editors();
				item.setID(rs.getInt("ID"));
				item.setEditorName(rs.getString("editorName"));
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
	 * ���ձ༭������ѯ�༭��ID
	 * @param name
	 * @return
	 */
	public int QueryIDByName(String name) {
		String sql = "select ID from Editors where editorName = ?";
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
	 * ����id��ѯ�༭����
	 * @param ID
	 * @return
	 */
	public String QueryNameByID(int ID) {
		String sql = "select editorName from Editors where ID = ?";
		Object[] parameters = {ID};
		ResultSet rs = MyDbHelper.executeQuery(sql,parameters);
		try {
			while(rs.next()) {
				return rs.getString("editorName");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * ��ѯ�༭����Ŀ
	 * @return
	 */
	public int QueryCount(){
		String sql = "select * from Editors";
		return MyDbHelper.getCount(sql);
	}
	
	/**
	 * �жϱ༭���Ƿ����
	 * @return
	 */
	public boolean IsExist(String editorName){
		String sql = "select * from Editors where editorName = ?";
		Object[] parameters = {editorName};
		if( MyDbHelper.getCount(sql, parameters) > 0)
			return true;
		return false;
	}
	
	/**
	 * ����id�޸ı༭����
	 * @param editorName
	 * @param id
	 * @return
	 */
	public int Update(String editorName,int id){
		String sql = "update Editors set editorName = ? where ID = ?";
		Object[] parameters = {editorName,id};
		if( MyDbHelper.executeNonQuery(sql, parameters) != 0)//ִ��Ӱ��������Ϊ0
			return 1;
		return 0;
	}
	
	/**
	 * ���ݱ༭����ɾ��
	 * @param editorName
	 * @return
	 */
	public int Delete(String editorName){
		String sql = "delete from Editors where editorName = ?";
		Object[] parameters = {editorName};
		if( MyDbHelper.executeNonQuery(sql, parameters) != 0)//ִ��Ӱ��������Ϊ0
			return 1;
		return 0;
	}
	
	/**
	 * ��ӱ༭��
	 * @param editorName
	 * @return
	 */
	public int Add(String editorName){
		String sql = "insert into Editors values(?)";
		Object[] parameters = {editorName};
		if( MyDbHelper.executeNonQuery(sql, parameters) != 0)//ִ��Ӱ��������Ϊ0
			return 1;
		return 0;
	}
}
