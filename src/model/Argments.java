package model;

public class Argments {
	private int ID; //��������ʶ��
	private int num;//����������½������
	private int days; //����֮ǰ�������¼�����ھ������������
	private int lock_days;  //�˻�����������
	
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
