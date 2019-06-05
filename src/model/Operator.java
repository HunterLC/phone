package model;

public class Operator {
	private int ID;               //表主键id
	private int userID;           //记录所属者id
	private String logicalSignal; //逻辑运算符
	private String segment;       //字段名
	private String signal;        //运算符
	private String valueData;     //值
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
	public String getLogicalSignal() {
		return logicalSignal;
	}
	public void setLogicalSignal(String logicalSignal) {
		this.logicalSignal = logicalSignal;
	}
	public String getSegment() {
		return segment;
	}
	public void setSegment(String segment) {
		this.segment = segment;
	}
	public String getSignal() {
		return signal;
	}
	public void setSignal(String signal) {
		this.signal = signal;
	}
	public String getValueData() {
		return valueData;
	}
	public void setValueData(String valueData) {
		this.valueData = valueData;
	}
}
