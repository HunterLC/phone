package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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

import bll.InventoryDao;
import bll.PhoneDao;
import bll.RKDao;
import model.Phone;
import model.RK;

import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerDateModel;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Calendar;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.ActionMap;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;

public class Manage_RK extends JFrame {

	private JPanel contentPane,PhoneInfoPanel,SettingPanel,RKPanel;
	private JScrollPane phoneInfoScrollPane,scrollPane;
	private TitledBorder titledBorder1,titledBorder2,titledBorder3;
	private JLabel phoneNameLabel;
	private JLabel phoneNumberLabel;
	private JLabel printNumberLabel,priceLabel;
	private JTextField phoneNameTextField;
	private JTextField phoneNumberTextField;
	private JButton closeButton,searchButton,saveButton;
	private JTable phonesTable,rkTable;
	private static int CURRENTID = 0;
	private DefaultTableModel tableModel,tableModel1;
	private String head[]=null,head1[]=null;  //表格列名
	private Object [][]data=null;
	private Object [][]data1=null;   //表格行表
	private JTextField priceTextField;
	private JTextField bandTypeTextField;
	private JSpinner RKTimeSpinner,RKTimeSearchSpinner,RKNumberSpinner;
	private JTextArea noteTextArea;
	private List<RK> rkList;

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
					Manage_RK frame = new Manage_RK();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Manage_RK() {
		setTitle("\u624B\u673A\u5165\u5E93");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1020, 692);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setVisible(true);
		setLocationRelativeTo(null);
		
