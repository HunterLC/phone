package model;

public class Users {
	private int ID;  //��������ʶ��
	private String username;//�û���
	private String Password; //����
	private int fault_time;  //������¼ʧ�ܵĴ���
	private String unlock_time;//�Զ�������ʱ��
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public int getFault_time() {
		return fault_time;
	}
	public void setFault_time(int fault_time) {
		this.fault_time = fault_time;
	}
	public String getUnlock_time() {
		return unlock_time;
	}
	public void setUnlock_time(String unlock_time) {
		this.unlock_time = unlock_time;
	}
	
}
