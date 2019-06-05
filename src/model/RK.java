package model;

import java.text.DecimalFormat;
import java.util.Date;

public class RK {
	private int ID;
	private Date inDate;
	private String phoneNumber;
	private int totalNumber;
	private String note;
	private float price;
	private String phoneName;
	private String bandTypeName;
	private int count;
	
	public Object get(int number) {
		switch(number) {
		case 0: return phoneName;
		case 1: return phoneNumber;
		case 2: return bandTypeName;
		case 3: return inDate;
		case 4: return totalNumber;
		case 5: return count;
		case 6: return new DecimalFormat("##,##0.00").format(price);
		case 7: return new DecimalFormat("##,##0.00").format(price * totalNumber);
		}
		return null;
	}
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public Date getInDate() {
		return inDate;
	}
	public void setInDate(Date inDate) {
		this.inDate = inDate;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public int getTotalNumber() {
		return totalNumber;
	}
	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getPhoneName() {
		return phoneName;
	}
	public void setPhoneName(String phoneName) {
		this.phoneName = phoneName;
	}
	public String getBandTypeName() {
		return bandTypeName;
	}
	public void setBandTypeName(String bandTypeName) {
		this.bandTypeName = bandTypeName;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
}
