package model;

public class UsersRights {

	private int ID;      //�û�Ȩ�޷����id
	private int userID;  //�û�id
	private int rightID; //Ȩ��id
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public int getRightID() {
		return rightID;
	}
	public void setRightID(int rightID) {
		this.rightID = rightID;
	}
}
