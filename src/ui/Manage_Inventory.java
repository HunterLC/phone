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
	private String head[]=null;  //�������
	private String head1[]=null;  //�������
	private Object [][]data=null;   //����б�
	private Object [][]data1=null;   //����б�
	private ButtonGroup selectedGroup;
	private JTable signalTable;
	public static List<Operator> operatorList = new ArrayList<Operator>();//��������
	public static List<Phone> phoneList = new ArrayList<Phone>();//����
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
		titledBorder3.setTitleFont(new Font("����", Font.PLAIN, 20));
		titledBorder4 = new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u6392\u5E8F\u65B9\u5F0F", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 102, 153));
		titledBorder4.setTitleFont(new Font("����", Font.PLAIN, 20));
		
		selectedGroup = new ButtonGroup();
		
		BooksInfoPanel = new JPanel();
		titledBorder1 = new TitledBorder(null, "��XXX��", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 102, 153));
		titledBorder1.setTitleFont(new Font("����", Font.PLAIN, 20));
		BooksInfoPanel.setBorder(titledBorder1);
		BooksInfoPanel.setBounds(14, 13, 1035, 422);
		contentPane.add(BooksInfoPanel);
		BooksInfoPanel.setLayout(null);
		
		BooksInfoScrollPane = new JScrollPane();
		BooksInfoScrollPane.setBounds(14, 26, 1007, 383);
		BooksInfoPanel.add(BooksInfoScrollPane);
		
		//��ʼ�����
		booksTable = new JTable();
		booksTable.setRowHeight(40);
		booksTable.setBorder(new LineBorder(new Color(0, 0, 0)));
		head=new String[] {"����","�ͺ�","�ֻ�Ʒ��","��������","����ʱ��","�������","����","������"};
		tableModel=new DefaultTableModel(queryData(),head){
		public boolean isCellEditable(int row, int column)
		{
			return false;
			}
		};
		booksTable.setFont(new Font("����", Font.BOLD, 16));
		booksTable.addMouseListener(new MouseAdapter() {   //�û���������¼�
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
		//BooksInfoScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);  //����ˮƽ��������Ҫʱ�ɼ�
		//FitTableColumns(booksTable);
		BooksInfoScrollPane.setViewportView(booksTable);
		
		OperatePanel = new JPanel();
		titledBorder2 = new TitledBorder(UIManager.getBorder("TitledBorder.border"), "����", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 102, 153));
		titledBorder2.setTitleFont(new Font("����", Font.PLAIN, 20));
		OperatePanel.setBorder(titledBorder2);
		OperatePanel.setBounds(14, 435, 842, 222);
		contentPane.add(OperatePanel);
		OperatePanel.setLayout(null);
		
		logicalSignalLabel = new JLabel("\u903B\u8F91\u8FD0\u7B97\u7B26");
		logicalSignalLabel.setFont(new Font("����", Font.PLAIN, 16));
		logicalSignalLabel.setBounds(283, 65, 98, 18);
		OperatePanel.add(logicalSignalLabel);
		
		segmentLabel = new JLabel("\u5B57\u6BB5");
		segmentLabel.setFont(new Font("����", Font.PLAIN, 16));
		segmentLabel.setBounds(423, 65, 72, 18);
		OperatePanel.add(segmentLabel);
		
		signalLabel = new JLabel("\u8FD0\u7B97\u7B26");
		signalLabel.setFont(new Font("����", Font.PLAIN, 16));
		signalLabel.setBounds(593, 65, 72, 18);
		OperatePanel.add(signalLabel);
		
		valueDataTextField = new JTextField();
		valueDataTextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		valueDataTextField.setFont(new Font("����", Font.PLAIN, 16));
		valueDataTextField.setBounds(708, 86, 122, 34);
		OperatePanel.add(valueDataTextField);
		valueDataTextField.setColumns(10);
		
		logicalSignalComboBox = new JComboBox();
		logicalSignalComboBox.setModel(new DefaultComboBoxModel(QueryLogicalSignal()));
		logicalSignalComboBox.setFont(new Font("����", Font.PLAIN, 16));
		logicalSignalComboBox.setBounds(283, 86, 98, 34);
		OperatePanel.add(logicalSignalComboBox);
		
		segmentComboBox = new JComboBox();
		segmentComboBox.setModel(new DefaultComboBoxModel(QuerySegmentName()));
		segmentComboBox.setFont(new Font("����", Font.PLAIN, 16));
		segmentComboBox.setBounds(421, 86, 134, 34);
		OperatePanel.add(segmentComboBox);
		
		valueDataLabel = new JLabel("\u503C");
		valueDataLabel.setFont(new Font("����", Font.PLAIN, 16));
		valueDataLabel.setBounds(708, 65, 72, 18);
		OperatePanel.add(valueDataLabel);
		
		signalComboBox = new JComboBox();
		signalComboBox.setModel(new DefaultComboBoxModel(QuerySignal()));
		signalComboBox.setFont(new Font("����", Font.PLAIN, 16));
		signalComboBox.setBounds(592, 86, 102, 34);
		OperatePanel.add(signalComboBox);		
		
		closeButton = new JButton("\u5173\u95ED");
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					dispose();
			}
		});
		closeButton.setFont(new Font("����", Font.PLAIN, 20));
		closeButton.setBounds(708, 143, 122, 49);
		OperatePanel.add(closeButton);
		
		//��ѯ��ť
		searchButton = new JButton("\u67E5\u8BE2");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RefreshListPanel();//ˢ���鼮���
			}
		});
		searchButton.setFont(new Font("����", Font.PLAIN, 20));
		searchButton.setBounds(593, 143, 101, 49);
		OperatePanel.add(searchButton);
		
		deleteButton = new JButton("\u5220\u9664");
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(OPERATOR_CURRENTID == 0) //û��ѡ��ɾ������
					return;
				int selection = JOptionPane.showConfirmDialog(null,"ȷ��ɾ����","ɾ��",JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
				if( selection == JOptionPane.OK_OPTION ){
					OperatorDao.getInstance().Delete(OPERATOR_CURRENTID);
					OPERATOR_CURRENTID = 0;
					RefreshOperatorList();
				}
			}
		});
		deleteButton.setFont(new Font("����", Font.PLAIN, 20));
		deleteButton.setBounds(423, 143, 107, 49);
		OperatePanel.add(deleteButton);
		
		//���
		addButton = new JButton("\u6DFB\u52A0");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String logicalSignal = logicalSignalComboBox.getSelectedItem().toString();//����߼������
				String segment = segmentComboBox.getSelectedItem().toString(); //����ֶ���
				String signal = signalComboBox.getSelectedItem().toString(); //��������
				String valueData = valueDataTextField.getText();  //���ֵ
				if(valueData.equals("")){//valueֵ δ��д
					JOptionPane.showMessageDialog(null,"ֵΪ��","��ʾ",JOptionPane.INFORMATION_MESSAGE);
					valueDataTextField.requestFocus();
					return;
				}
				if(operatorList.size()>=1 && logicalSignal.equals("")) {//�ǵ�һ��where����ȱ���߼������
					JOptionPane.showMessageDialog(null,"�߼������Ϊ��","��ʾ",JOptionPane.INFORMATION_MESSAGE);
					logicalSignalComboBox.requestFocus();
					return;
				} 
				//�����ֶ�ȥ�ж������ֵ�Ƿ�Ϸ�
				switch (segment) {
				case "ӡ��":
					if (!isNumeric(valueData)) {//ӡ����ֵ����������
						JOptionPane.showMessageDialog(null, "ֵ��Ϊ������", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
						valueDataTextField.requestFocus();
						return;
					}
					break;
				case "�۸�":
					if (!isNumeric(valueData)&&!MyUtil.isPositiveDecimal(valueData)) {//ӡ����ֵ������С��
						JOptionPane.showMessageDialog(null, "ֵ��Ϊ��ʵ��", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
						valueDataTextField.requestFocus();
						return;
					}
					break;
				case "����ʱ��":
					if (!MyUtil.validateTime(valueData)) {//ӡ����ֵ������С��
						JOptionPane.showMessageDialog(null, "ֵ��Ϊyyyy-MM-dd��ʽ�������ڷǷ�", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
						valueDataTextField.requestFocus();
						return;
					}
					break;
				}
				//ͨ����Ч����֤
				OperatorDao.getInstance().Add(1, logicalSignal, segment, signal, valueData);
				OPERATOR_CURRENTID = OperatorDao.getInstance().QueryID(USER_ID, logicalSignal, segment, signal, valueData);//1Ϊ��¼userID
				//ˢ��������б�
				RefreshOperatorList();
			}
		});
		addButton.setFont(new Font("����", Font.PLAIN, 20));
		addButton.setBounds(283, 143, 98, 49);
		OperatePanel.add(addButton);
		
		operateScrollPane = new JScrollPane();
		operateScrollPane.setBounds(14, 27, 241, 165);
		OperatePanel.add(operateScrollPane);
		
		//�����չʾ���
		signalTable = new JTable();
		signalTable.setRowHeight(30);
		signalTable.setBorder(new LineBorder(new Color(0, 0, 0)));
		head1=new String[] {"�߼������","�ֶ���","�����","ֵ"};
		tableModel1=new DefaultTableModel(queryOperate(),head1){
		public boolean isCellEditable(int row, int column)
		{
			return false;
			}
		};
		signalTable.setFont(new Font("����", Font.BOLD, 16));
		signalTable.addMouseListener(new MouseAdapter() {   //�û���������¼�
			@SuppressWarnings("deprecation")
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String logicalSignal = operatorList.get(signalTable.getSelectedRow()).getLogicalSignal();//����߼������
				String segment = operatorList.get(signalTable.getSelectedRow()).getSegment(); //����ֶ���
				String signal = operatorList.get(signalTable.getSelectedRow()).getSignal(); //��������
				String valueData = operatorList.get(signalTable.getSelectedRow()).getValueData();  //���ֵ
				OPERATOR_CURRENTID = OperatorDao.getInstance().QueryID(USER_ID, logicalSignal, segment, signal, valueData);
			}
		});
		ActionMap am1 = (ActionMap) UIManager.get("Table.actionMap");  
		am1.put("selectNextRowCell", new AbstractAction() {  
		    @Override  
		    public void actionPerformed(ActionEvent e) { 
		    	String logicalSignal = operatorList.get(signalTable.getSelectedRow()).getLogicalSignal();//����߼������
				String segment = operatorList.get(signalTable.getSelectedRow()).getSegment(); //����ֶ���
				String signal = operatorList.get(signalTable.getSelectedRow()).getSignal(); //��������
				String valueData = operatorList.get(signalTable.getSelectedRow()).getValueData();  //���ֵ
				OPERATOR_CURRENTID = OperatorDao.getInstance().QueryID(USER_ID, logicalSignal, segment, signal, valueData);
		    }  
		});  
		signalTable.setActionMap(am1); 
		DefaultTableCellRenderer r1 = new DefaultTableCellRenderer();
		r1.setHorizontalAlignment(JLabel.CENTER);
		signalTable.setDefaultRenderer(Object.class, r1);
		signalTable.setModel(tableModel1);
		signalTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		operateScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);  //����ˮƽ��������Ҫʱ�ɼ�
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
				chooser.setDialogType(JFileChooser.SAVE_DIALOG); // ����
				//chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // ֻ��ʾĿ¼
				//�ڵ����Ի���֮ǰ�������ļ����͵Ĺ�����
				FileNameExtensionFilter filter=new FileNameExtensionFilter("*.pdf", "pdf");
				chooser.setFileFilter(filter);
				int re = chooser.showSaveDialog(FRAME);
				if (re == JFileChooser.APPROVE_OPTION) {
					System.out.println(chooser.getSelectedFile().getPath());
					InventoryPDF pdf = new InventoryPDF(phoneList);
					try {
						pdf.createPDF(chooser.getSelectedFile().getPath());
						runtime.exec("rundll32 url.dll FileProtocolHandler "+chooser.getSelectedFile().getPath());//�Զ����ļ�
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
				chooser.setDialogType(JFileChooser.SAVE_DIALOG); // ����
				//chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // ֻ��ʾĿ¼
				//�ڵ����Ի���֮ǰ�������ļ����͵Ĺ�����
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
					}//�Զ����ļ�
					
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
				chooser.setDialogType(JFileChooser.SAVE_DIALOG); // ����
				//chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // ֻ��ʾĿ¼
				//�ڵ����Ի���֮ǰ�������ļ����͵Ĺ�����
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
					}//�Զ����ļ�
					
				}
			}
		});
		btn_Picture.setBounds(28, 106, 113, 27);
		exportPanel.add(btn_Picture);
	}
	
	/**
	 * ����table���п������е����ݶ��仯
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
	//�����û����������
    /**
     * @return
     */
    public Object[][] queryData(){
    	for(int i=0;i<phoneList.size();i++)//ʹ��֮ǰ�������
    		phoneList.remove(i);
        phoneList= SearchPhoneDao.getInstance().QueryAll(CombineCondition());
        titledBorder1.setTitle("��"+phoneList.size()+"��");
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
     * ��ѯ��������
     * @return
     */
    public Object[][] queryOperate(){
    	for(int i=0;i<operatorList.size();i++)//ʹ��֮ǰ�������
    		operatorList.remove(i);
        operatorList=OperatorDao.getInstance().QueryRecords(USERID);//��ѯָ��id�����м�¼
        data1=new Object[operatorList.size()][head1.length];
        for(int i=0;i<operatorList.size();i++){
            if(i==0)
            	data1[i][0]=null; //where��һ����������Ҫ�����߼������
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
	 * ��ѯ�����
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
	 * ��ѯ�߼���
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
	 * ��ѯ�ֶ���
	 * @return
	 */
	public Object[] QuerySegmentName() {
		List<String> result = new ArrayList<String>();
		result.add("����");
		result.add("�ͺ�");
		result.add("�ֻ�Ʒ��");
		result.add("���");
		result.add("����");
		result.add("����ʱ��");
		result.add("��������");
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
	 * ��װ����
	 * @return
	 */
	public String CombineCondition() {
		String condition = ""; //�����ַ���
		if(operatorList.size() > 0) {//������
			for(int i = 0; i < operatorList.size(); i++) {
				//step 1���߼����ӷ�
				if( i == 0)
					condition = condition + " ";//�൱�ڵ�һ���߼����ӷ��ǿո�
				else
					condition = condition + operatorList.get(i).getLogicalSignal() + " ";
				//step 2:�ֶ�
				condition  = condition + GetSegmentName(operatorList.get(i).getSegment()) + " ";
				//step 3:�����+ֵ
				if(operatorList.get(i).getSegment().equals("���")||operatorList.get(i).getSegment().equals("����"))
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
	 * ��ȡ�ֶ���
	 * @param name
	 * @return
	 */
	public String GetSegmentName(String name) {
		switch(name) {
		case "����": return "phoneName";
		case "�ͺ�": return "phoneNumber";
		case "�ֻ�Ʒ��": return "bandType";
		case "���": return "inventory";
		case "����": return "price";
		case "����ʱ��": return "produceTime";
		case "��������": return "manufacturersName";
		}
		return null;
	}
}
