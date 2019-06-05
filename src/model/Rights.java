package model;

public class Rights {

	private int id; //权限表id
	private int rightNO; //权限编号
	private String rightName; //权限名
	private String Module;   //模块名
	private String W_name;   //窗口
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRightNO() {
		return rightNO;
	}
	public void setRightNO(int rightNO) {
		this.rightNO = rightNO;
	}
	public String getRightName() {
		return rightName;
	}
	public void setRightName(String rightName) {
		this.rightName = rightName;
	}
	public String getModule() {
		return Module;
	}
	public void setModule(String module) {
		Module = module;
	}
	public String getW_name() {
		return W_name;
	}
	public void setW_name(String w_name) {
		W_name = w_name;
	}
}
