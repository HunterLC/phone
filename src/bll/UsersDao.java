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
	public static Users queryUser;  //用于未登录成功时查询用户信息
	public List<Users> userList = new ArrayList<Users>();  //获取用户名列表
	public static UsersDao getInstance(){  //获得一个实例
		return new UsersDao();
	}
	public int checkAccount(String account,String password) throws SQLException                 //验证账号正确性
	{
		//单条数据查询		
		String sql =  "select * from Users where username=?";
		Object[] parameters= {account};   //数据传入
		//查询
		ResultSet rs = MyDbHelper.executeQuery(sql, parameters);  //执行
		while(rs.next())
		{
			String a = null;
			try {
				a = rs.getString("username");          //获取账号
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String b = null;
			try {
				b = rs.getString("Password");        //获取密码
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(a.equals(account)&&b.equals(password))     //密码匹配
			{
				MyUser = account;           //保存当前登录用户信息
				loginUser = new Users();
				loginUser.setID(rs.getInt("ID"));
				loginUser.setUsername(a);
				loginUser.setPassword(b);
				loginUser.setFault_time(rs.getInt("fault_time"));
				loginUser.setUnlock_time(rs.getString("unlock_time"));
				return 1;
			}
			else if(a.equals(account) == true && b.equals(password) == false){  //账户存在，密码错误
				return -1;
			}		
		}
		MyDbHelper.free(rs);
		return 0;   //账户不存在
	}
	
	public boolean setFault_Time(int fault_time,String username){   //设置连续登录失败次数
		String sql = "update Users set fault_time = ? where username = ?";
		Object[] parameters = {fault_time,username};
		if( MyDbHelper.executeNonQuery(sql, parameters) > 0){
			if(loginUser != null)
				loginUser.setFault_time(fault_time);   //程序保留数据与数据库一致
			if(queryUser != null)
				queryUser.setFault_time(fault_time);   //程序保留数据与数据库一致
			return true;
		}	
		return false;
	}
	
	public boolean setUnlock_Time(String unlock_time,String username){
		String sql = "update Users set unlock_time = ? where username = ?";
		Object[] parameters = {unlock_time,username};
		if( MyDbHelper.executeNonQuery(sql, parameters) > 0){
			if(loginUser != null)
				loginUser.setUnlock_time(unlock_time);   //程序保留数据与数据库一致
			if(queryUser != null)
				queryUser.setUnlock_time(unlock_time);   //程序保留数据与数据库一致
			return true;
		}
		return false;
	}
	
	public boolean QueryUser(String username){
		String sql = "select * from Users where username = ?";
		Object[] parameters = {username};
		ResultSet rs = MyDbHelper.executeQuery(sql, parameters);  //执行
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
	 * 查询所有用户
	 * @return
	 */
	public List<Users> QueryUser(){
		String sql = "select * from Users";
		ResultSet rs = MyDbHelper.executeQuery(sql);  //执行
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
	 * 查询用户id
	 * @param username
	 * @return
	 */
	public int QueryID(String username){
		String sql = "select * from Users where username = ?";
		Object[] parameters = {username};
		ResultSet rs = MyDbHelper.executeQuery(sql,parameters);  //执行
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
	 * 查询用户密码
	 * @param username
	 * @return
	 */
	public String QueryPassword(String username){
		String sql = "select * from Users where username = ?";
		Object[] parameters = {username};
		ResultSet rs = MyDbHelper.executeQuery(sql,parameters);  //执行
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
	 * 增加新用户
	 * @param username
	 * @param password
	 * @return
	 */
	public int AddUser(String username,String password){
		String sql = "insert into Users values(?,?,?,?)";
		Object[] parameters = {username,password,0,null};
		if( MyDbHelper.executeNonQuery(sql, parameters) != 0)//执行影响行数不为0
			return 1;
		return 0;
	}
	
	/**
	 * 修改用户信息
	 * @param username
	 * @param password
	 * @param id
	 * @return
	 */
	public int UpdateUser(String username, String password,int id){
		String sql = "update Users set username = ?,Password = ? where ID = ?";
		Object[] parameters = {username,password,id};
		if( MyDbHelper.executeNonQuery(sql, parameters) != 0)//执行影响行数不为0
			return 1;
		return 0;
	}
	
	/**
	 * 更新用户信息密码
	 * @param username
	 * @param password
	 * @return
	 */
	public int UpdatePassword(String username,String password){
		String sql = "update Users set Password = ? where username = ?";
		Object[] parameters = {password,username};
		if( MyDbHelper.executeNonQuery(sql, parameters) != 0)//执行影响行数不为0
			return 1;
		return 0;
	}
	
	/**
	 * 删除用户
	 * @param username
	 * @return
	 */
	public int DeleteUser(String username){
		String sql = "delete from Users where username = ?";
		Object[] parameters = {username};
		if( MyDbHelper.executeNonQuery(sql, parameters) != 0)//执行影响行数不为0
			return 1;
		return 0;
	}
	
	/**
	 * 判断用户是否存在
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
	 * 按照用户id查询用户所有信息
	 * @param id
	 * @return
	 */
	public Users QueryUserByID(int id){
		String sql = "select * from Users where ID = ?";
		Object[] parameters = {id};
		ResultSet rs = MyDbHelper.executeQuery(sql,parameters);  //执行
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
	 * 查询用户人数
	 * @return
	 */
	public int QueryUsersCount(){
		String sql = "select * from Users";
		return MyDbHelper.getCount(sql);
	}
}