		PhoneInfoPanel = new JPanel();
		titledBorder1 = new TitledBorder(null, "共XXX项", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 102, 153));
		titledBorder1.setTitleFont(new Font("宋体", Font.PLAIN, 20));
		PhoneInfoPanel.setBorder(titledBorder1);
		PhoneInfoPanel.setBounds(14, 0, 974, 273);
		contentPane.add(PhoneInfoPanel);
		PhoneInfoPanel.setLayout(null);
		
		phoneInfoScrollPane = new JScrollPane();
		phoneInfoScrollPane.setBounds(14, 25, 946, 235);
		PhoneInfoPanel.add(phoneInfoScrollPane);
		
		//初始化表格
		phonesTable = new JTable();
		phonesTable.setRowHeight(35);
		phonesTable.setBorder(new LineBorder(new Color(0, 0, 0)));
		head=new String[]  {"名称","备注","型号","手机品牌","屏幕分辨率","库存","定价","生产时间","操作系统","生产厂家","编辑员"};
		tableModel=new DefaultTableModel(queryData(),head){
		public boolean isCellEditable(int row, int column)
		{
			return false;
			}
		};
		phonesTable.setFont(new Font("宋体", Font.BOLD, 14));
		phonesTable.addMouseListener(new MouseAdapter() {   //用户名表格点击事件
			@Override
			public void mouseClicked(MouseEvent arg0) {
				///titledBorder2.setTitle("修改");
				String phoneName = phonesTable.getValueAt(phonesTable.getSelectedRow(), 0).toString();
				String phoneNumber = phonesTable.getValueAt(phonesTable.getSelectedRow(), 2).toString();
				String bandType = phonesTable.getValueAt(phonesTable.getSelectedRow(), 3).toString();
				String price = phonesTable.getValueAt(phonesTable.getSelectedRow(), 6).toString();
				phoneNameTextField.setText(phoneName);
				phoneNumberTextField.setText(phoneNumber);
				priceTextField.setText(price);
				bandTypeTextField.setText(bandType);
				CURRENTID = PhoneDao.getInstance().QueryIDByphoneName(phoneName);//更新ID
				SettingPanel.repaint();
				RKNumberSpinner.requestFocus();
				searchButton.setEnabled(true);
				
			}
		});
		ActionMap am = (ActionMap) UIManager.get("Table.actionMap");  
		am.put("selectNextRowCell", new AbstractAction() {  
		    @Override  
		    public void actionPerformed(ActionEvent e) {
		    	
		    	String phoneName = phonesTable.getValueAt(phonesTable.getSelectedRow(), 0).toString();
				String phoneNumber = phonesTable.getValueAt(phonesTable.getSelectedRow(), 2).toString();
				String bandType = phonesTable.getValueAt(phonesTable.getSelectedRow(), 3).toString();
				String price = phonesTable.getValueAt(phonesTable.getSelectedRow(), 6).toString();
				phoneNameTextField.setText(phoneName);
				phoneNumberTextField.setText(phoneNumber);
				priceTextField.setText(price);
				bandTypeTextField.setText(bandType);
				CURRENTID = PhoneDao.getInstance().QueryIDByphoneName(phoneName);//更新ID
				SettingPanel.repaint();
				RKNumberSpinner.requestFocus();
				saveButton.setEnabled(true);
		    }  
		});  
		phonesTable.setActionMap(am); 
		DefaultTableCellRenderer r = new DefaultTableCellRenderer();
		r.setHorizontalAlignment(JLabel.CENTER);
		phonesTable.setDefaultRenderer(Object.class, r);
		phonesTable.setModel(tableModel);
		//phonesTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		//phoneInfoScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);  //设置水平滚动条需要时可见
		//FitTableColumns(phonesTable);
		phoneInfoScrollPane.setViewportView(phonesTable);
		
		SettingPanel = new JPanel();
		titledBorder2 = new TitledBorder(UIManager.getBorder("TitledBorder.border"), "入库", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 102, 153));
		titledBorder2.setTitleFont(new Font("宋体", Font.PLAIN, 20));
		SettingPanel.setBorder(titledBorder2);
		SettingPanel.setBounds(14, 286, 974, 159);
		contentPane.add(SettingPanel);
		SettingPanel.setLayout(null);
		
		phoneNameLabel = new JLabel("\u540D\u79F0");
		phoneNameLabel.setFont(new Font("宋体", Font.PLAIN, 20));
		phoneNameLabel.setBounds(14, 38, 72, 18);
		SettingPanel.add(phoneNameLabel);
		
		phoneNumberLabel = new JLabel("\u578B\u53F7");
		phoneNumberLabel.setFont(new Font("宋体", Font.PLAIN, 20));
		phoneNumberLabel.setBounds(14, 84, 72, 18);
		SettingPanel.add(phoneNumberLabel);
		
		phoneNameTextField = new JTextField();
		phoneNameTextField.setEditable(false);
		phoneNameTextField.setFont(new Font("宋体", Font.PLAIN, 20));
		phoneNameTextField.setBounds(64, 37, 282, 24);
		SettingPanel.add(phoneNameTextField);
		phoneNameTextField.setColumns(10);
		
		phoneNumberTextField = new JTextField();
		phoneNumberTextField.setEditable(false);
		phoneNumberTextField.setFont(new Font("宋体", Font.PLAIN, 20));
		phoneNumberTextField.setBounds(64, 84, 282, 24);
		SettingPanel.add(phoneNumberTextField);
		phoneNumberTextField.setColumns(10);
		
		printNumberLabel = new JLabel("\u54C1\u724C");
		printNumberLabel.setFont(new Font("宋体", Font.PLAIN, 20));
		printNumberLabel.setBounds(181, 121, 40, 27);
		SettingPanel.add(printNumberLabel);
		
		priceLabel = new JLabel("\u5B9A\u4EF7");
		priceLabel.setFont(new Font("宋体", Font.PLAIN, 20));
		priceLabel.setBounds(14, 123, 72, 23);
		SettingPanel.add(priceLabel);
		
		saveButton = new JButton("\u4FDD\u5B58");
		
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("吼吼吼吼");
				save();
			}
		});
		saveButton.setFont(new Font("宋体", Font.PLAIN, 20));
		saveButton.setBounds(830, 121, 99, 27);
		SettingPanel.add(saveButton);
		
		priceTextField = new JTextField();
		priceTextField.setEditable(false);
		priceTextField.setBounds(64, 124, 103, 24);
		SettingPanel.add(priceTextField);
		priceTextField.setColumns(10);
		
		bandTypeTextField = new JTextField();
		bandTypeTextField.setEditable(false);
		bandTypeTextField.setBounds(227, 124, 119, 24);
		SettingPanel.add(bandTypeTextField);
		bandTypeTextField.setColumns(10);
		
		JLabel RKNumberLabel = new JLabel("\u5165\u5E93\u6570\u91CF");
		RKNumberLabel.setFont(new Font("宋体", Font.PLAIN, 20));
		RKNumberLabel.setBounds(401, 29, 85, 18);
		SettingPanel.add(RKNumberLabel);
		
		RKNumberSpinner = new JSpinner();
		RKNumberSpinner.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER)
					saveButton.requestFocus();
			}
		});
		RKNumberSpinner.setFont(new Font("宋体", Font.PLAIN, 20));
		RKNumberSpinner.setBounds(487, 24, 72, 27);
		SettingPanel.add(RKNumberSpinner);
		
		JLabel RKTimeLabel = new JLabel("\u5165\u5E93\u65F6\u95F4");
		RKTimeLabel.setFont(new Font("宋体", Font.PLAIN, 20));
		RKTimeLabel.setBounds(573, 29, 85, 18);
		SettingPanel.add(RKTimeLabel);
		
		 // 获得时间日期模型
		SpinnerDateModel model = new SpinnerDateModel();
		// 获得JSPinner对象
		RKTimeSpinner = new JSpinner(model);
		RKTimeSpinner.setValue(new Date());
		// 设置时间格式
		JSpinner.DateEditor editor = new JSpinner.DateEditor(RKTimeSpinner, "yyyy-MM-dd");
		RKTimeSpinner.setEditor(editor);
		RKTimeSpinner.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
					//osTypeComboBox.requestFocus();
					;
			}
		});
		RKTimeSpinner.setFont(new Font("宋体", Font.PLAIN, 16));
		RKTimeSpinner.setBounds(672, 26, 132, 27);
		SettingPanel.add(RKTimeSpinner);
		
		JLabel noteLabel = new JLabel("\u5907\u6CE8");
		noteLabel.setFont(new Font("宋体", Font.PLAIN, 20));
		noteLabel.setBounds(360, 92, 40, 18);
		SettingPanel.add(noteLabel);
		
		JScrollPane noteScrollPane = new JScrollPane();
		noteScrollPane.setBounds(403, 63, 401, 83);
		SettingPanel.add(noteScrollPane);
		
		noteTextArea = new JTextArea();
		noteScrollPane.setViewportView(noteTextArea);
		
		RKPanel = new JPanel();
		titledBorder3 = new TitledBorder(null, "\u624B\u673A\u5165\u5E93\u660E\u7EC6\u67E5\u8BE2\uFF08\u5171XX\u6761\uFF09", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 102, 153));
		titledBorder3.setTitleFont(new Font("宋体", Font.PLAIN, 20));
		RKPanel.setBorder(titledBorder3);
		RKPanel.setBounds(14, 458, 974, 174);
		contentPane.add(RKPanel);
		RKPanel.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(14, 23, 789, 138);
		
		RKPanel.add(scrollPane);
		
		closeButton = new JButton("\u5173\u95ED");
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		closeButton.setBounds(817, 134, 99, 27);
		RKPanel.add(closeButton);
		closeButton.setFont(new Font("宋体", Font.PLAIN, 20));
		
		searchButton = new JButton("\u67E5\u8BE2");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RefreshRKList();
			}
		});
		searchButton.setBounds(817, 94, 99, 27);
		RKPanel.add(searchButton);
		searchButton.setFont(new Font("宋体", Font.PLAIN, 20));
		
		 // 获得时间日期模型
		SpinnerDateModel model1 = new SpinnerDateModel();
		// 获得JSPinner对象
		RKTimeSearchSpinner = new JSpinner(model1);
		RKTimeSearchSpinner.setValue(new Date());
		// 设置时间格式
		JSpinner.DateEditor editor1 = new JSpinner.DateEditor(RKTimeSearchSpinner, "yyyy-MM-dd");
		RKTimeSearchSpinner.setEditor(editor1);
		RKTimeSearchSpinner.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
					//osTypeComboBox.requestFocus();
					;
			}
		});
		RKTimeSearchSpinner.setFont(new Font("宋体", Font.PLAIN, 16));
		RKTimeSearchSpinner.setBounds(817, 42, 125, 27);
		RKPanel.add(RKTimeSearchSpinner);
		// 初始化表格
				rkTable = new JTable();
				rkTable.setRowHeight(35);
				rkTable.setBorder(new LineBorder(new Color(0, 0, 0)));
				head1 = new String[] { "名称", "型号","单价","入库数量","入库时间", "入库备注"};
				tableModel1 = new DefaultTableModel(queryData1(), head1) {
					public boolean isCellEditable(int row, int column) {
						return false;
					}
				};
				rkTable.setFont(new Font("宋体", Font.BOLD, 14));
				rkTable.addMouseListener(new MouseAdapter() { // 用户名表格点击事件
					@Override
					public void mouseClicked(MouseEvent arg0) {

					}
				});
				rkTable.setDefaultRenderer(Object.class, r);
				rkTable.setModel(tableModel1);
				//rkTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				//scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); // 设置水平滚动条需要时可见
				//FitTableColumns(rkTable);
				scrollPane.setViewportView(rkTable);
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
                width = Math.max(width, preferedWidth);
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
    	String sortType = "phoneNumber"; 
    	String searchText = null;
        List<Phone> list=PhoneDao.getInstance().QueryAll(searchText,sortType);
        titledBorder1.setTitle("共"+list.size()+"项");
        data=new Object[list.size()][head.length];
        for(int i=0;i<list.size();i++){
            for(int j=0;j<head.length;j++){
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
        }
        return data;
    }
    
	// 生成用户名表格数据
	/**
	 * @return
	 */
	public Object[][] queryData1() {
		if(rkList!=null)
		for (int i = 0; i < rkList.size(); i++) {
			rkList.remove(i);
		}
		String searchText = new SimpleDateFormat("yyyy-MM-dd").format(RKTimeSearchSpinner.getValue());
		rkList = RKDao.getInstance().QueryAll(searchText);
		titledBorder3.setTitle("手机入库明细查询（共"+rkList.size()+"条）");
		data1 = new Object[rkList.size()][head1.length];
		for (int i = 0; i < rkList.size(); i++) {
			data1[i][0] = rkList.get(i).getPhoneName();
			data1[i][1] = rkList.get(i).getPhoneNumber();
			data1[i][2] = rkList.get(i).getPrice();
			data1[i][3] = rkList.get(i).getTotalNumber();
			data1[i][4] = rkList.get(i).getInDate();
			data1[i][5] = rkList.get(i).getNote();
		}
		return data1;
	}
    
	public void RefreshListPanel() {
		PhoneInfoPanel.removeAll();
		PhoneInfoPanel.add(phoneInfoScrollPane);
    	tableModel=new DefaultTableModel(queryData(),head){
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };
        phonesTable.setModel(tableModel);
       // FitTableColumns(phonesTable);
        phoneInfoScrollPane.setViewportView(phonesTable);
    	titledBorder1.setTitle("共"+PhoneDao.getInstance().QueryCount()+"项");
		PhoneInfoPanel.validate();
		PhoneInfoPanel.repaint();
	}
	
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
		String number = RKNumberSpinner.getValue().toString();
		String date = new SimpleDateFormat("yyyy-MM-dd").format(RKTimeSpinner.getValue());
		String note = noteTextArea.getText();
		String phoneNumber = phoneNumberTextField.getText();
		RKDao.getInstance().Add(date, phoneNumber, number, note);
		InventoryDao.getInstance().Update(Integer.valueOf(number)+InventoryDao.getInstance().QueryInventory(phoneNumber), phoneNumber);
		RefreshRKList();
		phonesTable.requestFocus();
	}
	public void RefreshRKList() {
		RKPanel.remove(scrollPane);
		RKPanel.add(scrollPane);
    	tableModel1=new DefaultTableModel(queryData1(),head1){
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };
        rkTable.setModel(tableModel1);
       // FitTableColumns(rkTable);
        scrollPane.setViewportView(rkTable);
        RKPanel.validate();
        RKPanel.repaint();
	}
}
