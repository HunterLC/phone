package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import bll.OsTypeDao;
import bll.PhoneDao;
import bll.BandTypeDao;
import bll.EditorsDao;
import bll.InventoryDao;
import bll.ManufacturersDao;
import bll.ResolutionDao;
import model.osType;
import model.bandType;
import model.Manufacturers;
import model.Phone;
import model.Editors;
import model.Resolution;

import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerDateModel;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.ActionMap;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class PhoneFrame extends JFrame {

	private JPanel contentPane,PhonesInfoPanel,SettingPanel,SortPanel,searchPanel;
	private JScrollPane PhoneInfoScrollPane;
	private TitledBorder titledBorder1,titledBorder2,titledBorder3,titledBorder4;
	private JLabel phoneNameLabel;
	private JLabel phoneNumberLabel;
	private JLabel resolutionLabel;
	private JLabel osTypeLabel,inventoryLabel,manufacturersLabel,authorLabel,categoryLabel,priceLabel,timeLabel,editorLabel;
	private JTextField phoneNameTextField;
	private JTextField phoneNumberTextField;
	private JTextField authorTextField;
	private JComboBox resolutionComboBox,osTypeComboBox,manufacturersComboBox,editorComboBox,categoryComboBox;
	private JSpinner inventorySpinner,timeSpinner;
	private JButton closeButton,deleteButton,resetButton,saveButton;
	private JTable phonesTable;
	private static int CURRENTID = 0;
	private DefaultTableModel tableModel;
	private String head[]=null;  //表格列名
	private Object [][]data=null;   //表格行表
	private JTextField searchTextField;
	private JRadioButton bookNumRadioButton,phoneNameRadioButton,bjsRadioButton,publishRadioButton,bookTypeRadioButton;
	private ButtonGroup selectedGroup;
	private JTextField priceTextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
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
					PhoneFrame frame = new PhoneFrame();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PhoneFrame() {
		setTitle("\u624B\u673A\u4FE1\u606F");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 932, 682);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setVisible(true);
		setLocationRelativeTo(null);
		

		searchPanel = new JPanel();
		titledBorder3 = new TitledBorder(null, "\u68C0\u7D22", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 102, 153));
		titledBorder3.setTitleFont(new Font("宋体", Font.PLAIN, 20));
		searchPanel.setBorder(titledBorder3);
		searchPanel.setBounds(14, 0, 197, 57);
		contentPane.add(searchPanel);
		searchPanel.setLayout(null);
		
		searchTextField = new JTextField();
		searchTextField.setFont(new Font("宋体", Font.PLAIN, 16));
		searchTextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				RefreshListPanel();
				System.out.println("hhhhhhhhhhhahahha");
				phonesTable.clearSelection();
				CURRENTID = 0; //当前用户id清零
				phoneNameTextField.setText("");
				authorTextField.setText("");
				phoneNumberTextField.setText("");
				priceTextField.setText("");
				resolutionComboBox.setSelectedIndex(0);
				osTypeComboBox.setSelectedIndex(0);
				manufacturersComboBox.setSelectedIndex(0);
				editorComboBox.setSelectedIndex(0);
				categoryComboBox.setSelectedIndex(0);
				inventorySpinner.setValue(0);
				try {
					timeSpinner.setValue(new SimpleDateFormat("yyyy-MM-dd").parse(new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				deleteButton.setEnabled(false);
				titledBorder2.setTitle("新增");
				SettingPanel.repaint();
			}
		});
		searchTextField.setBounds(48, 19, 135, 25);
		searchPanel.add(searchTextField);
		searchTextField.setColumns(10);
		
		SortPanel = new JPanel();
		titledBorder4 = new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u6392\u5E8F\u65B9\u5F0F", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 102, 153));
		titledBorder4.setTitleFont(new Font("宋体", Font.PLAIN, 20));
		SortPanel.setBorder(titledBorder4);
		SortPanel.setBounds(226, 0, 674, 57);
		contentPane.add(SortPanel);
		SortPanel.setLayout(null);
		
		bookNumRadioButton = new JRadioButton("\u578B\u53F7");
		bookNumRadioButton.setFont(new Font("宋体", Font.PLAIN, 16));
		bookNumRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				RefreshListPanel();
			}
		});
		bookNumRadioButton.setSelected(true);
		bookNumRadioButton.setBounds(21, 22, 77, 26);
		SortPanel.add(bookNumRadioButton);
		
		phoneNameRadioButton = new JRadioButton("\u624B\u673A\u540D\u79F0");
		phoneNameRadioButton.setFont(new Font("宋体", Font.PLAIN, 16));
		phoneNameRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RefreshListPanel();
			}
		});
		phoneNameRadioButton.setBounds(126, 22, 116, 26);
		SortPanel.add(phoneNameRadioButton);
		
		bjsRadioButton = new JRadioButton("\u751F\u4EA7\u5382\u5BB6");
		bjsRadioButton.setFont(new Font("宋体", Font.PLAIN, 16));
		bjsRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RefreshListPanel();
			}
		});
		bjsRadioButton.setBounds(273, 22, 101, 26);
		SortPanel.add(bjsRadioButton);
		
		publishRadioButton = new JRadioButton("\u751F\u4EA7\u65F6\u95F4");
		publishRadioButton.setFont(new Font("宋体", Font.PLAIN, 16));
		publishRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RefreshListPanel();
			}
		});
		publishRadioButton.setBounds(395, 22, 116, 26);
		SortPanel.add(publishRadioButton);
		
		bookTypeRadioButton = new JRadioButton("\u624B\u673A\u54C1\u724C");
		bookTypeRadioButton.setFont(new Font("宋体", Font.PLAIN, 16));
		bookTypeRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RefreshListPanel();
			}
		});
		bookTypeRadioButton.setBounds(535, 22, 116, 26);
		SortPanel.add(bookTypeRadioButton);
		
		selectedGroup = new ButtonGroup();//单选组
		selectedGroup.add(bookNumRadioButton);
		selectedGroup.add(phoneNameRadioButton);
		selectedGroup.add(bjsRadioButton);
		selectedGroup.add(publishRadioButton);
		selectedGroup.add(bookTypeRadioButton);
		
		PhonesInfoPanel = new JPanel();
		titledBorder1 = new TitledBorder(null, "共XXX项", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 102, 153));
		titledBorder1.setTitleFont(new Font("宋体", Font.PLAIN, 20));
		PhonesInfoPanel.setBorder(titledBorder1);
		PhonesInfoPanel.setBounds(14, 57, 886, 337);
		contentPane.add(PhonesInfoPanel);
		PhonesInfoPanel.setLayout(null);
		
		PhoneInfoScrollPane = new JScrollPane();
		PhoneInfoScrollPane.setBounds(14, 26, 858, 298);
		PhonesInfoPanel.add(PhoneInfoScrollPane);
		
		//初始化表格
		phonesTable = new JTable();
		phonesTable.setRowHeight(30);
		phonesTable.setBorder(new LineBorder(new Color(0, 0, 0)));
		head=new String[] {"名称","备注","型号","手机品牌","屏幕分辨率","库存","定价","生产时间","操作系统","生产厂家","编辑员"};
		tableModel=new DefaultTableModel(queryData(),head){
		public boolean isCellEditable(int row, int column)
		{
			return false;
			}
		};
		phonesTable.setFont(new Font("宋体", Font.BOLD, 16));
		phonesTable.addMouseListener(new MouseAdapter() {   //用户名表格点击事件
			@SuppressWarnings("deprecation")
			@Override
			public void mouseClicked(MouseEvent arg0) {
				titledBorder2.setTitle("修改");
				SettingPanel.repaint();
				//填充手机编辑框
				String phoneName = phonesTable.getValueAt(phonesTable.getSelectedRow(), 0).toString();
		    	String note = phonesTable.getValueAt(phonesTable.getSelectedRow(), 1).toString();
				String phoneNumber = phonesTable.getValueAt(phonesTable.getSelectedRow(), 2).toString();
				String bandTypeID = phonesTable.getValueAt(phonesTable.getSelectedRow(), 3).toString();
				String ResolutionID = phonesTable.getValueAt(phonesTable.getSelectedRow(), 4).toString();
				String inventory = phonesTable.getValueAt(phonesTable.getSelectedRow(), 5).toString();
				String price = phonesTable.getValueAt(phonesTable.getSelectedRow(), 6).toString();
				String produceTime = phonesTable.getValueAt(phonesTable.getSelectedRow(), 7).toString();
				String osType = phonesTable.getValueAt(phonesTable.getSelectedRow(), 8).toString();
				String manufacturersID = phonesTable.getValueAt(phonesTable.getSelectedRow(), 9).toString();
				String editorName = phonesTable.getValueAt(phonesTable.getSelectedRow(), 10).toString();
				phoneNameTextField.setText(phoneName);
				authorTextField.setText(note);
				phoneNumberTextField.setText(phoneNumber);
				priceTextField.setText(price);
				resolutionComboBox.setSelectedItem(ResolutionID);
				osTypeComboBox.setSelectedItem(osType);
				manufacturersComboBox.setSelectedItem(manufacturersID);
				editorComboBox.setSelectedItem(editorName);
				categoryComboBox.setSelectedItem(bandTypeID);
				inventorySpinner.setValue(Integer.valueOf(inventory));
				try {
					timeSpinner.setValue(new SimpleDateFormat("yyyy-MM-dd").parse(produceTime));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				CURRENTID = PhoneDao.getInstance().QueryIDByphoneName(phoneName);//获得当前选中的id
				deleteButton.setEnabled(true);
				saveButton.setEnabled(true);
			}
		});
		ActionMap am = (ActionMap) UIManager.get("Table.actionMap");  
		am.put("selectNextRowCell", new AbstractAction() {  
		    @Override  
		    public void actionPerformed(ActionEvent e) {  
		    	titledBorder2.setTitle("修改");
				SettingPanel.repaint();
		        // 自己的处理代码  
		    	String phoneName = phonesTable.getValueAt(phonesTable.getSelectedRow(), 0).toString();
		    	String note = phonesTable.getValueAt(phonesTable.getSelectedRow(), 1).toString();
				String phoneNumber = phonesTable.getValueAt(phonesTable.getSelectedRow(), 2).toString();
				String bandTypeID = phonesTable.getValueAt(phonesTable.getSelectedRow(), 3).toString();
				String ResolutionID = phonesTable.getValueAt(phonesTable.getSelectedRow(), 4).toString();
				String inventory = phonesTable.getValueAt(phonesTable.getSelectedRow(), 5).toString();
				String price = phonesTable.getValueAt(phonesTable.getSelectedRow(), 6).toString();
				String produceTime = phonesTable.getValueAt(phonesTable.getSelectedRow(), 7).toString();
				String osType = phonesTable.getValueAt(phonesTable.getSelectedRow(), 8).toString();
				String manufacturersID = phonesTable.getValueAt(phonesTable.getSelectedRow(), 9).toString();
				String editorName = phonesTable.getValueAt(phonesTable.getSelectedRow(), 10).toString();
				phoneNameTextField.setText(phoneName);
				authorTextField.setText(note);
				phoneNumberTextField.setText(phoneNumber);
				priceTextField.setText(price);
				resolutionComboBox.setSelectedItem(ResolutionID);
				osTypeComboBox.setSelectedItem(osType);
				manufacturersComboBox.setSelectedItem(manufacturersID);
				editorComboBox.setSelectedItem(editorName);
				categoryComboBox.setSelectedItem(bandTypeID);
				inventorySpinner.setValue(Integer.valueOf(inventory));
				try {
					timeSpinner.setValue(new SimpleDateFormat("yyyy-MM-dd").parse(produceTime));
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				CURRENTID = PhoneDao.getInstance().QueryIDByphoneName(phoneName);//获得当前选中的id
				deleteButton.setEnabled(true);
				saveButton.setEnabled(true);
		    }  
		});  
		phonesTable.setActionMap(am); 
		DefaultTableCellRenderer r = new DefaultTableCellRenderer();
		r.setHorizontalAlignment(JLabel.CENTER);
		phonesTable.setDefaultRenderer(Object.class, r);
		phonesTable.setModel(tableModel);
		phonesTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		PhoneInfoScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);  //设置水平滚动条需要时可见
		FitTableColumns(phonesTable);
		PhoneInfoScrollPane.setViewportView(phonesTable);
		
		SettingPanel = new JPanel();
		titledBorder2 = new TitledBorder(UIManager.getBorder("TitledBorder.border"), "新增", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 102, 153));
		titledBorder2.setTitleFont(new Font("宋体", Font.PLAIN, 20));
		SettingPanel.setBorder(titledBorder2);
		SettingPanel.setBounds(14, 407, 886, 215);
		contentPane.add(SettingPanel);
		SettingPanel.setLayout(null);
		
		phoneNameLabel = new JLabel("\u624B\u673A\u540D\u79F0");
		phoneNameLabel.setFont(new Font("宋体", Font.PLAIN, 16));
		phoneNameLabel.setBounds(44, 36, 72, 18);
		SettingPanel.add(phoneNameLabel);
		
		phoneNumberLabel = new JLabel("\u624B\u673A\u578B\u53F7");
		phoneNumberLabel.setFont(new Font("宋体", Font.PLAIN, 16));
		phoneNumberLabel.setBounds(44, 72, 72, 18);
		SettingPanel.add(phoneNumberLabel);
		
		resolutionLabel = new JLabel("\u5206\u8FA8\u7387");
		resolutionLabel.setFont(new Font("宋体", Font.PLAIN, 16));
		resolutionLabel.setBounds(44, 107, 72, 18);
		SettingPanel.add(resolutionLabel);
		
		osTypeLabel = new JLabel("\u64CD\u4F5C\u7CFB\u7EDF");
		osTypeLabel.setFont(new Font("宋体", Font.PLAIN, 16));
		osTypeLabel.setBounds(44, 138, 72, 18);
		SettingPanel.add(osTypeLabel);
		
		phoneNameTextField = new JTextField();
		phoneNameTextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				authorTextField.requestFocus();
			}
		});
		phoneNameTextField.setFont(new Font("宋体", Font.PLAIN, 16));
		phoneNameTextField.setBounds(151, 27, 325, 28);
		SettingPanel.add(phoneNameTextField);
		phoneNameTextField.setColumns(10);
		phoneNameTextField.requestFocus();
		
		phoneNumberTextField = new JTextField();
		phoneNumberTextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				categoryComboBox.requestFocus();
			}
		});
		phoneNumberTextField.setFont(new Font("宋体", Font.PLAIN, 16));
		phoneNumberTextField.setBounds(151, 64, 325, 24);
		SettingPanel.add(phoneNumberTextField);
		phoneNumberTextField.setColumns(10);
		
		resolutionComboBox = new JComboBox();
		resolutionComboBox.setModel(new DefaultComboBoxModel(QueryOpenName()));
		resolutionComboBox.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER){ 					
					inventorySpinner.requestFocus();
				}
			}
		});

		resolutionComboBox.setFont(new Font("宋体", Font.PLAIN, 16));
		resolutionComboBox.setBounds(151, 95, 101, 33);
		SettingPanel.add(resolutionComboBox);
		
		osTypeComboBox = new JComboBox();
		osTypeComboBox.setModel(new DefaultComboBoxModel(QueryLanguageName()));
		osTypeComboBox.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
					manufacturersComboBox.requestFocus();
			}
		});
		

		osTypeComboBox.setFont(new Font("宋体", Font.PLAIN, 16));
		osTypeComboBox.setBounds(151, 135, 101, 28);
		SettingPanel.add(osTypeComboBox);
		
		inventoryLabel = new JLabel("\u5E93  \u5B58");
		inventoryLabel.setFont(new Font("宋体", Font.PLAIN, 16));
		inventoryLabel.setBounds(266, 105, 72, 18);
		SettingPanel.add(inventoryLabel);
		
		manufacturersLabel = new JLabel("\u751F\u4EA7\u5382\u5BB6");
		manufacturersLabel.setFont(new Font("宋体", Font.PLAIN, 16));
		manufacturersLabel.setBounds(266, 136, 72, 18);
		SettingPanel.add(manufacturersLabel);
		
		manufacturersComboBox = new JComboBox();
		manufacturersComboBox.setModel(new DefaultComboBoxModel(QueryBjsName()));
		manufacturersComboBox.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
					saveButton.requestFocus();
			}
		});

		manufacturersComboBox.setFont(new Font("宋体", Font.PLAIN, 16));
		manufacturersComboBox.setBounds(339, 133, 137, 30);
		SettingPanel.add(manufacturersComboBox);
		
		inventorySpinner = new JSpinner();
		inventorySpinner.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
					priceTextField.requestFocus();
			}
		});
		inventorySpinner.setFont(new Font("宋体", Font.PLAIN, 16));
		inventorySpinner.setBounds(339, 95, 137, 30);
		SettingPanel.add(inventorySpinner);
		
		authorTextField = new JTextField();
		authorTextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				phoneNumberTextField.requestFocus();
			}
		});
		authorTextField.setFont(new Font("宋体", Font.PLAIN, 16));
		authorTextField.setColumns(10);
		authorTextField.setBounds(598, 30, 274, 28);
		SettingPanel.add(authorTextField);
		
		authorLabel = new JLabel("\u5907    \u6CE8");
		authorLabel.setFont(new Font("宋体", Font.PLAIN, 16));
		authorLabel.setBounds(521, 36, 96, 18);
		SettingPanel.add(authorLabel);
		
		categoryLabel  = new JLabel("\u624B\u673A\u54C1\u724C");
		categoryLabel.setFont(new Font("宋体", Font.PLAIN, 16));
		categoryLabel.setBounds(521, 73, 92, 18);
		SettingPanel.add(categoryLabel);
		
		priceLabel = new JLabel("\u5B9A    \u4EF7");
		priceLabel.setFont(new Font("宋体", Font.PLAIN, 16));
		priceLabel.setBounds(521, 105, 96, 18);
		SettingPanel.add(priceLabel);
		
		timeLabel = new JLabel("\u751F\u4EA7\u65E5\u671F");
		timeLabel.setFont(new Font("宋体", Font.PLAIN, 16));
		timeLabel.setBounds(684, 105, 92, 18);
		SettingPanel.add(timeLabel);
		
		// 获得时间日期模型
		SpinnerDateModel model = new SpinnerDateModel();
		// 获得JSPinner对象
		timeSpinner = new JSpinner(model);
		timeSpinner.setValue(new Date());
		// 设置时间格式
		JSpinner.DateEditor editor = new JSpinner.DateEditor(timeSpinner, "yyyy-MM-dd");
		timeSpinner.setEditor(editor);
		timeSpinner.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
					osTypeComboBox.requestFocus();
			}
		});
		timeSpinner.setFont(new Font("宋体", Font.PLAIN, 16));
		timeSpinner.setBounds(766, 100, 106, 30);
		SettingPanel.add(timeSpinner);
		
		editorLabel = new JLabel("\u7F16\u8F91\u5458");
		editorLabel.setFont(new Font("宋体", Font.PLAIN, 16));
		editorLabel.setBounds(521, 136, 96, 18);
		SettingPanel.add(editorLabel);
		
		editorComboBox = new JComboBox();
		editorComboBox.setModel(new DefaultComboBoxModel(QueryEditorName()));
		editorComboBox.setFont(new Font("宋体", Font.PLAIN, 16));
		editorComboBox.setBounds(598, 133, 274, 30);
		SettingPanel.add(editorComboBox);
		
		closeButton = new JButton("\u5173\u95ED");
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!phoneNameTextField.getText().equals("")||!phoneNumberTextField.getText().equals("")||!authorTextField.getText().equals("")||!priceTextField.getText().equals("")) {
					int selection = JOptionPane.showConfirmDialog(null,"数据区还有未保存数据，是否退出并丢弃？","注意",JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
					if( selection == JOptionPane.OK_OPTION ){
						dispose();
					}
				}
				else
					dispose();
			}
		});
		closeButton.setFont(new Font("宋体", Font.PLAIN, 20));
		closeButton.setBounds(794, 176, 78, 32);
		SettingPanel.add(closeButton);
		
		saveButton = new JButton("\u4FDD\u5B58");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		saveButton.setFont(new Font("宋体", Font.PLAIN, 20));
		saveButton.setBounds(678, 176, 78, 32);
		SettingPanel.add(saveButton);
		
		deleteButton = new JButton("\u5220\u9664");
		deleteButton.setEnabled(false);
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selection = JOptionPane.showConfirmDialog(null,"确认删除"+PhoneDao.getInstance().QueryphoneNameByID(CURRENTID)+"？","删除",JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
				if( selection == JOptionPane.OK_OPTION ){
					String phoneName = phoneNameTextField.getText();
					PhoneDao.getInstance().Delete(phoneName);
					RefreshListPanel();//刷新table
					phonesTable.clearSelection();
					CURRENTID = 0; //当前用户id清零
					searchTextField.setText("");
					bookNumRadioButton.setSelected(true);
					phoneNameTextField.setText("");
					authorTextField.setText("");
					phoneNumberTextField.setText("");
					priceTextField.setText("");
					resolutionComboBox.setSelectedIndex(0);
					osTypeComboBox.setSelectedIndex(0);
					manufacturersComboBox.setSelectedIndex(0);
					editorComboBox.setSelectedIndex(0);
					categoryComboBox.setSelectedIndex(0);
					inventorySpinner.setValue(0);
					try {
						timeSpinner.setValue(new SimpleDateFormat("yyyy-MM-dd").parse(new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					deleteButton.setEnabled(false);
					titledBorder2.setTitle("新增");
					SettingPanel.repaint();
				}
			}
		});
		deleteButton.setFont(new Font("宋体", Font.PLAIN, 20));
		deleteButton.setBounds(576, 176, 72, 32);
		SettingPanel.add(deleteButton);
		
		resetButton = new JButton("\u6E05\u9664");
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				titledBorder2.setTitle("新增");
				SettingPanel.repaint();
				searchTextField.setText("");
				bookNumRadioButton.setSelected(true);
				phoneNameTextField.setText("");
				authorTextField.setText("");
				phoneNumberTextField.setText("");
				priceTextField.setText("");
				resolutionComboBox.setSelectedIndex(0);
				osTypeComboBox.setSelectedIndex(0);
				manufacturersComboBox.setSelectedIndex(0);
				editorComboBox.setSelectedIndex(0);
				categoryComboBox.setSelectedIndex(0);
				inventorySpinner.setValue(0);
				try {
					timeSpinner.setValue(new SimpleDateFormat("yyyy-MM-dd").parse(new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				deleteButton.setEnabled(false);
				CURRENTID = 0;
				RefreshListPanel(); //刷新手机列表
			}
		});
		resetButton.setFont(new Font("宋体", Font.PLAIN, 20));
		resetButton.setBounds(463, 176, 78, 32);
		SettingPanel.add(resetButton);
		
		priceTextField = new JTextField();
		priceTextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				timeSpinner.requestFocus();
			}
		});
		priceTextField.setFont(new Font("宋体", Font.PLAIN, 16));
		priceTextField.setBounds(598, 101, 78, 25);
		SettingPanel.add(priceTextField);
		priceTextField.setColumns(10);
		
		categoryComboBox = new JComboBox();
		categoryComboBox.setModel(new DefaultComboBoxModel(QueryBookTypeName()));
		categoryComboBox.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
					resolutionComboBox.requestFocus();
			}
		});
		categoryComboBox.setFont(new Font("宋体", Font.PLAIN, 16));
		categoryComboBox.setBounds(598, 69, 274, 24);
		SettingPanel.add(categoryComboBox);
	}
	
	/**
	 * 设置table的列宽随着列的内容而变化
	 * @param myTable
	 */
	public void FitTableColumns(JTable myTable) {               
        JTableHeader header = myTable.getTableHeader();
        int rowCount = myTable.getRowCount();
        Enumeration columns = myTable.getColumnModel().getColumns();
        while (columns.hasMoreElements()) {
            TableColumn column = (TableColumn) columns.nextElement();
            int col = header.getColumnModel().getColumnIndex(column.getIdentifier());
            int width = (int) myTable.getTableHeader().getDefaultRenderer().getTableCellRendererComponent(myTable,column.getIdentifier(), false, false, -1, col).getPreferredSize().getWidth();
            for (int row = 0; row < rowCount; row++){
                int preferedWidth = (int) myTable.getCellRenderer(row, col).getTableCellRendererComponent(myTable,myTable.getValueAt(row, col), false, false,row, col).getPreferredSize().getWidth();
                width = Math.max(width, preferedWidth)+4;
            }
            header.setResizingColumn(column);
            column.setWidth(width + myTable.getIntercellSpacing().width);
        }
    }
	//生成用户名表格数据
    /**
     * @return
     */
    public Object[][] queryData(){
    	//获取排序方式
    	String sortType = null; 
    	Enumeration<AbstractButton> radioBtns= selectedGroup.getElements();  
    	while (radioBtns.hasMoreElements()) {  
    		AbstractButton btn = radioBtns.nextElement();  
    	    if(btn.isSelected()){  
    	    	sortType=btn.getText();  
    	        break;  
    	    }  
    	}
    	String searchText = searchTextField.getText();
        List<Phone> list=PhoneDao.getInstance().QueryAll(searchText,QuerySortType(sortType));
        titledBorder1.setTitle("共"+list.size()+"项");
        data=new Object[list.size()][head.length];
        for(int i=0;i<list.size();i++){
                data[i][0]=list.get(i).getPhoneName();
                data[i][1]=list.get(i).getNote();
                data[i][2]=list.get(i).getPhoneNumber();
                data[i][3]=list.get(i).getBandType();
                data[i][4]=list.get(i).getResolution();
                data[i][5]=list.get(i).getInventory();
                data[i][6]=list.get(i).getPrice();
                data[i][7]=list.get(i).getProduceTime();
                data[i][8]=list.get(i).getOsType();
                data[i][9]=list.get(i).getManufacturers();
                data[i][10]=list.get(i).getEditor();
        }
        return data;
    }
    
    public String QuerySortType(String sortType) {
    	String type = null;
    	if(sortType.equals("型号"))
    		type="phoneNumber";
    	else if(sortType.equals("手机名称"))
    		type="phoneName";
    	else if(sortType.equals("生产厂家"))
    		type="manufacturersName";
    	else if(sortType.equals("生产时间"))
    		type="produceTime";
    	else if(sortType.equals("手机品牌"))
    		type="bandType";
    	return type;
    }
	public void RefreshListPanel() {
		PhonesInfoPanel.removeAll();
		PhonesInfoPanel.add(PhoneInfoScrollPane);
    	tableModel=new DefaultTableModel(queryData(),head){
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };
        phonesTable.setModel(tableModel);
        FitTableColumns(phonesTable);
        PhoneInfoScrollPane.setViewportView(phonesTable);
		PhonesInfoPanel.validate();
		PhonesInfoPanel.repaint();
	}
	
	//设置表格行选中
	public void SetSelected(){
		if(CURRENTID == 0)
			return ;
		String queryName = PhoneDao.getInstance().QueryphoneNameByID(CURRENTID);
		for(int i =0;i<phonesTable.getRowCount();i++)
			if(queryName.equals(phonesTable.getValueAt(i,0).toString())){
				phonesTable.setRowSelectionInterval(i,i);//设置新添加用户行为选中样式；
				Rectangle rect = phonesTable.getCellRect(i, 0, true);  	  
				phonesTable.scrollRectToVisible(rect);
				break;
			}
	}
	
	public void save() {
		String phoneName = phoneNameTextField.getText();
    	String note = authorTextField.getText();
		String phoneNumber = phoneNumberTextField.getText();
		String bandTypeID = categoryComboBox.getSelectedItem().toString();
		String ResolutionID = resolutionComboBox.getSelectedItem().toString();
		String inventory = inventorySpinner.getValue().toString();
		String price = priceTextField.getText();
		String produceTime = new SimpleDateFormat("yyyy-MM-dd").format(timeSpinner.getValue());
		System.out.println(produceTime);
		String osType = osTypeComboBox.getSelectedItem().toString();
		String manufacturersID = manufacturersComboBox.getSelectedItem().toString();
		String editorName = editorComboBox.getSelectedItem().toString();
		if(phoneName.equals("")) {//手机名称为空
			JOptionPane.showMessageDialog(null,"手机名称为空","提示",JOptionPane.INFORMATION_MESSAGE);
			phoneNameTextField.requestFocus();
		}else if(phoneNumber.equals("")) {//型号为空
			JOptionPane.showMessageDialog(null,"型号为空","提示",JOptionPane.INFORMATION_MESSAGE);
			phoneNumberTextField.requestFocus();
		}else if(price.equals("")) {//定价为空
			JOptionPane.showMessageDialog(null,"定价为空","提示",JOptionPane.INFORMATION_MESSAGE);
			priceTextField.requestFocus();
		}
		else {  //不为空
			if(CURRENTID !=0 ){  //当前选中了一项
				if(!PhoneDao.getInstance().QueryphoneNumberByID(CURRENTID).equals(phoneNumber) && PhoneDao.getInstance().phoneNumberIsExist(phoneNumber)) {
					JOptionPane.showMessageDialog(null,"型号已经存在","提示",JOptionPane.INFORMATION_MESSAGE);
					phoneNumberTextField.requestFocus();
					return ;
				}
					
				else {
					InventoryDao.getInstance().Update(Integer.valueOf(inventory), phoneNumber);//更新库存
					PhoneDao.getInstance().Update(
							phoneName,
							note,
							phoneNumber,
							BandTypeDao.getInstance().QueryIDByName(bandTypeID),
							ResolutionDao.getInstance().QueryIDByName(ResolutionID),
							InventoryDao.getInstance().QueryIDByPhoneNumber(phoneNumber),
							Float.valueOf(price),
							produceTime,
							OsTypeDao.getInstance().QueryIDByName(osType),
							ManufacturersDao.getInstance().QueryIDByName(manufacturersID),
							EditorsDao.getInstance().QueryIDByName(editorName),
							CURRENTID);
				}				
					
			}
			else if(CURRENTID ==0 ){  //添加手机
				if(PhoneDao.getInstance().phoneNumberIsExist(phoneNumber)) {
					JOptionPane.showMessageDialog(null,"型号已经存在","提示",JOptionPane.INFORMATION_MESSAGE);
					phoneNumberTextField.requestFocus();
					return ;
				}
				else {
					InventoryDao.getInstance().Add(phoneNumber, Integer.valueOf(inventory));
					PhoneDao.getInstance().Add(
							phoneName,
							note,
							phoneNumber,
							BandTypeDao.getInstance().QueryIDByName(bandTypeID),
							ResolutionDao.getInstance().QueryIDByName(ResolutionID),
							InventoryDao.getInstance().QueryIDByPhoneNumber(phoneNumber),
							Float.valueOf(price),
							produceTime,
							OsTypeDao.getInstance().QueryIDByName(osType),
							ManufacturersDao.getInstance().QueryIDByName(manufacturersID),
							EditorsDao.getInstance().QueryIDByName(editorName));
					CURRENTID =PhoneDao.getInstance().QueryIDByphoneName(phoneName);//更新当前id指针为新建用户的id
					deleteButton.setEnabled(true);
					resetButton.setEnabled(true);
					titledBorder2.setTitle("修改");
					SettingPanel.repaint();
				}
			}
			RefreshListPanel();//刷新列表
			SetSelected();//设置选中
			phoneNameTextField.requestFocus();
		}
	}
	
	public Object[] QueryBjsName() {
		List<String> result = new ArrayList<String>();
		List<Manufacturers> list=ManufacturersDao.getInstance().QueryAllUsed();
		for(Manufacturers item:list) {
			String name = item.getManufacturersName();
			if(result == null)
				result.add(name);
			else if(!result.contains(name))
				result.add(name);
		}
		return result.toArray();
	}
	
	public Object[] QueryBookTypeName() {
		List<String> result = new ArrayList<String>();
		List<bandType> list=BandTypeDao.getInstance().QueryAll();
		for(bandType item:list) {
			String name = item.getBandType();
			if(result == null)
				result.add(name);
			else if(!result.contains(name))
				result.add(name);
		}
		return result.toArray();
	}
	public Object[] QueryOpenName() {
		List<String> result = new ArrayList<String>();
		List<Resolution> list=ResolutionDao.getInstance().QueryAll();
		for(Resolution item:list) {
			String name = item.getResolutionName();
			if(result == null)
				result.add(name);
			else if(!result.contains(name))
				result.add(name);
		}
		return result.toArray();
	}
	public Object[] QueryLanguageName() {
		List<String> result = new ArrayList<String>();
		List<osType> list=OsTypeDao.getInstance().QueryAll();
		for(osType item:list) {
			String name = item.getOsType();
			if(result == null)
				result.add(name);
			else if(!result.contains(name))
				result.add(name);
		}
		return result.toArray();
	}
	public Object[] QueryEditorName() {
		List<String> result = new ArrayList<String>();
		List<Editors> list=EditorsDao.getInstance().QueryAll();
		for(Editors item:list) {
			String name = item.getEditorName();
			if(result == null)
				result.add(name);
			else if(!result.contains(name))
				result.add(name);
		}
		return result.toArray();
	}

}
