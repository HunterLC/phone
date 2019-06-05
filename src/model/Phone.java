package model;

import java.text.DecimalFormat;

public class Phone {
	private int ID;
	private String phoneName;
	private String note;
	private String phoneNumber;
	private String bandType;
	private String resolution;
	private int inventory;
	private float price;
	private String produceTime;
	private String osType;
	private String manufacturers;
	private String editor;
	
	public Object get(int number) {
		switch(number) {
		case 0: return phoneName;
		case 1: return phoneNumber;
		case 2: return bandType;
		case 3: return manufacturers;
		case 4: return produceTime;
		case 5: return inventory;
		case 6: return new DecimalFormat("##,##0.00").format(price);
		case 7: return new DecimalFormat("##,##0.00").format(price * inventory);
		}
		return null;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getPhoneName() {
		return phoneName;
	}
	public void setPhoneName(String phoneName) {
		this.phoneName = phoneName;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getBandType() {
		return bandType;
	}
	public void setBandType(String bandType) {
		this.bandType = bandType;
	}
	public String getResolution() {
		return resolution;
	}
	public void setResolution(String resolution) {
		this.resolution = resolution;
	}
	public int getInventory() {
		return inventory;
	}
	public void setInventory(int inventory) {
		this.inventory = inventory;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getProduceTime() {
		return produceTime;
	}
	public void setProduceTime(String produceTime) {
		this.produceTime = produceTime;
	}
	public String getOsType() {
		return osType;
	}
	public void setOsType(String osType) {
		this.osType = osType;
	}
	public String getManufacturers() {
		return manufacturers;
	}
	public void setManufacturers(String manufacturers) {
		this.manufacturers = manufacturers;
	}
	public String getEditor() {
		return editor;
	}
	public void setEditor(String editor) {
		this.editor = editor;
	}
}
