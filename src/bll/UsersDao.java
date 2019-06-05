package bll;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dal.MyDbHelper;
import model.Users;

public class UsersDao {

	public static String MyUser = null;
	public static Users loginUser;
	public static Users queryUser;  //����δ��¼�ɹ�ʱ��ѯ�û���Ϣ
	public List<Users> userList = new ArrayList<Users>();  //��ȡ�û����б�
	public static UsersDao getInstance(){  //���һ��ʵ��
		return new UsersDao();
	}
	public int checkAccount(String account,String password) throws SQLException                 //��֤�˺���ȷ��
	{
		//�������ݲ�ѯ		
		String sql =  "select * from Users where username=?";
		Object[] parameters= {account};   //���ݴ���
		//��ѯ
		ResultSet rs = MyDbHelper.executeQuery(sql, parameters);  //ִ��
		while(rs.next())
		{
			String a = null;
			try {
				a = rs.getString("username");          //��ȡ�˺�
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String b = null;
			try {
				b = rs.getString("Password");        //��ȡ����
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(a.equals(account)&&b.equals(password))     //����ƥ��
			{
				MyUser = account;           //���浱ǰ��¼�û���Ϣ
				loginUser = new Users();
				loginUser.setID(rs.getInt("ID"));
				loginUser.setUsername(a);
				loginUser.setPassword(b);
				loginUser.setFault_time(rs.getInt("fault_time"));
				loginUser.setUnlock_time(rs.getString("unlock_time"));
				return 1;
			}
			else if(a.equals(account) == true && b.equals(password) == false){  //�˻����ڣ��������
				return -1;
			}		
		}
		MyDbHelper.free(rs);
		return 0;   //�˻�������
	}
	
	public boolean setFault_Time(int fault_time,String username){   //����������¼ʧ�ܴ���
		String sql = "update Users set fault_time = ? where username = ?";
		Object[] parameters = {fault_time,username};
		if( MyDbHelper.executeNonQuery(sql, parameters) > 0){
			if(loginUser != null)
				loginUser.setFault_time(fault_time);   //���������������ݿ�һ��
			if(queryUser != null)
				queryUser.setFault_time(fault_time);   //���������������ݿ�һ��
			return true;
		}	
		return false;
	}
	
	public boolean setUnlock_Time(String unlock_time,String username){
		String sql = "update Users set unlock_time = ? where username = ?";
		Object[] parameters = {unlock_time,username};
		if( MyDbHelper.executeNonQuery(sql, parameters) > 0){
			if(loginUser != null)
				loginUser.setUnlock_time(unlock_time);   //���������������ݿ�һ��
			if(queryUser != null)
				queryUser.setUnlock_time(unlock_time);   //���������������ݿ�һ��
			return true;
		}
		return false;
	}
	
	public boolean QueryUser(String username){
		String sql = "select * from Users where username = ?";
		Object[] parameters = {username};
		ResultSet rs = MyDbHelper.executeQuery(sql, parameters);  //ִ��
		try {
			while(rs.next()){
				queryUser = new Users();
				queryUser.setID(rs.getInt("ID"));
				queryUser.setUsername(rs.getString("username"));
				queryUser.setPassword(rs.getString("Password"));
				queryUser.setFault_time(rs.getInt("fault_time"));
				queryUser.setUnlock_time(rs.getString("unlock_time"));
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * ��ѯ�����û�
	 * @return
	 */
	public List<Users> QueryUser(){
		String sql = "select * from Users";
		ResultSet rs = MyDbHelper.executeQuery(sql);  //ִ��
		try {
			while(rs.next()){
				Users user = new Users();
				user.setID(rs.getInt("ID"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("Password"));
				userList.add(user);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userList;
	}
	
	/**
	 * ��ѯ�û�id
	 * @param username
	 * @return
	 */
	public int QueryID(String username){
		String sql = "select * from Users where username = ?";
		Object[] parameters = {username};
		ResultSet rs = MyDbHelper.executeQuery(sql,parameters);  //ִ��
		try {
			while(rs.next()){
				return rs.getInt("ID");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * ��ѯ�û�����
	 * @param username
	 * @return
	 */
	public String QueryPassword(String username){
		String sql = "select * from Users where username = ?";
		Object[] parameters = {username};
		ResultSet rs = MyDbHelper.executeQuery(sql,parameters);  //ִ��
		try {
			while(rs.next()){
				return rs.getString("Password");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * �������û�
	 * @param username
	 * @param password
	 * @return
	 */
	public int AddUser(String username,String password){
		String sql = "insert into Users values(?,?,?,?)";
		Object[] parameters = {username,password,0,null};
		if( MyDbHelper.executeNonQuery(sql, parameters) != 0)//ִ��Ӱ��������Ϊ0
			return 1;
		return 0;
	}
	
	/**
	 * �޸��û���Ϣ
	 * @param username
	 * @param password
	 * @param id
	 * @return
	 */
	public int UpdateUser(String username, String password,int id){
		String sql = "update Users set username = ?,Password = ? where ID = ?";
		Object[] parameters = {username,password,id};
		if( MyDbHelper.executeNonQuery(sql, parameters) != 0)//ִ��Ӱ��������Ϊ0
			return 1;
		return 0;
	}
	
	/**
	 * �����û���Ϣ����
	 * @param username
	 * @param password
	 * @return
	 */
	public int UpdatePassword(String username,String password){
		String sql = "update Users set Password = ? where username = ?";
		Object[] parameters = {password,username};
		if( MyDbHelper.executeNonQuery(sql, parameters) != 0)//ִ��Ӱ��������Ϊ0
			return 1;
		return 0;
	}
	
	/**
	 * ɾ���û�
	 * @param username
	 * @return
	 */
	public int DeleteUser(String username){
		String sql = "delete from Users where username = ?";
		Object[] parameters = {username};
		if( MyDbHelper.executeNonQuery(sql, parameters) != 0)//ִ��Ӱ��������Ϊ0
			return 1;
		return 0;
	}
	
	/**
	 * �ж��û��Ƿ����
	 * @return
	 */
	public boolean UserIsExist(String username){
		String sql = "select * from Users where username = ?";
		Object[] parameters = {username};
		if( MyDbHelper.getCount(sql, parameters) > 0)
			return true;
		return false;
	}
	
	/**
	 * �����û�id��ѯ�û�������Ϣ
	 * @param id
	 * @return
	 */
	public Users QueryUserByID(int id){
		String sql = "select * from Users where ID = ?";
		Object[] parameters = {id};
		ResultSet rs = MyDbHelper.executeQuery(sql,parameters);  //ִ��
		try {
			while(rs.next()){
				Users user = new Users();
				user.setID(rs.getInt("ID"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("Password"));
				return user;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * ��ѯ�û�����
	 * @return
	 */
	public int QueryUsersCount(){
		String sql = "select * from Users";
		return MyDbHelper.getCount(sql);
	}
}
