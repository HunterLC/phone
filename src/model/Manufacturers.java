package model;

public class Manufacturers {
	private int ID;
	private int Num;
	private String manufacturersName;
	private String isUse;
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getNum() {
		return Num;
	}
	public void setNum(int num) {
		Num = num;
	}
	public String getManufacturersName() {
		return manufacturersName;
	}
	public void setManufacturersName(String manufacturersName) {
		this.manufacturersName = manufacturersName;
	}
	public String getIsUse() {
		return isUse;
	}
	public void setIsUse(String isUse) {
		this.isUse = isUse;
	}
}
