package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import bll.ManufacturersDao;
import bll.UsersDao;
import model.Manufacturers;
import model.Users;
import util.MyProperties;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;

public class Manage_Manufacturers extends JFrame {

	private JPanel contentPane,listPanel,settingPanel;
	private JTextField nameTextField;
	private JScrollPane scrollPane;
	private JTable listTable;
	private DefaultTableModel tableModel;
	private TitledBorder titledBorder,titledBorder1;
	private String head[]=null;  //表格列名
	private Object [][]data=null;   //表格行表
	private JLabel nameLabel,label,isUse;
	private JButton saveButton,deleteButton,resetButton,closeButton;
	private static int CURRENTID = 0;
	private JTextField numTextField;
	private JRadioButton yesRadioButton,noRadioButton;
	private ButtonGroup selectedGroup;

	
	/**
	 * Create the frame.
	 */
	public Manage_Manufacturers() {
		setResizable(false);
		try
	    {		
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencySmallShadow;			
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();			
			UIManager.put("RootPane.setupButtonVisible", false);
	      //设置此开关量为false即表示关闭之，BeautyEye LNF中默认是true
	        BeautyEyeLNFHelper.translucencyAtFrameInactive = false;
	    }
	    catch(Exception e)
	    {
	        //TODO exception
	    }
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 697, 554);
		setTitle("\u751F\u4EA7\u5382\u5BB6\u7BA1\u7406");
		setVisible(true);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		listPanel = new JPanel();
		titledBorder = new TitledBorder(UIManager.getBorder("TitledBorder.border"), "共"+ManufacturersDao.getInstance().QueryCount()+"项", TitledBorder.LEADING, TitledBorder.TOP, null, Color.RED);
		titledBorder.setTitleFont(new Font("宋体", Font.PLAIN, 20));
		listPanel.setBorder(titledBorder);
		listPanel.setBounds(14, 0, 345, 494);
		contentPane.add(listPanel);
		listPanel.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(14, 24, 317, 457);
		listPanel.add(scrollPane);
		
		//初始化表格
		listTable = new JTable();
		listTable.setRowHeight(25);
		listTable.setBorder(new LineBorder(new Color(0, 0, 0)));
		head=new String[] {"序号","生产厂家名称","是否在用"};
		tableModel=new DefaultTableModel(queryData(),head){
		public boolean isCellEditable(int row, int column)
		{
			return false;
			}
		};
		listTable.setFont(new Font("宋体", Font.BOLD, 14));
		listTable.addMouseListener(new MouseAdapter() {   //用户名表格点击事件
			@Override
			public void mouseClicked(MouseEvent arg0) {
				titledBorder1.setTitle("修改");
				settingPanel.repaint();
				//填充生产厂家编辑框
				//String num = Integer.valueOf(listTable.getValueAt(listTable.getSelectedRow(), 0).toString());
				String num = listTable.getValueAt(listTable.getSelectedRow(), 0).toString();
				String bjsName = listTable.getValueAt(listTable.getSelectedRow(), 1).toString();
				String isUse = listTable.getValueAt(listTable.getSelectedRow(), 2).toString();
				nameTextField.setText(bjsName);
				numTextField.setText(num);
				if(isUse.equals("是"))
					yesRadioButton.setSelected(true);
				else
					noRadioButton.setSelected(true);
				CURRENTID = ManufacturersDao.getInstance().QueryIDByName(bjsName);//获得当前选中生产厂家的id
				deleteButton.setEnabled(true);
				resetButton.setEnabled(true);
			}
		});
		DefaultTableCellRenderer r = new DefaultTableCellRenderer();
		r.setHorizontalAlignment(JLabel.CENTER);
		listTable.setDefaultRenderer(Object.class, r);
		listTable.setModel(tableModel);
		scrollPane.setViewportView(listTable);
				
		settingPanel = new JPanel();
		titledBorder1 = new TitledBorder(null, "新增", TitledBorder.LEADING, TitledBorder.TOP, null, Color.GRAY);
		titledBorder1.setTitleFont(new Font("宋体", Font.PLAIN, 20));
		settingPanel.setBorder(titledBorder1);
		settingPanel.setBounds(395, 0, 261, 494);
		contentPane.add(settingPanel);
		settingPanel.setLayout(null);
		
		nameLabel = new JLabel("生产厂家名称");
		nameLabel.setBounds(14, 165, 120, 31);
		settingPanel.add(nameLabel);
		nameLabel.setFont(new Font("宋体", Font.PLAIN, 20));
		
