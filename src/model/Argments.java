package model;

public class Argments {
	private int ID; //主键，标识列
	private int num;//允许连续登陆的天数
	private int days; //今天之前，允许登录的日期距今天的最大天数
	private int lock_days;  //账户锁定的天数
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getDays() {
		return days;
	}
	public void setDays(int days) {
		this.days = days;
	}
	public int getLock_days() {
		return lock_days;
	}
	public void setLock_days(int lock_days) {
		this.lock_days = lock_days;
	}
}
