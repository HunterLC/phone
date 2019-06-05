package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import com.itextpdf.text.DocumentException;

import bll.OperatorDao;
import bll.SearchPhoneDao;
import jxl.write.WriteException;
import model.Operator;
import model.Phone;
import util.InventoryEXCEL;
import util.InventoryPDF;
import util.InventoryPicture;
import util.MyUtil;
import util.RKPie;
import util.TestPic;

import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Manage_Inventory extends JFrame {

	private JPanel contentPane,BooksInfoPanel,OperatePanel,exportPanel;
	private JScrollPane BooksInfoScrollPane,operateScrollPane;
	private TitledBorder titledBorder1,titledBorder2,titledBorder3,titledBorder4;
	private JLabel logicalSignalLabel;
	private JLabel segmentLabel;
	private JLabel signalLabel,valueDataLabel;
	private JTextField valueDataTextField;
	private JComboBox logicalSignalComboBox,segmentComboBox,signalComboBox;
	private JButton closeButton,deleteButton,addButton,searchButton;
	private JTable booksTable;
	private static int CURRENTID = 0;
	private static int OPERATOR_CURRENTID = 0;
	private DefaultTableModel tableModel,tableModel1;
	private String head[]=null;  //表格列名
	private String head1[]=null;  //表格列名
	private Object [][]data=null;   //表格行表
	private Object [][]data1=null;   //表格行表
	private ButtonGroup selectedGroup;
	private JTable signalTable;
	public static List<Operator> operatorList = new ArrayList<Operator>();//保存条件
	public static List<Phone> phoneList = new ArrayList<Phone>();//保存
	private int USERID;
	private JFrame FRAME;

	
	/**
	 * Create the frame.
	 */
	public Manage_Inventory(int USER_ID) {
		FRAME = this;
		USERID = USER_ID;
		setTitle("\u5E93\u5B58\u4FE1\u606F\u4E00\u89C8");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1081, 717);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setVisible(true);
		setLocationRelativeTo(null);
		titledBorder3 = new TitledBorder(null, "\u68C0\u7D22", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 102, 153));
		titledBorder3.setTitleFont(new Font("宋体", Font.PLAIN, 20));
		titledBorder4 = new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u6392\u5E8F\u65B9\u5F0F", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 102, 153));
		titledBorder4.setTitleFont(new Font("宋体", Font.PLAIN, 20));
		
		selectedGroup = new ButtonGroup();
		
		BooksInfoPanel = new JPanel();
		titledBorder1 = new TitledBorder(null, "共XXX项", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 102, 153));
		titledBorder1.setTitleFont(new Font("宋体", Font.PLAIN, 20));
		BooksInfoPanel.setBorder(titledBorder1);
		BooksInfoPanel.setBounds(14, 13, 1035, 422);
		contentPane.add(BooksInfoPanel);
		BooksInfoPanel.setLayout(null);
		
		BooksInfoScrollPane = new JScrollPane();
		BooksInfoScrollPane.setBounds(14, 26, 1007, 383);
		BooksInfoPanel.add(BooksInfoScrollPane);
		
		//初始化表格
		booksTable = new JTable();
		booksTable.setRowHeight(40);
		booksTable.setBorder(new LineBorder(new Color(0, 0, 0)));
		head=new String[] {"名称","型号","手机品牌","生产厂家","生产时间","库存数量","定价","总码样"};
		tableModel=new DefaultTableModel(queryData(),head){
		public boolean isCellEditable(int row, int column)
		{
			return false;
			}
		};
		booksTable.setFont(new Font("宋体", Font.BOLD, 16));
		booksTable.addMouseListener(new MouseAdapter() {   //用户名表格点击事件
			@SuppressWarnings("deprecation")
			@Override
			public void mouseClicked(MouseEvent arg0) {
				

			}
		});
		ActionMap am = (ActionMap) UIManager.get("Table.actionMap");  
		am.put("selectNextRowCell", new AbstractAction() {  
		    @Override  
		    public void actionPerformed(ActionEvent e) {  

		    }  
		});  
		booksTable.setActionMap(am); 
		DefaultTableCellRenderer r = new DefaultTableCellRenderer();
		r.setHorizontalAlignment(JLabel.CENTER);
		booksTable.setDefaultRenderer(Object.class, r);
		booksTable.setModel(tableModel);
		//booksTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		//BooksInfoScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);  //设置水平滚动条需要时可见
		//FitTableColumns(booksTable);
		BooksInfoScrollPane.setViewportView(booksTable);
		
		OperatePanel = new JPanel();
		titledBorder2 = new TitledBorder(UIManager.getBorder("TitledBorder.border"), "操作", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 102, 153));
		titledBorder2.setTitleFont(new Font("宋体", Font.PLAIN, 20));
		OperatePanel.setBorder(titledBorder2);
		OperatePanel.setBounds(14, 435, 842, 222);
		contentPane.add(OperatePanel);
		OperatePanel.setLayout(null);
		
		logicalSignalLabel = new JLabel("\u903B\u8F91\u8FD0\u7B97\u7B26");
		logicalSignalLabel.setFont(new Font("宋体", Font.PLAIN, 16));
		logicalSignalLabel.setBounds(283, 65, 98, 18);
		OperatePanel.add(logicalSignalLabel);
		
		segmentLabel = new JLabel("\u5B57\u6BB5");
		segmentLabel.setFont(new Font("宋体", Font.PLAIN, 16));
		segmentLabel.setBounds(423, 65, 72, 18);
		OperatePanel.add(segmentLabel);
		
		signalLabel = new JLabel("\u8FD0\u7B97\u7B26");
		signalLabel.setFont(new Font("宋体", Font.PLAIN, 16));
		signalLabel.setBounds(593, 65, 72, 18);
		OperatePanel.add(signalLabel);
		
		valueDataTextField = new JTextField();
		valueDataTextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		valueDataTextField.setFont(new Font("宋体", Font.PLAIN, 16));
		valueDataTextField.setBounds(708, 86, 122, 34);
		OperatePanel.add(valueDataTextField);
		valueDataTextField.setColumns(10);
		
		logicalSignalComboBox = new JComboBox();
		logicalSignalComboBox.setModel(new DefaultComboBoxModel(QueryLogicalSignal()));
		logicalSignalComboBox.setFont(new Font("宋体", Font.PLAIN, 16));
		logicalSignalComboBox.setBounds(283, 86, 98, 34);
		OperatePanel.add(logicalSignalComboBox);
		
		segmentComboBox = new JComboBox();
		segmentComboBox.setModel(new DefaultComboBoxModel(QuerySegmentName()));
		segmentComboBox.setFont(new Font("宋体", Font.PLAIN, 16));
		segmentComboBox.setBounds(421, 86, 134, 34);
		OperatePanel.add(segmentComboBox);
		
		valueDataLabel = new JLabel("\u503C");
		valueDataLabel.setFont(new Font("宋体", Font.PLAIN, 16));
		valueDataLabel.setBounds(708, 65, 72, 18);
		OperatePanel.add(valueDataLabel);
		
		signalComboBox = new JComboBox();
		signalComboBox.setModel(new DefaultComboBoxModel(QuerySignal()));
		signalComboBox.setFont(new Font("宋体", Font.PLAIN, 16));
		signalComboBox.setBounds(592, 86, 102, 34);
		OperatePanel.add(signalComboBox);		
		
		closeButton = new JButton("\u5173\u95ED");
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					dispose();
			}
		});
		closeButton.setFont(new Font("宋体", Font.PLAIN, 20));
		closeButton.setBounds(708, 143, 122, 49);
		OperatePanel.add(closeButton);
		
		//查询按钮
		searchButton = new JButton("\u67E5\u8BE2");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RefreshListPanel();//刷新书籍表格
			}
		});
		searchButton.setFont(new Font("宋体", Font.PLAIN, 20));
		searchButton.setBounds(593, 143, 101, 49);
		OperatePanel.add(searchButton);
		
		deleteButton = new JButton("\u5220\u9664");
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(OPERATOR_CURRENTID == 0) //没有选中删除对象
					return;
				int selection = JOptionPane.showConfirmDialog(null,"确认删除？","删除",JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
				if( selection == JOptionPane.OK_OPTION ){
					OperatorDao.getInstance().Delete(OPERATOR_CURRENTID);
					OPERATOR_CURRENTID = 0;
					RefreshOperatorList();
				}
			}
		});
		deleteButton.setFont(new Font("宋体", Font.PLAIN, 20));
		deleteButton.setBounds(423, 143, 107, 49);
		OperatePanel.add(deleteButton);
		
		//添加
		addButton = new JButton("\u6DFB\u52A0");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String logicalSignal = logicalSignalComboBox.getSelectedItem().toString();//获得逻辑运算符
				String segment = segmentComboBox.getSelectedItem().toString(); //获得字段名
				String signal = signalComboBox.getSelectedItem().toString(); //获得运算符
				String valueData = valueDataTextField.getText();  //获得值
				if(valueData.equals("")){//value值 未填写
					JOptionPane.showMessageDialog(null,"值为空","提示",JOptionPane.INFORMATION_MESSAGE);
					valueDataTextField.requestFocus();
					return;
				}
				if(operatorList.size()>=1 && logicalSignal.equals("")) {//非第一个where条件缺少逻辑运算符
					JOptionPane.showMessageDialog(null,"逻辑运算符为空","提示",JOptionPane.INFORMATION_MESSAGE);
					logicalSignalComboBox.requestFocus();
					return;
				} 
				//根据字段去判断输入的值是否合法
				switch (segment) {
				case "印数":
					if (!isNumeric(valueData)) {//印数的值不是正整数
						JOptionPane.showMessageDialog(null, "值不为正整数", "提示", JOptionPane.INFORMATION_MESSAGE);
						valueDataTextField.requestFocus();
						return;
					}
					break;
				case "价格":
					if (!isNumeric(valueData)&&!MyUtil.isPositiveDecimal(valueData)) {//印数的值不是正小数
						JOptionPane.showMessageDialog(null, "值不为正实数", "提示", JOptionPane.INFORMATION_MESSAGE);
						valueDataTextField.requestFocus();
						return;
					}
					break;
				case "出版时间":
					if (!MyUtil.validateTime(valueData)) {//印数的值不是正小数
						JOptionPane.showMessageDialog(null, "值不为yyyy-MM-dd格式或者日期非法", "提示", JOptionPane.INFORMATION_MESSAGE);
						valueDataTextField.requestFocus();
						return;
					}
					break;
				}
				//通过有效性验证
				OperatorDao.getInstance().Add(1, logicalSignal, segment, signal, valueData);
				OPERATOR_CURRENTID = OperatorDao.getInstance().QueryID(USER_ID, logicalSignal, segment, signal, valueData);//1为登录userID
				//刷新运算符列表
				RefreshOperatorList();
			}
		});
		addButton.setFont(new Font("宋体", Font.PLAIN, 20));
		addButton.setBounds(283, 143, 98, 49);
		OperatePanel.add(addButton);
		
		operateScrollPane = new JScrollPane();
		operateScrollPane.setBounds(14, 27, 241, 165);
		OperatePanel.add(operateScrollPane);
		
		//运算符展示表格
		signalTable = new JTable();
		signalTable.setRowHeight(30);
		signalTable.setBorder(new LineBorder(new Color(0, 0, 0)));
		head1=new String[] {"逻辑运算符","字段名","运算符","值"};
		tableModel1=new DefaultTableModel(queryOperate(),head1){
		public boolean isCellEditable(int row, int column)
		{
			return false;
			}
		};
		signalTable.setFont(new Font("宋体", Font.BOLD, 16));
		signalTable.addMouseListener(new MouseAdapter() {   //用户名表格点击事件
			@SuppressWarnings("deprecation")
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String logicalSignal = operatorList.get(signalTable.getSelectedRow()).getLogicalSignal();//获得逻辑运算符
				String segment = operatorList.get(signalTable.getSelectedRow()).getSegment(); //获得字段名
				String signal = operatorList.get(signalTable.getSelectedRow()).getSignal(); //获得运算符
				String valueData = operatorList.get(signalTable.getSelectedRow()).getValueData();  //获得值
				OPERATOR_CURRENTID = OperatorDao.getInstance().QueryID(USER_ID, logicalSignal, segment, signal, valueData);
			}
		});
		ActionMap am1 = (ActionMap) UIManager.get("Table.actionMap");  
		am1.put("selectNextRowCell", new AbstractAction() {  
		    @Override  
		    public void actionPerformed(ActionEvent e) { 
		    	String logicalSignal = operatorList.get(signalTable.getSelectedRow()).getLogicalSignal();//获得逻辑运算符
				String segment = operatorList.get(signalTable.getSelectedRow()).getSegment(); //获得字段名
				String signal = operatorList.get(signalTable.getSelectedRow()).getSignal(); //获得运算符
				String valueData = operatorList.get(signalTable.getSelectedRow()).getValueData();  //获得值
				OPERATOR_CURRENTID = OperatorDao.getInstance().QueryID(USER_ID, logicalSignal, segment, signal, valueData);
		    }  
		});  
		signalTable.setActionMap(am1); 
		DefaultTableCellRenderer r1 = new DefaultTableCellRenderer();
		r1.setHorizontalAlignment(JLabel.CENTER);
		signalTable.setDefaultRenderer(Object.class, r1);
		signalTable.setModel(tableModel1);
		signalTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		operateScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);  //设置水平滚动条需要时可见
		FitTableColumns(signalTable);
		operateScrollPane.setViewportView(signalTable);
		
		exportPanel = new JPanel();
		exportPanel.setBorder(new TitledBorder(null, "\u5BFC\u51FA\u4E3A", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		exportPanel.setBounds(870, 448, 179, 209);
		contentPane.add(exportPanel);
		exportPanel.setLayout(null);
		
		JButton btn_PDF = new JButton("PDF");
		btn_PDF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				Runtime runtime = Runtime.getRuntime(); 
				chooser.setDialogType(JFileChooser.SAVE_DIALOG); // 保存
				//chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // 只显示目录
				//在弹出对话框之前，创建文件类型的过滤器
				FileNameExtensionFilter filter=new FileNameExtensionFilter("*.pdf", "pdf");
				chooser.setFileFilter(filter);
				int re = chooser.showSaveDialog(FRAME);
				if (re == JFileChooser.APPROVE_OPTION) {
					System.out.println(chooser.getSelectedFile().getPath());
					InventoryPDF pdf = new InventoryPDF(phoneList);
					try {
						pdf.createPDF(chooser.getSelectedFile().getPath());
						runtime.exec("rundll32 url.dll FileProtocolHandler "+chooser.getSelectedFile().getPath());//自动打开文件
					} catch (DocumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		btn_PDF.setBounds(28, 26, 113, 27);
		exportPanel.add(btn_PDF);
		
		JButton btn_EXCEL = new JButton("EXCEL");
		btn_EXCEL.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				Runtime runtime = Runtime.getRuntime(); 
				chooser.setDialogType(JFileChooser.SAVE_DIALOG); // 保存
				//chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // 只显示目录
				//在弹出对话框之前，创建文件类型的过滤器
				FileNameExtensionFilter filter=new FileNameExtensionFilter("*.xls", "xls");
				chooser.setFileFilter(filter);
				int re = chooser.showSaveDialog(FRAME);
				if (re == JFileChooser.APPROVE_OPTION) {
					System.out.println(chooser.getSelectedFile().getPath());
					InventoryEXCEL excel = new InventoryEXCEL(phoneList);
					try {
						excel.createExcel(chooser.getSelectedFile().getPath());
					} catch (WriteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						runtime.exec("rundll32 url.dll FileProtocolHandler "+chooser.getSelectedFile().getPath());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}//自动打开文件
					
				}
			}
		});
		btn_EXCEL.setBounds(28, 66, 113, 27);
		exportPanel.add(btn_EXCEL);
		
		JButton btn_Picture = new JButton("\u7EDF\u8BA1\u56FE");
		btn_Picture.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				Runtime runtime = Runtime.getRuntime(); 
				chooser.setDialogType(JFileChooser.SAVE_DIALOG); // 保存
				//chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // 只显示目录
				//在弹出对话框之前，创建文件类型的过滤器
				FileNameExtensionFilter filter=new FileNameExtensionFilter("*.png", "png");
				chooser.setFileFilter(filter);
				int re = chooser.showSaveDialog(FRAME);
				if (re == JFileChooser.APPROVE_OPTION) {
					System.out.println(chooser.getSelectedFile().getPath());
					InventoryPicture picture = new InventoryPicture(phoneList);
					picture.create(chooser.getSelectedFile().getPath());
					try {
						runtime.exec("rundll32 url.dll FileProtocolHandler "+chooser.getSelectedFile().getPath());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}//自动打开文件
					
				}
			}
		});
		btn_Picture.setBounds(28, 106, 113, 27);
		exportPanel.add(btn_Picture);
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
                width = Math.max(width, preferedWidth)+2;
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
    	for(int i=0;i<phoneList.size();i++)//使用之前清空数据
    		phoneList.remove(i);
        phoneList= SearchPhoneDao.getInstance().QueryAll(CombineCondition());
        titledBorder1.setTitle("共"+phoneList.size()+"项");
        data=new Object[phoneList.size()][head.length];
		for (int i = 0; i < phoneList.size(); i++) {
			data[i][0]=phoneList.get(i).getPhoneName();
            data[i][1]=phoneList.get(i).getPhoneNumber();
            data[i][2]=phoneList.get(i).getBandType();
            data[i][5]=phoneList.get(i).getInventory();
            data[i][6]=phoneList.get(i).getPrice();
            data[i][4]=phoneList.get(i).getProduceTime();
            data[i][3]=phoneList.get(i).getManufacturers();
            data[i][7]=phoneList.get(i).getInventory()*phoneList.get(i).getPrice();
		}
        return data;
    }
    
    /**
     * 查询操作符表
     * @return
     */
    public Object[][] queryOperate(){
    	for(int i=0;i<operatorList.size();i++)//使用之前清空数据
    		operatorList.remove(i);
        operatorList=OperatorDao.getInstance().QueryRecords(USERID);//查询指定id的所有记录
        data1=new Object[operatorList.size()][head1.length];
        for(int i=0;i<operatorList.size();i++){
            if(i==0)
            	data1[i][0]=null; //where第一个条件不需要设置逻辑运算符
            else
            	data1[i][0]=operatorList.get(i).getLogicalSignal();
            data1[i][1]=operatorList.get(i).getSegment();
            data1[i][2]=operatorList.get(i).getSignal();
            data1[i][3]=operatorList.get(i).getValueData();
        }
        return data1;
    }
   
	public void RefreshListPanel() {
		BooksInfoPanel.removeAll();
		BooksInfoPanel.add(BooksInfoScrollPane);
    	tableModel=new DefaultTableModel(queryData(),head){
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };
        booksTable.setModel(tableModel);
       // FitTableColumns(booksTable);
        BooksInfoScrollPane.setViewportView(booksTable);
		BooksInfoPanel.validate();
		BooksInfoPanel.repaint();
	}
	
	public void RefreshOperatorList() {
		OperatePanel.remove(operateScrollPane);
		OperatePanel.add(operateScrollPane);
    	tableModel1=new DefaultTableModel(queryOperate(),head1){
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };
        signalTable.setModel(tableModel1);
        FitTableColumns(signalTable);
        operateScrollPane.setViewportView(signalTable);
        OperatePanel.validate();
        OperatePanel.repaint();
	}
	
	
	/**
	 * 查询运算符
	 * @return
	 */
	public Object[] QuerySignal() {
		List<String> result = new ArrayList<String>();
		result.add(">");
		result.add("=");
		result.add("<");
		result.add("<>");
		result.add(">=");
		result.add("<=");
		result.add("like");
		return result.toArray();
	}

	
	/**
	 * 查询逻辑符
	 * @return
	 */
	public Object[] QueryLogicalSignal() {
		List<String> result = new ArrayList<String>();
		result.add("");
		result.add("and");
		result.add("or");
		return result.toArray();
	}
	
	/**
	 * 查询字段名
	 * @return
	 */
	public Object[] QuerySegmentName() {
		List<String> result = new ArrayList<String>();
		result.add("名称");
		result.add("型号");
		result.add("手机品牌");
		result.add("库存");
		result.add("定价");
		result.add("生产时间");
		result.add("生产厂家");
		return result.toArray();
	}
	
	public boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
	
	/**
	 * 组装条件
	 * @return
	 */
	public String CombineCondition() {
		String condition = ""; //条件字符串
		if(operatorList.size() > 0) {//有条件
			for(int i = 0; i < operatorList.size(); i++) {
				//step 1：逻辑连接符
				if( i == 0)
					condition = condition + " ";//相当于第一个逻辑连接符是空格
				else
					condition = condition + operatorList.get(i).getLogicalSignal() + " ";
				//step 2:字段
				condition  = condition + GetSegmentName(operatorList.get(i).getSegment()) + " ";
				//step 3:运算符+值
				if(operatorList.get(i).getSegment().equals("库存")||operatorList.get(i).getSegment().equals("定价"))
				{
					if(operatorList.get(i).getSignal().equals("like"))
						condition  = condition + operatorList.get(i).getSignal() + " '%"+operatorList.get(i).getValueData()+"%' ";
					else
						condition  = condition + operatorList.get(i).getSignal() +" "+operatorList.get(i).getValueData()+" ";
				}
				else {
					if(operatorList.get(i).getSignal().equals("like"))
						condition  = condition + operatorList.get(i).getSignal() + " '%"+operatorList.get(i).getValueData()+"%' ";
					else
						condition  = condition + operatorList.get(i).getSignal() +" '"+operatorList.get(i).getValueData()+"' ";
				}
				
			}
		}
		else
			condition = null;
		System.out.println(condition);
		return condition;
	}
	
	/**
	 * 获取字段名
	 * @param name
	 * @return
	 */
	public String GetSegmentName(String name) {
		switch(name) {
		case "名称": return "phoneName";
		case "型号": return "phoneNumber";
		case "手机品牌": return "bandType";
		case "库存": return "inventory";
		case "定价": return "price";
		case "生产时间": return "produceTime";
		case "生产厂家": return "manufacturersName";
		}
		return null;
	}
}