		nameTextField = new JTextField();
		nameTextField.setBounds(14, 202, 220, 31);
		settingPanel.add(nameTextField);
		nameTextField.setFont(new Font("宋体", Font.PLAIN, 20));
		nameTextField.setColumns(10);
		
		saveButton = new JButton("保存");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String bjsName = nameTextField.getText();
				String num = numTextField.getText();
				String isUse = yesRadioButton.isSelected() ? "是" : "否";
				if(num.equals("")) //排序号为空
					JOptionPane.showMessageDialog(null,"排序号为空","提示",JOptionPane.INFORMATION_MESSAGE);
				else if(bjsName.equals("")) {//生产厂家名为空
					JOptionPane.showMessageDialog(null,"生产厂家名为空","提示",JOptionPane.INFORMATION_MESSAGE);
				}
				else if(!yesRadioButton.isSelected() && !noRadioButton.isSelected()) { //使用状态未选择
					JOptionPane.showMessageDialog(null,"未选择生产厂家使用状态","提示",JOptionPane.INFORMATION_MESSAGE);
				}
				else {  //生产厂家名不为空
					if(CURRENTID !=0 ){  //当前选中了一项
						//修改之后的排序号或生产厂家已经存在
						if(!isNumeric(num))//输入的排序号不为数字
							JOptionPane.showMessageDialog(null,"排序号必须为正整数","提示",JOptionPane.INFORMATION_MESSAGE);
						else if(Integer.valueOf(num)<=0)
							JOptionPane.showMessageDialog(null,"排序号必须为正整数","提示",JOptionPane.INFORMATION_MESSAGE);
						else if(ManufacturersDao.getInstance().QueryNumByID(CURRENTID) != Integer.valueOf(num) && ManufacturersDao.getInstance().numIsExist(Integer.valueOf(num)))
							JOptionPane.showMessageDialog(null,"该排序号已经存在","提示",JOptionPane.INFORMATION_MESSAGE);
						else if(!ManufacturersDao.getInstance().QueryNameByID(CURRENTID).equals(bjsName) && ManufacturersDao.getInstance().nameIsExist(bjsName))
							JOptionPane.showMessageDialog(null,"该生产厂家名已经存在","提示",JOptionPane.INFORMATION_MESSAGE);
						else//修改之后的排序号、生产厂家不存在					
							ManufacturersDao.getInstance().Update(Integer.valueOf(num),bjsName,isUse,CURRENTID);
					}
					else if(CURRENTID ==0 ){  //添加生产厂家
						if(!isNumeric(num))//输入的排序号不为数字
							JOptionPane.showMessageDialog(null,"排序号必须为正整数","提示",JOptionPane.INFORMATION_MESSAGE);
						else if(Integer.valueOf(num)<=0)
							JOptionPane.showMessageDialog(null,"排序号必须为正整数","提示",JOptionPane.INFORMATION_MESSAGE);
						else if(ManufacturersDao.getInstance().QueryNumByID(CURRENTID) != Integer.valueOf(num) && ManufacturersDao.getInstance().numIsExist(Integer.valueOf(num)))
							JOptionPane.showMessageDialog(null,"该排序号已经存在","提示",JOptionPane.INFORMATION_MESSAGE);
						else if(ManufacturersDao.getInstance().nameIsExist(bjsName))
							JOptionPane.showMessageDialog(null,"该生产厂家名已经存在","提示",JOptionPane.INFORMATION_MESSAGE);
						else {
							ManufacturersDao.getInstance().Add(Integer.valueOf(num),bjsName,isUse);
							CURRENTID =ManufacturersDao.getInstance().QueryIDByName(bjsName);//更新当前id指针为新建用户的id
							deleteButton.setEnabled(true);
							resetButton.setEnabled(true);
							titledBorder1.setTitle("修改");
							settingPanel.repaint();
						}
					}
					RefreshListPanel();//刷新列表
					SetSelected();//设置选中
				}
			}
		});
		saveButton.setFont(new Font("宋体", Font.PLAIN, 20));
		saveButton.setBounds(7, 384, 113, 27);
		settingPanel.add(saveButton);
		
		deleteButton = new JButton("删除");
		deleteButton.setEnabled(false);
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selection = JOptionPane.showConfirmDialog(null,"确认删除"+ManufacturersDao.getInstance().QueryNameByID(CURRENTID)+"？","删除",JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
				if( selection == JOptionPane.OK_OPTION ){
					String bjsName = nameTextField.getText();
					ManufacturersDao.getInstance().Delete(bjsName);
					RefreshListPanel();//刷新table
					listTable.clearSelection();
					CURRENTID = 0; //当前用户id清零
					nameTextField.setText("");
					numTextField.setText("");
					nameTextField.requestFocus();
					selectedGroup.clearSelection();
					deleteButton.setEnabled(false);
					titledBorder1.setTitle("新增");
					settingPanel.repaint();
				}
			}
		});
		deleteButton.setFont(new Font("宋体", Font.PLAIN, 20));
		deleteButton.setBounds(134, 384, 113, 27);
		settingPanel.add(deleteButton);
		
		resetButton = new JButton("复位");
		resetButton.setEnabled(false);
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				titledBorder1.setTitle("新增");
				settingPanel.repaint();
				nameTextField.setText("");
				numTextField.setText("");
				nameTextField.requestFocus();
				deleteButton.setEnabled(false);
				resetButton.setEnabled(false);
				selectedGroup.clearSelection();
				CURRENTID = 0;
				RefreshListPanel(); //刷新生产厂家列表
			}
		});
		resetButton.setFont(new Font("宋体", Font.PLAIN, 20));
		resetButton.setBounds(14, 442, 113, 27);
		settingPanel.add(resetButton);
		
		closeButton = new JButton("关闭");
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		closeButton.setFont(new Font("宋体", Font.PLAIN, 20));
		closeButton.setBounds(134, 442, 113, 27);
		settingPanel.add(closeButton);
		
		label = new JLabel("\u5E8F\u53F7");
		label.setFont(new Font("宋体", Font.PLAIN, 20));
		label.setBounds(14, 61, 72, 18);
		settingPanel.add(label);
		
		numTextField = new JTextField();
		numTextField.setFont(new Font("宋体", Font.PLAIN, 20));
		numTextField.setBounds(14, 92, 220, 31);
		settingPanel.add(numTextField);
		numTextField.setColumns(10);
		
		isUse = new JLabel("\u662F\u5426\u5728\u7528");
		isUse.setFont(new Font("宋体", Font.PLAIN, 20));
		isUse.setBounds(14, 277, 106, 18);
		settingPanel.add(isUse);
		
		yesRadioButton = new JRadioButton("\u662F");
		noRadioButton = new JRadioButton("\u5426");
		yesRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(yesRadioButton.isSelected())
					noRadioButton.setSelected(false);
				else
					noRadioButton.setSelected(true);
			}
		});
		yesRadioButton.setFont(new Font("宋体", Font.PLAIN, 20));
		yesRadioButton.setBounds(50, 307, 65, 27);
		settingPanel.add(yesRadioButton);
		
		
		noRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(noRadioButton.isSelected())
					yesRadioButton.setSelected(false);
				else
					yesRadioButton.setSelected(true);
			}
		});
		noRadioButton.setFont(new Font("宋体", Font.PLAIN, 20));
		noRadioButton.setBounds(117, 307, 80, 27);
		settingPanel.add(noRadioButton);
		
		selectedGroup = new ButtonGroup();//单选组
		selectedGroup.add(yesRadioButton);
		selectedGroup.add(noRadioButton);
	}
	
	//生成用户名表格数据
    /**
     * @return
     */
    public Object[][] queryData(){
        List<Manufacturers> list=ManufacturersDao.getInstance().QueryAll();
        data=new Object[list.size()][head.length];
        for(int i=0;i<list.size();i++){
            for(int j=0;j<head.length;j++){
                data[i][0]=list.get(i).getNum();
                data[i][1]=list.get(i).getManufacturersName();
                data[i][2]=list.get(i).getIsUse();
            }
        }
        return data;
    }
    
	public void RefreshListPanel() {
		listPanel.removeAll();
		listPanel.add(scrollPane);
    	tableModel=new DefaultTableModel(queryData(),head){
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };
    	listTable.setModel(tableModel);
    	scrollPane.setViewportView(listTable);
    	titledBorder.setTitle("共"+ManufacturersDao.getInstance().QueryCount()+"项");
		listPanel.repaint();
		listPanel.validate();
		listPanel.repaint();
	}
	
	//设置表格行选中
	public void SetSelected(){
		String queryName = ManufacturersDao.getInstance().QueryNameByID(CURRENTID);
		for(int i =0;i<listTable.getRowCount();i++)
			if(queryName.equals(listTable.getValueAt(i,1).toString())){
				listTable.setRowSelectionInterval(i,i);//设置新添加用户行为选中样式；
				break;
			}
	}
	
	/**
              * 利用正则表达式判断字符串是否是数字
     * @param str
     * @return
     */
    public boolean isNumeric(String str){
           Pattern pattern = Pattern.compile("[0-9]*");
           Matcher isNum = pattern.matcher(str);
           if( !isNum.matches() ){
               return false;
           }
           return true;
    }
}
